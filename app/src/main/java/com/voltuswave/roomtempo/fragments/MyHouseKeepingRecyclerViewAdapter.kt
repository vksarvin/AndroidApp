package com.voltuswave.roomtempo.fragments

import android.content.Context
import android.graphics.Color
import android.support.design.card.MaterialCardView
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.UnitDetail_
import com.voltuswave.roomtempo.models.UsersList
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import kotlinx.android.synthetic.main.fragment_housekeeping.view.*
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyHouseKeepingRecyclerViewAdapter(val context: Context,val activity: FragmentActivity?,
        initHouseKeepingList: List<UnitDetail_>,private var userList:List<UsersList>?,
        private val mListener: HouseKeepingFragment.OnHouseKeepingListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyHouseKeepingRecyclerViewAdapter.ViewHolder>() {
    private var storedProcedureCalls = StoredProcedureCalls()
    private val actors = mutableListOf<UnitDetail_>()
    private val filterList = mutableListOf<UnitDetail_>()
    private val mOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        //val item = v.tag as UnitDetail_
        // Notify the active callbacks interface (the activity, if the fragment is attached to
        // one) that an item has been selected.
        //mListener?.onHouseKeepingListFragmentInteraction(item.unitDetails!!.priorReservationId,"Prior RES Detail")
    }
    companion object {
        internal var mAssignedUsersDialog: AssignedUsersDialog? = null
    }

    init {
        actors.addAll(initHouseKeepingList)
        filterList.addAll(initHouseKeepingList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_housekeeping, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val unitDetail = actors[position]

            if (unitDetail.unitDetails != null) {
                holder.munitTypeName.text = unitDetail.unitDetails!!.unitTypeName
                holder.mpropertyNickName.text = unitDetail.unitDetails!!.propertyNickName
                holder.munitName.text = unitDetail.unitDetails!!.unitName
                holder.mpriorResGuestName.text = unitDetail.unitDetails!!.priorResGuestName
                holder.mpriorResCheckInDate.text = unitDetail.unitDetails!!.priorResCheckInDate
                holder.mpriorResCheckOutDate.text = unitDetail.unitDetails!!.priorResCheckOutDate
                holder.mpriorResGuests.text = "${unitDetail.unitDetails!!.priorResGuests} Persons"
                holder.mpriorUnitStatus.text = unitDetail.unitDetails!!.priorUnitStatus
                holder.mnextResGuestName.text = unitDetail.unitDetails!!.nextResGuestName
                holder.mnextResCheckInDate.text = unitDetail.unitDetails!!.nextResCheckInDate
                holder.mnextResCheckOutDate.text = unitDetail.unitDetails!!.nextResCheckOutDate
                holder.mnextResGuests.text = "${unitDetail.unitDetails!!.nextResGuests} Persons"
                holder.mnextUnitStatus.text = unitDetail.unitDetails!!.nextUnitStatus

                if (unitDetail.unitDetails!!.housekeepingInChargeInitials != "") {
                    holder.houseKeepingInCharge.visibility = View.VISIBLE
                    holder.mtv_housekeepingInChargeInitials.text = unitDetail.unitDetails!!.housekeepingInChargeInitials
                }

                when {
                    unitDetail.unitDetails!!.unitTypeName == "Apartment" -> holder.munitTypeName.setBackgroundColor(Color.parseColor("#DDDEF7"))
                    unitDetail.unitDetails!!.unitTypeName == "Cottage" -> holder.munitTypeName.setBackgroundColor(Color.parseColor("#E5FDF4"))
                    unitDetail.unitDetails!!.unitTypeName == "Villa" -> holder.munitTypeName.setBackgroundColor(Color.parseColor("#DDFDCE"))
                    unitDetail.unitDetails!!.unitTypeName == "Lodge" -> holder.munitTypeName.setBackgroundColor(Color.parseColor("#C4FFC9"))
                    unitDetail.unitDetails!!.unitTypeName == "1 BR Superior" -> holder.munitTypeName.setBackgroundColor(Color.parseColor("#FFE7C4"))
                    unitDetail.unitDetails!!.unitTypeName == "House" -> holder.munitTypeName.setBackgroundColor(Color.parseColor("#F1EDBD"))
                    else -> holder.munitTypeName.setBackgroundColor(Color.parseColor("#FFE7C4"))
                }

                val statusTypeIdDirty: Int = unitDetail.unitDetails!!.taskstatusMetaData!![0].statusTypeId
                holder.mstatusNameDirty.text = unitDetail.unitDetails!!.taskstatusMetaData!![0].statusName
                val statusTypeIdClean: Int = unitDetail.unitDetails!!.taskstatusMetaData!![1].statusTypeId
                holder.mstatusNameClean.text = unitDetail.unitDetails!!.taskstatusMetaData!![1].statusName
                val statusTypeIdInspected: Int = unitDetail.unitDetails!!.taskstatusMetaData!![2].statusTypeId
                holder.mstatusNameInspected.text = unitDetail.unitDetails!!.taskstatusMetaData!![2].statusName
                val statusTypeId: Int = unitDetail.unitDetails!!.statusTypeId
                val taskId: Int = unitDetail.unitDetails!!.taskId
                val unitId: Int = unitDetail.unitDetails!!.unitId
                val unitTypeId: Int = unitDetail.unitDetails!!.unitTypeId
                val priorreservationId: Int = unitDetail.unitDetails!!.priorReservationId
                val nextreservationId: Int = unitDetail.unitDetails!!.nextReservationId
                holder.mstatusNameDirty.setOnClickListener {
                    holder.mstatusNameDirty.setBackgroundColor(Color.parseColor("#FFB50D"))
                    holder.mstatusNameClean.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    holder.mstatusNameInspected.setBackgroundColor(Color.parseColor("#EBEBEB"))
                    holder.mstatusNameClean.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.clean, 0, 0)
                    holder.mstatusNameClean.setTextColor(Color.parseColor("#89000000"))
                    holder.mstatusNameInspected.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.inspected, 0, 0)
                    holder.mstatusNameInspected.setTextColor(Color.parseColor("#89000000"))
                    holder.mstatusNameInspected.isEnabled = false
                    storedProcedureCalls.getUpdateTaskStatus("updateTaskStatus", statusTypeIdDirty, taskId, activity)
                }

                holder.mstatusNameClean.setOnClickListener {
                    holder.mstatusNameDirty.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    holder.mstatusNameClean.setBackgroundColor(Color.parseColor("#8A9EDE"))
                    holder.mstatusNameInspected.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    holder.mstatusNameInspected.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.inspected, 0, 0)
                    holder.mstatusNameInspected.setTextColor(Color.parseColor("#89000000"))
                    holder.mstatusNameDirty.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dirty, 0, 0)
                    holder.mstatusNameDirty.setTextColor(Color.parseColor("#89000000"))
                    holder.mstatusNameInspected.isEnabled = true
                    storedProcedureCalls.getUpdateTaskStatus("updateTaskStatus", statusTypeIdClean, taskId, activity)
                }

                holder.mstatusNameInspected.setOnClickListener {
                    holder.mstatusNameDirty.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    holder.mstatusNameClean.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    holder.mstatusNameInspected.setBackgroundColor(Color.parseColor("#95D171"))
                    holder.mstatusNameClean.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.clean, 0, 0)
                    holder.mstatusNameClean.setTextColor(Color.parseColor("#89000000"))
                    holder.mstatusNameDirty.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dirty, 0, 0)
                    holder.mstatusNameDirty.setTextColor(Color.parseColor("#89000000"))
                    storedProcedureCalls.getUpdateTaskStatus("updateTaskStatus", statusTypeIdInspected, taskId, activity)
                }

                holder.mpriorResDetail.setOnClickListener {
                    if (priorreservationId != 0) {
                        mListener?.onHouseKeepingListFragmentInteraction(priorreservationId, "Prior RES Detail")
                    } else {
                        Toast.makeText(activity, "No Prior Reservation", Toast.LENGTH_SHORT).show()
                    }
                }

                holder.mnextResDetail.setOnClickListener {
                    if (nextreservationId != 0) {
                        mListener?.onHouseKeepingListFragmentInteraction(nextreservationId, "Next RES Detail")
                    } else {
                        Toast.makeText(activity, "No Next Reservation", Toast.LENGTH_SHORT).show()
                    }
                }

                val statusTypeName: String = unitDetail.unitDetails!!.statusTypeName
                when (statusTypeName) {
                    "Dirty" -> {
                        holder.mstatusNameDirty.setBackgroundColor(Color.parseColor("#FFB50D"))
                        if (unitDetail.assignedUsers!!.isEmpty()) {
                            holder.mstatusNameClean.setBackgroundColor(Color.parseColor("#EBEBEB"))
                            holder.mstatusNameInspected.setBackgroundColor(Color.parseColor("#EBEBEB"))
                        }else{
                            holder.mstatusNameClean.setBackgroundColor(Color.parseColor("#FFFFFF"))
                            holder.mstatusNameInspected.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        }
                        holder.mstatusNameDirty.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dirty_white, 0, 0)
                        holder.mstatusNameDirty.setTextColor(Color.parseColor("#FFFFFF"))
                        holder.mstatusNameClean.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.clean, 0, 0)
                        holder.mstatusNameClean.setTextColor(Color.parseColor("#89000000"))
                        holder.mstatusNameInspected.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.inspected, 0, 0)
                        holder.mstatusNameInspected.setTextColor(Color.parseColor("#89000000"))
                        holder.mstatusNameInspected.isEnabled = false
                    }
                    "Clean" -> {
                        holder.mstatusNameDirty.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        holder.mstatusNameClean.setBackgroundColor(Color.parseColor("#8A9EDE"))
                        holder.mstatusNameInspected.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        holder.mstatusNameClean.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.clean_white, 0, 0)
                        holder.mstatusNameClean.setTextColor(Color.parseColor("#FFFFFF"))
                        holder.mstatusNameInspected.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.inspected, 0, 0)
                        holder.mstatusNameInspected.setTextColor(Color.parseColor("#89000000"))
                        holder.mstatusNameDirty.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dirty, 0, 0)
                        holder.mstatusNameDirty.setTextColor(Color.parseColor("#89000000"))
                        holder.mstatusNameInspected.isEnabled = true
                    }
                    "Inspected" -> {
                        holder.mstatusNameDirty.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        holder.mstatusNameClean.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        holder.mstatusNameInspected.setBackgroundColor(Color.parseColor("#95D171"))
                        holder.mstatusNameInspected.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.inspected_white, 0, 0)
                        holder.mstatusNameInspected.setTextColor(Color.parseColor("#FFFFFF"))
                        holder.mstatusNameClean.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.clean, 0, 0)
                        holder.mstatusNameClean.setTextColor(Color.parseColor("#89000000"))
                        holder.mstatusNameDirty.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dirty, 0, 0)
                        holder.mstatusNameDirty.setTextColor(Color.parseColor("#89000000"))
                    }
                }
            }
            if (unitDetail.assignedUsers != null) {
                val assignedUserList = unitDetail.assignedUsers
                val taskId: Int = unitDetail.unitDetails!!.taskId
                if (unitDetail.assignedUsers!!.isEmpty()) {
                    holder.mstatusNameClean.isEnabled = false
                    holder.mstatusNameInspected.isEnabled = false
                } else {
                    holder.mstatusNameClean.isEnabled = true
                    holder.mstatusNameInspected.isEnabled = true
                }
                holder.massignedUser.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
                holder.massignedUser.adapter = HorizontalAdapter(assignedUserList)

                if (userList != null) {
                    holder.maddAssignedUser!!
                    holder.maddAssignedUser!!.setOnClickListener {
                        mAssignedUsersDialog = AssignedUsersDialog(context, taskId, userList, assignedUserList, position, activity)
                        mAssignedUsersDialog!!.show()
                    }
                }
            }

            with(holder.mView) {
                tag = unitDetail
                setOnClickListener(mOnClickListener)
            }
    }

    override fun getItemCount(): Int = actors.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val munitTypeName: TextView = mView.unitTypeName
        val mpropertyNickName: TextView = mView.propertyNickName
        val munitName: TextView = mView.unitName
        val mpriorResGuestName: TextView = mView.priorResGuestName
        val mpriorResCheckInDate: TextView = mView.priorResCheckInDate
        val mpriorResCheckOutDate: TextView = mView.priorResCheckOutDate
        val mpriorResGuests: TextView = mView.priorResGuests
        val mpriorUnitStatus: TextView = mView.priorUnitStatus
        val mnextResGuestName: TextView = mView.nextResGuestName
        val mnextResCheckInDate: TextView = mView.nextResCheckInDate
        val mnextResCheckOutDate: TextView = mView.nextResCheckOutDate
        val mnextResGuests: TextView = mView.nextResGuests
        val mnextUnitStatus: TextView = mView.nextUnitStatus
        val mstatusNameDirty: TextView = mView.statusNameDirty
        val mstatusNameClean: TextView = mView.statusNameClean
        val mstatusNameInspected: TextView = mView.statusNameInspected
        var maddAssignedUser: ImageView? = mView.add_assigned_user
        var massignedUser: RecyclerView = mView.rv_assignedUser
        var mpriorResDetail: LinearLayout = mView.priorResDetail
        var mnextResDetail: LinearLayout = mView.nextResDetail
        var miv_housekeepingInChargeProfileImage : ImageView? = mView.iv_housekeepingInChargeProfileImage
        var mtv_housekeepingInChargeInitials : TextView = mView.tv_housekeepingInChargeInitials
        var houseKeepingInCharge: MaterialCardView = mView.houseKeepingInCharge

        override fun toString(): String {
            return super.toString() + " '" + mpropertyNickName.text + "'"
        }
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        actors.clear()
        if (charText.isEmpty()) {
            actors.addAll(filterList)
            Log.e("actorslist", actors.size.toString())
            Log.e("filterlist", filterList.size.toString())
        } else {
            for (wp in filterList) {
                if (wp.unitDetails!!.houseKeeperStatus.toLowerCase(Locale.getDefault()).contains(charText) || wp.unitDetails!!.priorResGuestName.toLowerCase(Locale.getDefault()).contains(charText) || wp.unitDetails!!.nextResGuestName.toLowerCase(Locale.getDefault()).contains(charText) || wp.unitDetails!!.statusTypeName.toLowerCase(Locale.getDefault()).contains(charText) || wp.unitDetails!!.unitTypeName.toLowerCase(Locale.getDefault()).contains(charText)) {
                    actors.add(wp)
                    Log.e("actorslistinside", actors.size.toString())
                    Log.e("filterlistinside", filterList.size.toString())
                }
            }
        }
        notifyDataSetChanged()
    }
}


