package com.example.taskscheduler.constants

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object TimeConvertingFunctions {

    fun convertTimestampToDateTime(timestamp: Timestamp): Pair<String,String> {
        val dateTime = timestamp.toDate()
        val formatDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formatTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return Pair(formatDate.format(dateTime),formatTime.format(dateTime))
    }

    fun convertDateTimeToTimestamp(date: String, time: String): Timestamp {
        val dateTimeString = "$date $time"
        val format = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault())
        val date = format.parse(dateTimeString)
        return Timestamp(date!!)
    }

    // getFormatted - get values from picker
    fun getFormattedTime(now: LocalTime?): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
        return now!!.format(formatter)
    }

    fun getFormattedDate(it: Long?): String {
        val selectedDate = Date(it!!)
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = format.format(selectedDate)
        return formattedDate
    }
}