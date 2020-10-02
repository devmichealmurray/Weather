package com.devmmurray.weather.data.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeStampProcessing {

    @SuppressLint("SimpleDateFormat")
    fun transformTimeStamp(utcTime: Long, flag: TimeFlag): String {
        val timeStamp = Date(
            TimeUnit.MILLISECONDS
                .convert(utcTime, TimeUnit.SECONDS)
        ).time

        val correctFormat = when (flag) {
            TimeFlag.FULL -> SimpleDateFormat("E, MMMM d hh:mm a")
            TimeFlag.DAY -> SimpleDateFormat("EEEE")
            TimeFlag.HOUR -> SimpleDateFormat("hh a")
        }

        return correctFormat.format(timeStamp)
    }

}