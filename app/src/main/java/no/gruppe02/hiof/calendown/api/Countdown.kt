package no.gruppe02.hiof.calendown.api

import android.os.CountDownTimer
import java.util.Date

// NOTE: util.date is considered outdated(?), might be better to switch to java.time
fun getTimeDifference(fromDate: Date, toDate: Date): Long{
    return fromDate.time - toDate.time
}

fun getTimeDifference(date: Date): Long{
    val currentTime = Date()
    return date.time - currentTime.time
}

fun convertToTime(timeLong: Long): Triple<Long, Long, Long> {
    val seconds = timeLong / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return Triple(days, hours % 24, minutes % 60)
}

fun getTimer(date: Date): Triple<Long, Long, Long> {
    val countDownLong = getTimeDifference(date)
    return convertToTime(countDownLong)
}