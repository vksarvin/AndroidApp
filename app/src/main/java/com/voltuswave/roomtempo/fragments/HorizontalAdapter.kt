package com.voltuswave.roomtempo.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.AssignedUser



class HorizontalAdapter(private val assignedUserList: List<AssignedUser>?): RecyclerView.Adapter<HorizontalAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var iv_assignedUserImage: ImageView = view.findViewById(R.id.iv_assignedUserImage) as ImageView
        var tv_assignedUserName: TextView = view.findViewById(R.id.tv_assignedUserName) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_assigned_user, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return assignedUserList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = assignedUserList!![position]
      //  holder.iv_assignedUserImage.setImageDrawable(R.drawable.ic_account_circle_black_24dp)
        holder.tv_assignedUserName.text = item.initials
    }

}