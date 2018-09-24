package com.voltuswave.roomtempo.InternetHelper

import android.content.Context
import android.os.AsyncTask
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.utils.StaticConstants
import java.net.URL

class WaitForInternetConnectionView(private val mContext: Context, attrs: AttributeSet) : RelativeLayout(mContext, attrs), View.OnClickListener {

    private var mRefreshButton: Button? = null
    private var mProgressBar: ProgressBar? = null
    private var mWaitMessageText: TextView? = null
    private var mOnConnectionIsAvailableListener: OnConnectionIsAvailableListener? = null

    init {
        init()
    }

    fun init() {
        View.inflate(context, R.layout.view_wait_for_internet_connection, this)
        mWaitMessageText = findViewById(R.id.text_wait_message)
        mRefreshButton = findViewById(R.id.button_refresh)
        mRefreshButton!!.visibility = View.GONE
        mRefreshButton!!.setOnClickListener(this)
        mProgressBar = findViewById(R.id.progress)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_refresh -> {
                setWaitMessageNormal()
                if (mOnConnectionIsAvailableListener != null)
                    checkInternetConnection(mOnConnectionIsAvailableListener!!)
            }
        }
    }

    private fun setWaitMessageNegative() {
        mProgressBar!!.visibility = View.INVISIBLE
        mWaitMessageText!!.setText(R.string.wait_int)
    }

    private fun setWaitMessageNormal() {
        mProgressBar!!.visibility = View.VISIBLE
        mWaitMessageText!!.setText(R.string.wait_wait)
    }

    fun checkInternetConnection(listener: OnConnectionIsAvailableListener) {
        visibility = View.VISIBLE
        mOnConnectionIsAvailableListener = listener
        AsyncCheckInternetConnection(listener).execute()
    }


    private inner class AsyncCheckInternetConnection internal constructor(internal var mListener: OnConnectionIsAvailableListener) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                val myUrl = URL(StaticConstants.url)
                val connection = myUrl.openConnection()
                connection.connectTimeout = 5000
                connection.connect()
                return true
            } catch (e: Exception) {
                return false
            }

        }

        override fun onPostExecute(isConnected: Boolean?) {
            if (isConnected!!) {
                mListener.onConnectionIsAvailable()
            } else {
                setWaitMessageNegative()
                mRefreshButton!!.visibility = View.VISIBLE
            }
        }
    }

    fun close() {
        visibility = View.GONE
    }


    interface OnConnectionIsAvailableListener {
        fun onConnectionIsAvailable()
    }
}
