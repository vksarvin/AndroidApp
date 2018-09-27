package com.voltuswave.roomtempo.utils

class StaticConstants {
    // Shared Preferences Object
    companion object {
        val SESSION_OBJECT = "SessionObject"
        val SESSION_OBJECTfORuRL = "SessionObjectFORURL"
        val SCREEN_TIMEOUT = 1000L
        var token:String? = null
        var userID:String? = null
        var clientID:String? = null
        var url: String ="http://dev.roomtempo.com/"
        var sourceId: String ="dev.roomtempo.com"

        // Timestamp Format
        val UTC_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS"
        val UTC_DATE_TIME_FORMAT_SHORTHAND = "yyyy-MM-dd hh:mm:s"
        val DEFAULT_DATE_TIME_FORMAT = "dd MMMM yyyy hh:mm:ss"
        val LOGIN_DATE_TIME_FORMAT = "yyyy-MM-dd"
        val SIMPLE_DATE_FORMAT = "dd MMMM yyyy"
        val SHORT_DATE_FORMAT = "dd MMM yyyy"
        val UTC_TIME_FORMAT = "hh:mm:ss"
        val DEFAULT_TIME_FORMAT = "hh:mm:ss a"
        val CHAT_TIME_FORMAT = "hh:mm a"
        val SIMPLE_TIME_FORMAT = "hh:mm:ss a"
        val GRAPH_DATE_TIME_FORMAT = "dd MMM"

        // Regex patterns
        val EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val MOBILE_REGEX = "\\d{10}"

        val APP_CTA_TIMEOUT = 250

        // Font Typeface
        val TYPEFACE_FONT_ROBOTO_BOLD = "fonts/Roboto-Bold.ttf"
        val TYPEFACE_FONT_ROBOTO_LIGHT = "fonts/Roboto-Light.ttf"
    }

}