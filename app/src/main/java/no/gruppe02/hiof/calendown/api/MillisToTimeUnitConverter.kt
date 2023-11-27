package no.gruppe02.hiof.calendown.api

import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

private const val MILLIS_IN_SECOND = 1000L
private const val SECONDS_IN_MINUTE = 60
private const val MINUTES_IN_HOUR = 60
private const val HOURS_IN_DAY = 24
private const val MONTHS_IN_YEAR = 12
private const val DAYS_IN_YEAR = 365

private const val YEAR_MILLISECONDS =
    (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_YEAR)

// Solution based on code from the following link: https://www.geeksforgeeks.org/find-the-duration-of-difference-between-two-dates-in-java/
fun getRemainingTime(futureTimeInLong: Long): Long {
    val currentTimeMillis = System.currentTimeMillis()
    return futureTimeInLong - currentTimeMillis
}

// Solution based on code from the following link: https://www.geeksforgeeks.org/find-the-duration-of-difference-between-two-dates-in-java/
fun getSeconds(timeInLong: Long): Int{
    return ((timeInLong / MILLIS_IN_SECOND) % SECONDS_IN_MINUTE).toInt()
}

// Solution based on code from the following link: https://www.geeksforgeeks.org/find-the-duration-of-difference-between-two-dates-in-java/
fun getMinutes(timeInLong: Long): Int{
    return (timeInLong / (MILLIS_IN_SECOND * SECONDS_IN_MINUTE) % MINUTES_IN_HOUR).toInt()
}

// Solution based on code from the following link: https://www.geeksforgeeks.org/find-the-duration-of-difference-between-two-dates-in-java/
fun getHours(timeInLong: Long): Int{
    return (timeInLong / (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR) % HOURS_IN_DAY).toInt()
}

// Solution based on code from the following link: https://www.geeksforgeeks.org/find-the-duration-of-difference-between-two-dates-in-java/
fun getTotalDays(timeInLong: Long): Int{
    return (timeInLong /
            (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY)).toInt()
}

// Solution based on code provided by ChatGPT
fun getDays(timeInLong: Long): Int{
    val dateCurrent = LocalDate.now()
    val dateTarget = Instant.ofEpochMilli(timeInLong + System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate()

    return Period.between(
        dateCurrent, dateTarget
    ).days
}

// Solution based on code provided by ChatGPT
fun getMonths(timeInLong: Long): Int {
    val dateCurrent = LocalDate.now()
    val dateTarget = Instant.ofEpochMilli(timeInLong + System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate()

    return Period.between(
        dateCurrent, dateTarget
    ).months
}

// Solution based on code provided by ChatGPT
fun getYears(timeInLong: Long): Int {
    val dateCurrent = LocalDate.now()
    val dateTarget = Instant.ofEpochMilli(timeInLong + System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate()

    return Period.between(
        dateCurrent, dateTarget
    ).years
}
