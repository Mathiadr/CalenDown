package no.gruppe02.hiof.calendown.api

import java.time.LocalDate
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

fun getSeconds(timeInLong: Long): Number{
    return (timeInLong / MILLIS_IN_SECOND) % SECONDS_IN_MINUTE
}

fun getMinutes(timeInLong: Long): Number{
    return timeInLong / (MILLIS_IN_SECOND * SECONDS_IN_MINUTE) % MINUTES_IN_HOUR
}

fun getHours(timeInLong: Long): Number{
    return timeInLong / (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR) % HOURS_IN_DAY
}

fun getTotalDays(timeInLong: Long): Number{
    return timeInLong /
            (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY)
}

fun getDays(timeInLong: Long): Number{

    val calendarTarget = Calendar.getInstance()
    calendarTarget.timeInMillis = System.currentTimeMillis() + timeInLong

    val calendarCurrent = Calendar.getInstance()
    calendarCurrent.timeInMillis = System.currentTimeMillis()

    if (calendarCurrent.before(calendarTarget)) {
        val currentLocalDate = LocalDate.of(
            calendarCurrent.get(Calendar.YEAR),
            calendarCurrent.get(Calendar.MONTH) + 1,
            calendarCurrent.get(Calendar.DAY_OF_MONTH)
        )
        val targetLocalDate = LocalDate.of(
            calendarTarget.get(Calendar.YEAR),
            calendarTarget.get(Calendar.MONTH) + 1,
            calendarTarget.get(Calendar.DAY_OF_MONTH)
        )
        val remainingDays = targetLocalDate.dayOfMonth - currentLocalDate.dayOfMonth
        return remainingDays

    }
    else return 0
}

fun getMonths(timeInLong: Long): Number {
    val calendarTarget = Calendar.getInstance()
    calendarTarget.timeInMillis = System.currentTimeMillis() + timeInLong

    val calendarCurrent = Calendar.getInstance()
    calendarCurrent.timeInMillis = System.currentTimeMillis()

    val currentYearMonth = YearMonth.of(calendarCurrent.get(Calendar.YEAR), calendarCurrent.get(Calendar.MONTH)+1)
    val targetYearMonth = YearMonth.of(calendarTarget.get(Calendar.YEAR), calendarTarget.get(Calendar.MONTH)+1)
    val remainingMonthValue = (targetYearMonth.monthValue - currentYearMonth.monthValue).mod(
        MONTHS_IN_YEAR)
    return remainingMonthValue
}

fun getYears(timeInLong: Long): Number {
    val calendarTarget = Calendar.getInstance()
    calendarTarget.timeInMillis = System.currentTimeMillis() + timeInLong

    val calendarCurrent = Calendar.getInstance()
    calendarCurrent.timeInMillis = System.currentTimeMillis()

    return if (calendarCurrent.before(calendarTarget)) Math.floorDiv(getTotalDays(timeInLong).toInt() ,DAYS_IN_YEAR)
    else 0L
}
