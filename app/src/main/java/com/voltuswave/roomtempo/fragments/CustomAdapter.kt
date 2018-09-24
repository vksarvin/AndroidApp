package com.voltuswave.roomtempo.fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.UsersList



class CustomAdapter(private val context: Context,
                    private val dataSource: List<UsersList>?) : BaseAdapter() {

    private var selectedUsersList = LinkedHashMap<Int, UsersList>()

    private class ViewHolder(view: View) {
        var assign_user_check_View: CheckBox? = null
        var titleTextView: TextView? = null
        var thumbnailImageView: ImageView? = null

        init {
            assign_user_check_View = view.findViewById(R.id.assigned_user_check)
            titleTextView = view.findViewById(R.id.assigned_user)
            thumbnailImageView = view.findViewById(R.id.assigned_user_thumbnail)
        }
    }

    init {
        selectedUsersList = LinkedHashMap()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val holder: ViewHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.row_item, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            view = convertView
            holder = view.tag as ViewHolder
        }
        val usersList = dataSource!![position]
        holder.titleTextView!!.text = usersList.userFullName
        holder.assign_user_check_View!!.setTag(R.integer.btnplusview, view)
        holder.assign_user_check_View!!.tag = position

        holder.assign_user_check_View!!.setOnCheckedChangeListener { _, isChecked ->
            val pos = holder.assign_user_check_View!!.tag as Int
            if (isChecked) {
                val tempview = holder.assign_user_check_View!!.getTag(R.integer.btnplusview) as View?
                Log.e("CB Position Clicked : ", pos.toString())
                selectedUsersList[usersList.userId] = dataSource[pos]
            } else {
                Log.e("CB Position UnChecked: ", pos.toString())
                selectedUsersList.remove(dataSource[pos].userId)
            }
        }
        holder.assign_user_check_View!!.isChecked = usersList.isChecked
        if(holder.assign_user_check_View!!.isChecked){
            selectedUsersList[usersList.userId] = usersList
        }else{
            selectedUsersList.remove(usersList.userId)
        }

        return view as View
    }

    fun getSelectedUsers(): LinkedHashMap<Int,UsersList> {
        return selectedUsersList
    }

    override fun getItem(position: Int): Any {
        return dataSource!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
