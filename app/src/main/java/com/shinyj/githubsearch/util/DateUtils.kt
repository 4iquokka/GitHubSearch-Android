package com.shinyj.githubsearch.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {

    fun formatDateString(dateString : String) : String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.ENGLISH)
            val date = LocalDate.parse(dateString, inputFormatter)
            outputFormatter.format(date)
        } else {
            val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val outputFormatter = SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH)
            val date = inputFormatter.parse(dateString)
            outputFormatter.format(date!!)
        }
    }

}