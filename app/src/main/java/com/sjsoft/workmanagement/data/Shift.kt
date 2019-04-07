package com.sjsoft.workmanagement.data

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

data class Shift constructor(
    val id: String,
    val start: String,
    var end: String = "",
    val startLatitude: String,
    val startLongitude: String,
    var endLatitude: String = "",
    var endLongitude: String = "",
    val image: String
) {

    fun getStartDateTime(): String {
        return getTimeString(start)
    }

    fun getEndDateTime(): String {
        return getTimeString(end)
    }

    fun getTimeString(date: String): String {
        if (!date.isEmpty()) {
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            return format.format(df.parse(date))
        } else
            return ""
    }

    fun isEnded(): Boolean {
        return !end.isEmpty() && !end.isEmpty()
    }

    fun getDuration(): String? {
        if (isEnded()) {
            val milliseconds = getTimeInMilli(end) - getTimeInMilli(start)
            if (milliseconds >= 0) {
                val seconds = (milliseconds / 1000) % 60
                val minutes = ((milliseconds / (1000 * 60)) % 60)
                val hours = ((milliseconds / (1000 * 60 * 60)) % 24)

                return if (hours > 0)
                    "${hours}h ${minutes}m ${seconds}s"
                else if (minutes > 0)
                    "${minutes}m ${seconds}s"
                else
                    "${seconds}s"
            } else {
                return ("error")
            }
        }
        return null
    }

    fun getTimeInMilli(iso9601: String): Long {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        return df.parse(iso9601).time
    }

    private fun twoDigitString(number: Long): String {
        if (number == 0L) {
            return "00"
        }

        return if (number / 10 == 0L) {
            "0$number"
        } else number.toString()

    }
}