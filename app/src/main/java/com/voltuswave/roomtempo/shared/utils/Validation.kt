package com.voltuswave.roomtempo.shared.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.TextView
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.utils.StaticConstants
import java.util.regex.Pattern

object Validation {

    /**
     * Function to check if device has internet connection
     * @param context
     * @return
     */
    fun isConnected(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null
    }


    /**
     * Function to check if string is empty
     * @param string
     * @return
     */
    private fun isEmpty(string: String?): Boolean {

        return string == null || string.trim { it <= ' ' }.isEmpty()
    }


    /**
     * Function to regex match if email is valid
     * @param string
     * @return
     */
    fun isValidEmail(string: String): Boolean {

        val regex = StaticConstants.EMAIL_REGEX
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }


    /**
     * Function to regex match if mobile is valid
     * @param string
     * @return
     */
    fun isValidMobile(string: String): Boolean {

        val regex = StaticConstants.MOBILE_REGEX
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }


    /**
     * Function to regex match if password is valid
     * @param string
     * @return
     */
    fun isValidPassword(string: String): Boolean {

        return string.trim { it <= ' ' }.length > 5

    }

    /**
     * Function to validate date
     */
    fun validateDate(textView: TextView, context: Context): Boolean {

        var isValidPassword = false
        val text = textView.text.toString()

        // Validate Username
        if (Validation.isEmpty(text)) {

            textView.error = context.getString(R.string.err_msg_date)

        } else {

            isValidPassword = true
            textView.error = null
        }

        return isValidPassword
    }

    /**
     * Function to validate date
     */
    fun validateStartDate(textView: TextView, context: Context): Boolean {

        var isValidPassword = false
        val text = textView.text.toString()

        // Validate Username
        if (Validation.isEmpty(text)) {

            textView.error = context.getString(R.string.err_msg_start_date)

        } else {

            isValidPassword = true
            textView.error = null
        }

        return isValidPassword
    }
}