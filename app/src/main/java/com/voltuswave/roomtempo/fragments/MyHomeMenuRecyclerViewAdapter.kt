package com.voltuswave.roomtempo.fragments


import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.fragments.UserHomeFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_homemenu.view.*



/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyHomeMenuRecyclerViewAdapter(
        private val mValues: List<String>,private var context: Context,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyHomeMenuRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as String
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_homemenu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.contentCardView.text = item
        if(item == "Reservation List"){
            holder.contentCardView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.res_icon,0,0)
            holder.homeCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.reservationList))
        }else{
            holder.contentCardView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.hk_icon,0,0)
            holder.homeCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.houseKeeping))
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var contentCardView: TextView = mView.content
        var homeCardView: CardView = mView.home_card

        override fun toString(): String {
            return super.toString() + " '" + contentCardView.text + "'"
        }
    }
}
