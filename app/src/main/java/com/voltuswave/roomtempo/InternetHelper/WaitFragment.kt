package com.voltuswave.roomtempo.InternetHelper

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout

import com.voltuswave.roomtempo.R

class WaitFragment : Fragment(), View.OnClickListener {

    private lateinit var mConnect: Button
    private lateinit var mProgressPanel: RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_wait, container, false)
        mConnect = v.findViewById(R.id.connect)
        mProgressPanel = v.findViewById(R.id.progress_panel)
        mConnect.setOnClickListener(this)
        return v
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.connect -> mProgressPanel.visibility = View.VISIBLE
        }
    }

    companion object {

        val ERROR_CODE = "ec"
    }
}
