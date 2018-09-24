package com.voltuswave.roomtempo.fragments

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.voltuswave.roomtempo.R

class SpinnerAdapter(private val mContext: Context?, resource: Int, objects: MutableList<String>, private val promptTitle: String) : ArrayAdapter<String>(mContext, resource, objects) {

    private var selectedItem = 0

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        val linear: LinearLayout?

        if (position == 0) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            linear = inflater.inflate(R.layout.spinner_title, parent, false) as LinearLayout

            val promptText = linear.findViewById<View>(R.id.promptTextView) as TextView
            promptText.text = promptTitle
            promptText.gravity = Gravity.CENTER
        } else {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            linear = inflater.inflate(R.layout.spinner_item_with_separator, parent, false) as LinearLayout

            val itemText = linear.findViewById<View>(R.id.myspinneritem) as TextView
            itemText.text = getItem(position)

            if (position == count - 1) {
                linear.removeView(linear.findViewById(R.id.separatorLayout))
            }

            if (position == selectedItem)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    itemText.setTextColor(mContext!!.getColor(R.color.colorPrimary))
                }

        }

        return linear
    }


    override fun getCount(): Int {
        return super.getCount() + 1
    }

    override fun getItem(position: Int): String? {
        return if (position == 0) {
            promptTitle
        } else super.getItem(position - 1)
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    fun setSelectedItem(position: Int) {
        selectedItem = position
    }

}
