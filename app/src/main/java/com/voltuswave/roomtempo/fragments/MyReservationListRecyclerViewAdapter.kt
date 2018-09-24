package com.voltuswave.roomtempo.fragments

import android.arch.lifecycle.MutableLiveData
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.design.card.MaterialCardView
import android.support.v4.app.FragmentActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.ReservationList
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import com.voltuswave.roomtempo.utils.MyDiffUtilsCallBack
import kotlinx.android.synthetic.main.fragment_reservationlist.view.*
import org.json.JSONArray
import java.util.*


/**
 * [RecyclerView.Adapter] that can display a [ReservationList] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyReservationListRecyclerViewAdapter(
        initReservationList: List<ReservationList>,val activity: FragmentActivity?,
        private val mListener: ReservationListFragment.OnReservationListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyReservationListRecyclerViewAdapter.ViewHolder>() {

    private val cal = Calendar.getInstance()
    private var storedProcedureCalls = StoredProcedureCalls()
    private val mOnClickListener: View.OnClickListener
    private val actors = mutableListOf<ReservationList>()
    private val filterList = mutableListOf<ReservationList>()
    val reservationStatusFromRuMutableData = MutableLiveData<JSONArray>()
    private val pendingUpdates = ArrayDeque<List<ReservationList>>()
    init {
        mOnClickListener = View.OnClickListener { v ->
            val reservationId = v.tag as Int
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onReservationListFragmentInteraction(reservationId)
        }
            actors.addAll(initReservationList)
        filterList.addAll(initReservationList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val o = payloads[0] as Bundle
            for (key in o.keySet()) {
                if (key == "statusName") {
                    holder.mStatusName.text = actors[position].statusName
                }
            }
        }
    }

    fun updateList(actors: List<ReservationList>) {
        pendingUpdates.push(actors)
        if (pendingUpdates.size > 1) {
            return
        }
        updateItemsInternal(actors)
        /*val diffCallback = MyDiffUtilsCallBack(this.actors, actors)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.actors.clear()
        this.actors.addAll(actors)
        diffResult.dispatchUpdatesTo(this)*/
    }

    private fun updateItemsInternal(actors: List<ReservationList>) {
        val handler = Handler()
        Thread(Runnable {
            val diffCallback = MyDiffUtilsCallBack(this.actors, actors)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            handler.post{
                applyDiffResult(actors, diffResult)
            }
        }).start()

    }

    private fun applyDiffResult(newItems: List<ReservationList>, diffResult: DiffUtil.DiffResult) {
        pendingUpdates.remove(newItems)
        dispatchUpdates(newItems, diffResult)
        if (pendingUpdates.size > 0) {
            val latest = pendingUpdates.pop()
            pendingUpdates.clear()
            updateItemsInternal(latest)
        }
    }

    private fun dispatchUpdates(newItems: List<ReservationList>, diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
        this.actors.clear()
        this.actors.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_reservationlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = actors[position]
        holder.mGuestFullName.text = item.guestFullName

        if(item.numberOfAdults == 0 && item.numberOfChildren == 0){
            holder.mMemberCount.visibility = View.GONE
            holder.mPersonCount.visibility = View.VISIBLE
        }else{
            holder.mMemberCount.visibility = View.VISIBLE
            holder.mPersonCount.visibility = View.GONE
        }

        holder.mNumberOfAdults.text = item.numberOfAdults.toString()
        holder.mNumberOfChildren.text = item.numberOfChildren.toString()
        holder.mNumberOfPersons.text = item.persons.toString()

        holder.mStartDate.text = item.startDate
        holder.mEndDate.text = item.endDate
        holder.mNights.text = item.nights.toString()

        holder.mUnitTypeName.text = item.unitTypeName
        holder.mStatusName.text = item.statusName

        val isCheckIn = item.isIsCheckIn
        val isCheckOut = item.isIsCheckOut
        val isActionEnable = item.isIsActionEnable

        val nextStatusId = item.nextStatusId

        if(isCheckIn && isActionEnable){
            holder.mCheckIn.visibility = View.VISIBLE
        }else{
            holder.mCheckIn.visibility = View.GONE
        }
        if (isCheckOut && isActionEnable){
            holder.mCheckOut.visibility = View.VISIBLE
        }else{
            holder.mCheckOut.visibility = View.GONE
        }

        val statusId = item.statusId
        val reservationId = item.reservationId

        holder.mCheckIn.setOnClickListener {
            storedProcedureCalls.updateReservationStatus("UpdateReservationStatus",nextStatusId,reservationId,0,activity)
        }

        holder.mCheckOut.setOnClickListener {
            storedProcedureCalls.updateReservationStatus("UpdateReservationStatus",nextStatusId,reservationId,0,activity)
        }

        when {
            item.statusName == "Reserved" -> holder.mStatusName.setBackgroundColor(Color.parseColor("#D74ED2"))
            item.statusName == "Confirmed" -> holder.mStatusName.setBackgroundColor(Color.parseColor("#B97FFE"))
            item.statusName == "Cancelled" -> holder.mStatusName.setBackgroundColor(Color.parseColor("#595959"))
            item.statusName == "Guaranteed" -> holder.mStatusName.setBackgroundColor(Color.parseColor("#7DA4FA"))
            item.statusName == "On Hold" -> holder.mStatusName.setBackgroundColor(Color.parseColor("#FDCF46"))
            item.statusName == "No Show" -> holder.mStatusName.setBackgroundColor(Color.parseColor("#FD5190"))
            item.statusName == "In-House" -> holder.mStatusName.setBackgroundColor(Color.parseColor("#5BCF53"))
            item.statusName == "Departed" -> holder.mStatusName.setBackgroundColor(Color.parseColor("#D9D9D9"))
        }

        when {
            item.sourceName == "Rentals United" -> holder.mSourceName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rentalsunited, 0, 0, 0)
            item.sourceName == "Tempo" -> holder.mSourceName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tempo, 0, 0, 0)
        }


        with(holder.mView) {
            tag = item.reservationId
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = actors.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mGuestFullName: TextView = mView.GuestFullName
        val mNumberOfAdults: TextView = mView.NumberOfAdults
        val mNumberOfChildren: TextView = mView.NumberOfChildren
        val mNumberOfPersons: TextView = mView.NumberOfPersons
        val mMemberCount : LinearLayout = mView.member_count
        val mPersonCount: LinearLayout = mView.person_count

        val mStartDate: TextView = mView.StartDate
        val mEndDate: TextView = mView.EndDate
        val mNights: TextView = mView.Nights

        val mUnitTypeName: TextView = mView.UnitTypeName
        val mStatusName: TextView = mView.StatusName
        val mSourceName: TextView = mView.SourceName
        val mCheckIn: MaterialCardView = mView.checkIn_btn
        val mCheckOut: MaterialCardView = mView.checkOut_btn

        override fun toString(): String {
            return super.toString() + " '" + mGuestFullName.text + "'"
        }
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        actors.clear()
        if (charText.isEmpty()) {
            actors.addAll(filterList)
        } else {
            for (wp in filterList) {
                if (wp.guestFullName.toLowerCase(Locale.getDefault()).contains(charText) || wp.statusName.toLowerCase(Locale.getDefault()).contains(charText) || wp.unitTypeName.toLowerCase(Locale.getDefault()).contains(charText) || wp.unitName.toLowerCase(Locale.getDefault()).contains(charText)) {
                    actors.add(wp)
                }
            }
        }
        notifyDataSetChanged()

    }

}
