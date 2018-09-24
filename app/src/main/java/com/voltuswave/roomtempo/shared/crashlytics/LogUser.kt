package com.voltuswave.roomtempo.shared.crashlytics

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.voltuswave.roomtempo.services.SharedPreferenceService
import com.voltuswave.roomtempo.utils.StringConstants

object LogUser {

    /**
     * Log user with Crashlytics
     */
    fun logUser(context: Context) {

        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier(SharedPreferenceService.getValue(context, StringConstants.key_token))
        Crashlytics.setUserEmail(SharedPreferenceService.getValue(context, StringConstants.key_email))
        Crashlytics.setUserName(SharedPreferenceService.getValue(context, StringConstants.key_userFirstName))
    }
}