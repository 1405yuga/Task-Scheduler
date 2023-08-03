package com.example.taskscheduler.constants

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale


object TimeConvertingFunctions {

    fun convertTimestampToDateTime(timestamp: Timestamp): Pair<String, String> {
        val dateTime = timestamp.toDate()
        val formatDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formatTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return Pair(formatDate.format(dateTime), formatTime.format(dateTime))
    }

    fun convertDateTimeToTimestamp(date: String, time: String): Timestamp {
        val dateTimeString = "$date $time"
        val format = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault())
        val dateTimeFormattedString = format.parse(dateTimeString)
        return Timestamp(dateTimeFormattedString!!)
    }

    // getFormatted - get values from picker
    fun getFormattedTime(now: LocalTime?): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
        return now!!.format(formatter)
    }

    fun getFormattedDate(it: Long?): String {
        val selectedDate = Date(it!!)
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return format.format(selectedDate)
    }

    fun getDaysDifference(date1String: String, date2String: String): Long {
        val dateFormat = "dd-MM-yyyy"
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        val date1 = LocalDate.parse(date1String, formatter)
        val date2 = LocalDate.parse(date2String, formatter)

        return ChronoUnit.DAYS.between(date1, date2)
    }
}