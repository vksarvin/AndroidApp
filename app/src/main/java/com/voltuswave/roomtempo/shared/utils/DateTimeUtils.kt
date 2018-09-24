package com.voltuswave.roomtempo.shared.utils

import android.content.Context
import com.voltuswave.roomtempo.utils.StaticConstants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    /**
     * Function to get current systems date
     * @param context
     * @return
     */
    fun getDate(context: Context): String {

        val date = Date()
        val dateFormat = SimpleDateFormat(StaticConstants.DEFAULT_DATE_TIME_FORMAT)
        return dateFormat.format(date)
    }


    /**
     * Function to get current systems simple date
     * @param context
     * @return
     */
    fun getSimpleDate(context: Context): String {

        val date = Date()
        val dateFormat = SimpleDateFormat(StaticConstants.SIMPLE_DATE_FORMAT)
        return dateFormat.format(date)
    }

    /**
     * Function to get current systems time
     * @param context
     * @return
     */
    fun getTime(context: Context): String {

        val date = Date()
        val dateFormat = SimpleDateFormat(StaticConstants.DEFAULT_TIME_FORMAT)
        return dateFormat.format(date)
    }


    /**
     * Function to get current systems time (Chat)
     * @param context
     * @return
     */
    fun getChatTime(context: Context): String {

        val date = Date()
        val dateFormat = SimpleDateFormat(StaticConstants.CHAT_TIME_FORMAT)
        return dateFormat.format(date)
    }


    /**
     * Function to convert timestamp to date
     * @param timestamp
     * @return
     */
    fun convertTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat(StaticConstants.SIMPLE_DATE_FORMAT)
        return dateFormat.format(date)
    }

    /**
     * Function to convert timestamp to short date
     * @param timestamp
     * @return
     */
    fun convertTimestampToShortDate(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat(StaticConstants.SHORT_DATE_FORMAT)
        return dateFormat.format(date)
    }

    /**
     * Function to convert timestamp to time
     * @param timestamp
     * @return
     */
    fun convertTimestampToTime(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat(StaticConstants.DEFAULT_TIME_FORMAT)
        return dateFormat.format(date)
    }


    /**
     * Function to convert timestamp to UTC time
     * @param timestamp
     * @return
     */
    fun convertTimestampToUTCTime(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat(StaticConstants.UTC_TIME_FORMAT)
        return dateFormat.format(date)
    }

    /**
     * Function to convert timestamp to UTC
     * @param timestamp
     * @return
     */
    fun convertTimestampToUTC(timestamp: Long): String {

        val date = Date(timestamp)
        val format = SimpleDateFormat(StaticConstants.UTC_DATE_TIME_FORMAT)
        format.timeZone = TimeZone.getTimeZone("UTC")
        val formatted = format.format(date)
        return formatted.replace(" ", "T")
    }


    /**
     * Function to convert timestamp to Login Date
     * @param timestamp
     * @return
     */
    fun convertTimestampToLoginDate(timestamp: Long): String {

        val date = Date(timestamp)
        val format = SimpleDateFormat(StaticConstants.LOGIN_DATE_TIME_FORMAT)
        return format.format(date)
    }


    /**
     * Function to convert login date to timestamp
     * @return
     */
    fun convertDateLoginToTimestamp(date: String): Long {

        var timestamp: Long = 0
        val result: Date
        val df = SimpleDateFormat(StaticConstants.LOGIN_DATE_TIME_FORMAT)

        try {

            result = df.parse(date)
            timestamp = result.time

        } catch (e: ParseException) {

            // TODO: Handle Exception
        }

        return timestamp
    }

    /**
     * Function to convert UTC date to timestamp
     * @return
     */
    fun convertDateUTCToTimestamp(dateUTC: String): Long {
        var dateUTC = dateUTC

        var timestamp: Long = 0
        val result: Date
        dateUTC = dateUTC.replace("T", " ")
        val df = SimpleDateFormat(StaticConstants.UTC_DATE_TIME_FORMAT_SHORTHAND)

        try {

            result = df.parse(dateUTC)
            timestamp = result.time

        } catch (e: ParseException) {

            // TODO: Handle Exception
        }

        return timestamp
    }


    /**
     * Function to convert UTC date to timestamp
     * @return
     */
    fun convertDateSimpleToTimestamp(dateUTC: String): Long {

        var timestamp: Long = 0
        val result: Date
        val df = SimpleDateFormat(StaticConstants.SIMPLE_DATE_FORMAT)

        try {

            result = df.parse(dateUTC)
            timestamp = result.time

        } catch (e: ParseException) {

            // TODO: Handle Exception
        }

        return timestamp
    }


    /**
     * Function to convert date to timestamp
     * @param date
     * @return
     */
    fun convertDateToTimestamp(date: String): Long {

        var timestamp = 0L
        val result: Date
        val df = SimpleDateFormat(StaticConstants.DEFAULT_DATE_TIME_FORMAT)

        try {

            result = df.parse(date)
            timestamp = result.time

        } catch (e: ParseException) {

            // TODO: Handle Exception
        }

        return timestamp
    }


    /**
     * Fubction to convert date from Default Format to Graph Format
     * @param date
     * @return
     */
    fun convertDateFormat(date: String): String {

        val timestamp = convertDateToTimestamp(date)
        val dateFormat = SimpleDateFormat(StaticConstants.GRAPH_DATE_TIME_FORMAT)
        return dateFormat.format(timestamp)
    }


    /**
     * Function to convert year, month, day, hour and minute to timestamp
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return
     */
    fun convertTimeToTimestamp(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long {

        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        c.set(Calendar.HOUR, hour)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        return c.timeInMillis
    }

    /**
     * Function to get current timestamp of the system
     * @return
     */
    fun getCurrentTimestamp(): Long {
        val date = Date()
        return date.time
    }

    /**
     * Function to calculate age from date of birth
     * @param dobTimestamp
     * @return
     */
    fun calculateAgeFromDOB(dobTimestamp: Long): String {


        val dob = Calendar.getInstance()
        dob.timeInMillis = dobTimestamp
        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)


        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--
        }

        return age.toString()
    }

    fun addDay(date: Date, i: Int): Long {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DAY_OF_YEAR, i)
        return cal.timeInMillis
    }

    fun addMonth(date: Date, i: Int): Long {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MONTH, i)
        return cal.timeInMillis
    }

    fun addYear(date: Date, i: Int): Long {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.YEAR, i)
        return cal.timeInMillis
    }

    fun getCalculatedStringDate(duration: String): String {
        var calDateString = ""
        var count = 0
        //duration = "3 months";
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat(StaticConstants.DEFAULT_DATE_TIME_FORMAT)

        if (duration.contains("1 hr") || duration.contains("few hours")) {
            calDateString = dateFormat.format(addDay(calendar.time, -1))
        } else if (duration.contains("day")) {
            count = Integer.parseInt("" + duration[0])
            calDateString = dateFormat.format(addDay(calendar.time, -count))
        } else if (duration.contains("week")) {
            count = Integer.parseInt("" + duration[0]) * 7
            calDateString = dateFormat.format(addDay(calendar.time, -count))
        } else if (duration.contains("month")) {
            count = Integer.parseInt("" + duration[0])
            calDateString = dateFormat.format(addMonth(calendar.time, -count))
        } else if (duration.contains("yr")) {
            count = Integer.parseInt("" + duration[0])
            calDateString = dateFormat.format(addYear(calendar.time, -count))
        } else if (duration.contains("more than 10 years")) {
            calDateString = "more than 10 years"
        }

        return calDateString

    }


    fun getStringDateToDuration(strDate: String): String {
        var duration = ""
        //        strDate = "5 02 2016";
        var timestamp = 0L
        var result: Date? = null
        val df = SimpleDateFormat(StaticConstants.DEFAULT_DATE_TIME_FORMAT)

        try {

            result = df.parse(strDate)
            timestamp = result!!.time

        } catch (e: ParseException) {

            // TODO: Handle Exception
        }

        if (strDate.contains("more than 10 years")) {
            duration = "more than 10 years"
        } else {
            val MILLISECS_PER_DAY = (24 * 60 * 60 * 1000).toLong()
            val dateDiff = System.currentTimeMillis() - timestamp
            val longDay = dateDiff / MILLISECS_PER_DAY
            var daysValue = Math.abs(longDay).toInt()
            if (daysValue <= 6) {
                duration = if (daysValue <= 1) "1 day" else daysValue.toString() + " days"
            } else if (daysValue < 30) {
                val weekValue = daysValue / 7
                duration = if (weekValue == 1) weekValue.toString() + " week" else weekValue.toString() + " weeks"
            } else if (daysValue < 365) {
                val monthValue = daysValue / 30
                daysValue = daysValue - monthValue * 30
                val weekValue = daysValue / 7
                if (weekValue > 0) {
                    duration = if (monthValue == 1) monthValue.toString() + " month" else monthValue.toString() + " months " + if (weekValue == 1) weekValue.toString() + " week" else weekValue.toString() + " weeks"
                } else {
                    duration = if (monthValue == 1) monthValue.toString() + " month" else monthValue.toString() + " months"
                }
            } else if (daysValue >= 365 && daysValue <= 365 * 10) {
                val yearValue = daysValue / 365
                val monthValue = (daysValue - yearValue * 365) / 30
                if (monthValue > 0) {
                    duration = if (yearValue == 1) yearValue.toString() + " yr" else yearValue.toString() + " yrs " + if (monthValue == 1) monthValue.toString() + " month" else monthValue.toString() + " months "
                } else {
                    duration = if (yearValue == 1) yearValue.toString() + " yr" else yearValue.toString() + " yrs"
                }
            } else if (daysValue > 365 * 10) {
                duration = "more than 10 years"
            }
        }

        return duration
    }

    fun getValidateDate(firstDate: String, secondDate: String): Boolean {
        var temp = false
        var timestamp1 = 0L
        var timestamp2 = 0L
        var result1: Date? = null
        var result2: Date? = null
        val df = SimpleDateFormat(StaticConstants.DEFAULT_DATE_TIME_FORMAT)
        try {
            result1 = df.parse(firstDate)
            timestamp1 = result1!!.time
            result2 = df.parse(secondDate)
            timestamp2 = result2!!.time
        } catch (e: ParseException) {
            // TODO: Handle Exception
        }

        if (timestamp1 > timestamp2) {
            temp = true
        }
        return temp
    }


    /**
     * Function to convert date to timestamp
     * @param date
     * @return
     */
    fun convertDateToLongForHealthBook(date: String): Long {

        var timestamp = 0L
        val result: Date
        val df = SimpleDateFormat(StaticConstants.SIMPLE_DATE_FORMAT)

        try {

            result = df.parse(date)
            timestamp = result.time

        } catch (e: ParseException) {

            // TODO: Handle Exception
        }

        return timestamp
    }

    /*
    * Function to convert String to UTC
    * @param timestamp
    * @return
            */
    fun convertStringToUTC(dateString: String): String {
        val str_date = "$dateString 00:00:00"
        var returnDate = ""
        var df = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date
        try {
            date = df.parse(str_date)
            df = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            returnDate = df.format(date)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return returnDate.replace(" ", "T")
    }

    /*
    * Function to convert String to UTC
    * @param timestamp
    * @return
            */
    fun convertUTCToString(dateString: String): String {
        val str_date = dateString.replace("T", " ")
        var returnDate = ""
        var df = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        //df.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.timeZone = TimeZone.getDefault()
        val date: Date
        try {
            date = df.parse(str_date)
            df = SimpleDateFormat("dd-MM-yyyy")
            returnDate = df.format(date)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return returnDate
    }


    /**
     * Function to calculate start date from duration
     */
    fun calculateTimestampFromDuration(duration: Int): Long {

        var timestamp = getCurrentTimestamp()
        val calendar = Calendar.getInstance()

        when (duration) {

            0 -> {
                calendar.time = Date()
                calendar.add(Calendar.HOUR_OF_DAY, -1)
                timestamp = calendar.time.time
            }

            1 -> {
                calendar.time = Date()
                calendar.add(Calendar.HOUR_OF_DAY, -4)
                timestamp = calendar.time.time
            }

            2 -> {
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_WEEK, -1)
                timestamp = calendar.time.time
            }

            3 -> {
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_WEEK, -2)
                timestamp = calendar.time.time
            }

            4 -> {
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_WEEK, -3)
                timestamp = calendar.time.time
            }

            5 -> {
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_WEEK, -4)
                timestamp = calendar.time.time
            }

            6 -> {
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_WEEK, -5)
                timestamp = calendar.time.time
            }

            7 -> {
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_WEEK, -6)
                timestamp = calendar.time.time
            }

            8 -> {
                calendar.time = Date()
                calendar.add(Calendar.WEEK_OF_MONTH, -1)
                timestamp = calendar.time.time
            }

            9 -> {
                calendar.time = Date()
                calendar.add(Calendar.WEEK_OF_MONTH, -2)
                timestamp = calendar.time.time
            }

            10 -> {
                calendar.time = Date()
                calendar.add(Calendar.WEEK_OF_MONTH, -3)
                timestamp = calendar.time.time
            }

            11 -> {
                calendar.time = Date()
                calendar.add(Calendar.MONTH, -1)
                timestamp = calendar.time.time
            }

            12 -> {
                calendar.time = Date()
                calendar.add(Calendar.MONTH, -2)
                timestamp = calendar.time.time
            }

            13 -> {
                calendar.time = Date()
                calendar.add(Calendar.MONTH, -3)
                timestamp = calendar.time.time
            }

            14 -> {
                calendar.time = Date()
                calendar.add(Calendar.MONTH, -6)
                timestamp = calendar.time.time
            }

            15 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -1)
                timestamp = calendar.time.time
            }

            16 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -2)
                timestamp = calendar.time.time
            }

            17 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -3)
                timestamp = calendar.time.time
            }

            18 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -4)
                timestamp = calendar.time.time
            }

            19 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -5)
                timestamp = calendar.time.time
            }

            20 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -6)
                timestamp = calendar.time.time
            }

            21 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -7)
                timestamp = calendar.time.time
            }

            22 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -8)
                timestamp = calendar.time.time
            }

            23 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -9)
                timestamp = calendar.time.time
            }

            24 -> {
                calendar.time = Date()
                calendar.add(Calendar.YEAR, -10)
                timestamp = calendar.time.time
            }

            else -> {
            }
        }

        return timestamp
    }


    /**
     * Function to calculate Duration From Start Date
     * @param startTimestamp
     * @return
     */
    fun calculateDurationFromStartDate(startTimestamp: Long): String {

        var duration: String? = null
        val start = Calendar.getInstance()
        start.time = Date(startTimestamp)
        val today = Calendar.getInstance()

        /**
         * Calculate Difference in Year
         */
        var differenceInYears = today.get(Calendar.YEAR) - start.get(Calendar.YEAR)
        if (today.get(Calendar.MONTH) < start.get(Calendar.MONTH)) {
            differenceInYears--

        } else if (today.get(Calendar.MONTH) == start.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < start.get(Calendar.DAY_OF_MONTH)) {
            differenceInYears--
        }

        /**
         * Calculate Duration
         */
        if (differenceInYears > 0) {

            if (differenceInYears <= 9 && differenceInYears != 1) {

                duration = differenceInYears.toString() + " Years"

            } else if (differenceInYears == 1) {

                duration = differenceInYears.toString() + " Year"

            } else {

                duration = "More than 10 Years"
            }
        } else {

            // In case Difference is not in Years
            var differenceInMonths = today.get(Calendar.MONTH) - start.get(Calendar.MONTH)
            if (today.get(Calendar.YEAR) > start.get(Calendar.YEAR)) {
                differenceInMonths = differenceInMonths + 12
            }

            if (differenceInMonths > 0) {

                if (differenceInMonths > 3 && differenceInMonths < 9) {

                    duration = "6 Months"

                } else if (differenceInMonths == 1) {

                    duration = "1 Month"

                } else if (differenceInMonths > 9) {

                    duration = "1 Year"

                } else {

                    duration = differenceInMonths.toString() + " Months"
                }

            } else {

                // In case Difference is not in Months
                var differenceInWeeks = today.get(Calendar.WEEK_OF_MONTH) - start.get(Calendar.WEEK_OF_MONTH)
                if (today.get(Calendar.MONTH) > start.get(Calendar.MONTH)) {

                    differenceInWeeks = differenceInWeeks + 4
                }
                if (today.get(Calendar.DAY_OF_WEEK) < start.get(Calendar.DAY_OF_WEEK)) {
                    differenceInWeeks--
                }

                if (differenceInWeeks > 0) {

                    if (differenceInWeeks > 3) {

                        duration = "1 Month"

                    } else if (differenceInWeeks == 1) {

                        duration = "1 Week"

                    } else {

                        duration = differenceInWeeks.toString() + " Weeks"
                    }

                } else {

                    // In case Difference is not in Weeks
                    var differenceInDays = today.get(Calendar.DAY_OF_WEEK) - start.get(Calendar.DAY_OF_WEEK)
                    if (today.get(Calendar.WEEK_OF_MONTH) > start.get(Calendar.WEEK_OF_MONTH)) {
                        differenceInDays = differenceInDays + 7
                    }

                    if (differenceInDays > 0) {

                        if (differenceInDays == 1) {

                            duration = "1 Day"

                        } else {

                            duration = differenceInDays.toString() + " Days"
                        }

                    } else {

                        // In case difference is not in days
                        var differenceInHours = today.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY)
                        if (today.get(Calendar.DAY_OF_WEEK) > start.get(Calendar.DAY_OF_WEEK)) {
                            differenceInHours = differenceInHours + 24
                        }

                        if (differenceInHours == 1) {

                            duration = "1 Hour"

                        } else {

                            duration = "Few Hours"
                        }
                    }
                }
            }
        }

        return duration
    }
}