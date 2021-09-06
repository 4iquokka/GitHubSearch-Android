package com.shinyj.githubsearch.util

object Utils {

    fun getTag() : String {
        var className = Thread.currentThread().stackTrace[3].className
        className = className.substring(className.lastIndexOf('.') + 1)
        return "[$className]"
    }

}