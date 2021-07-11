package com.ridecell.maps.utils.common

import java.text.ParseException
import java.text.SimpleDateFormat

class DateUtils {

    private val formats = arrayOf(
        "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
        "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
        "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
        "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss",
        "yyyy:MM:dd HH:mm:ss", "yyyyMMdd"
    )

    fun parse(d: String?) {
        if (d != null) {
            for (parse in formats) {
                val sdf = SimpleDateFormat(parse)
                try {
                    sdf.parse(d)
                    println("Printing the value of $parse")
                } catch (e: ParseException) {
                }
            }
        }
    }
}