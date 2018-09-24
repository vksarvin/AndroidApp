package com.voltuswave.roomtempo.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.FolioItem

class FolioAccountAdapter(private val folioItemArrayList: List<FolioItem>?): RecyclerView.Adapter<FolioAccountAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var folioitemname_label = view.findViewById<TextView>(R.id.folioitemname_label)
        var itemqty = view.findViewById<TextView>(R.id.itemqty)
        var perNight = view.findViewById<TextView>(R.id.perNight)
        var charges = view.findViewById<TextView>(R.id.charges)
        var star_label = view.findViewById<TextView>(R.id.star_label)
        var nights_label = view.findViewById<TextView>(R.id.nights_label)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_folio_account, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return folioItemArrayList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = folioItemArrayList!![position]
        holder.folioitemname_label.text = item.folioItemName
        if(item.itemQty == null){
            holder.star_label.visibility = View.GONE
            holder.nights_label.visibility = View.GONE
            holder.itemqty.text = ""
        }else{
            holder.star_label.visibility = View.VISIBLE
            holder.nights_label.visibility = View.VISIBLE
            holder.itemqty.text = item.itemQty.toString()
        }
        if (item.adr == null){
            holder.perNight.text = ""
        }else{
            holder.perNight.text = "($ ${item.adr} Per Night)"
        }
        holder.charges.text = "$ ${item.charges}"
    }
}