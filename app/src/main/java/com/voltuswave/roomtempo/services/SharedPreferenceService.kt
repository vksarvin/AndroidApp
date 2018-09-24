package com.voltuswave.roomtempo.services

import android.content.Context
import android.util.Log
import com.voltuswave.roomtempo.models.Profile
import com.voltuswave.roomtempo.utils.StaticConstants
import com.voltuswave.roomtempo.utils.StringConstants
import java.text.SimpleDateFormat
import java.util.*

object SharedPreferenceService {

    /**
     * Function to initialize user session in shared preference memory
     * @param context
     * @param key
     * @param value
     */
    fun storeUserDetails(context: Context, key: String, value: String) {
        val sharedPreferences = context.getSharedPreferences(StaticConstants.SESSION_OBJECT, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        try {

            // Store key - value pair in Shared Preference
            editor.putString(key, value)

        } catch (error: Throwable) {
            Log.e(StringConstants.EXCEPTION_ERROR, error.toString())
        }

        // Commit to Shared Preference Cache
        editor.apply()
    }


    /**
     * Function to save Profile details from Database to Shared Preference Memory
     * @param context
     * @param profile
     */
    fun storeProfileToSharedPreference(context: Context, profile: Array<Profile>) {

        val sharedPreferences = context.getSharedPreferences(StaticConstants.SESSION_OBJECT, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        try {

            editor.putString(StringConstants.key_userFirstName, profile[0].userFirstName)
            editor.putString(StringConstants.key_failedLoginAttempt, profile[0].failedLoginAttempt.toString())
            editor.putString(StringConstants.key_token, profile[0].token)
            editor.putString(StringConstants.key_email, profile[0].email)
            editor.putString(StringConstants.key_userID, profile[0].userID.toString())
            editor.putString(StringConstants.key_FailedLoginTime, profile[0].failedLoginTime)
            editor.putString(StringConstants.key_clientName, profile[0].clientName)
            editor.putString(StringConstants.key_clientCode, profile[0].clientCode)
            editor.putString(StringConstants.key_userLastName, profile[0].userLastName)
            editor.putString(StringConstants.key_clientID, profile[0].clientID.toString())
            editor.putString(StringConstants.key_loginID, profile[0].loginID)
            editor.putString(StringConstants.key_PropertyId, profile[0].propertyId.toString())

        } catch (error: Exception) {

            // Handle Error
        }

        // Commit to Shared Preference Cache
        editor.apply()
    }


    /**
     * Function to fetch value corresponding to key in Shared Preference
     * @param context
     * @param key
     * @return
     */
    fun getValue(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences(StaticConstants.SESSION_OBJECT, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun getDateValue(context: Context, key: String): String? {
         val cal = Calendar.getInstance()
         val payloadFormat = "yyyy-MM-dd" // mention the format you need
         val sdf = SimpleDateFormat(payloadFormat, Locale.US)
        val sharedPreferences = context.getSharedPreferences(StaticConstants.SESSION_OBJECT, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, sdf.format(cal.time))
    }

    fun getPropertyIdValue(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences(StaticConstants.SESSION_OBJECT, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "0")
    }

    fun getUnitTypeIdValue(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences(StaticConstants.SESSION_OBJECT, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "0")
    }

    /**
     * Function to remove value corresponding to key in Shared Preference
     * @param context
     * @param key
     * @return
     */
    fun removeValue(context: Context, key: String){
        val sharedPreferences = context.getSharedPreferences(StaticConstants.SESSION_OBJECT, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * Function to clear user session from shared preference memory
     * @param context
     */
    fun destroyUserSession(context: Context) {
        val preferences = context.getSharedPreferences(StaticConstants.SESSION_OBJECT, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}
