package com.shinyj.githubsearch.util

import kotlin.math.ln

object StringUtils {

    fun withSuffix(number: Long) : String{
        if(number < 1000)
            return number.toString()

        val exp = (ln(number.toDouble()) / ln(1000.toDouble())).toInt()
        return String.format("%.1f %c", number/Math.pow(1000.0, exp.toDouble()), "kMGTPE"[exp -1])
    }
}