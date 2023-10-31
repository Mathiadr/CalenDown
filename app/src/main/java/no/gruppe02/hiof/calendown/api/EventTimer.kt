package no.gruppe02.hiof.calendown.api

import java.time.Month
import java.time.YearMonth
import java.util.Calendar

private const val MILLIS_IN_SECOND = 1000L
private const val SECONDS_IN_MINUTE = 60
private const val MINUTES_IN_HOUR = 60
private const val HOURS_IN_DAY = 24
private const val MONTHS_IN_YEAR = 12
private const val DAYS_IN_YEAR = 365

private const val YEAR_MILLISECONDS =
    (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_YEAR)



fun getRemainingTime(futureTimeInLong: Long): Long {
    val currentTimeMillis = System.currentTimeMillis()
    return futureTimeInLong - currentTimeMillis
}

fun getSeconds(timeInLong: Long): Long{
    return (timeInLong / MILLIS_IN_SECOND) % SECONDS_IN_MINUTE
}

fun getMinutes(timeInLong: Long): Long{
    return timeInLong / (MILLIS_IN_SECOND * SECONDS_IN_MINUTE) % MINUTES_IN_HOUR
}

fun getHours(timeInLong: Long): Long{
    return timeInLong / (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR) % HOURS_IN_DAY
}

fun getTotalDays(timeInLong: Long): Long{
    return timeInLong /
            (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY)
}

fun getDays(timeInLong: Long): Long{

    val calendarTarget = Calendar.getInstance()
    calendarTarget.timeInMillis = System.currentTimeMillis() + timeInLong

    val calendarCurrent = Calendar.getInstance()
    calendarCurrent.timeInMillis = System.currentTimeMillis()

    var daysBetweenWithinMonth = 0L
    while (calendarCurrent.before(calendarTarget)) {
        calendarCurrent.add(Calendar.DAY_OF_MONTH, 1)
        daysBetweenWithinMonth++
    }

    return daysBetweenWithinMonth % YearMonth.now().lengthOfMonth()
}

fun getMonths(timeInLong: Long): Long {
    val currentCalendar = Calendar.getInstance()
    val targetCalendar = Calendar.getInstance()
    targetCalendar.timeInMillis = timeInLong
    var remainingMonths = (targetCalendar.get(Calendar.MONTH))
    if (remainingMonths <= 0) remainingMonths = 0
    return (remainingMonths).toLong()
}

fun getYears(timeInLong: Long): Long{
    val remainingDays = getTotalDays(timeInLong)
    return (remainingDays / 365)
}