package no.gruppe02.hiof.calendown.api

fun getRemainingTime(futureTimeInLong: Long): Long {
    val currentTimeMillis = System.currentTimeMillis()
    return futureTimeInLong - currentTimeMillis
}

fun getSeconds(timeInLong: Long): Number{
    return (timeInLong / 1000) % 60
}

fun getMinutes(timeInLong: Long): Number{
    return (timeInLong / (1000 * 60)) % 60
}

fun getHours(timeInLong: Long): Number{
    return (timeInLong / (1000 * 60 * 60)) % 24
}

fun getDays(timeInLong: Long): Number{
    return (timeInLong / (1000 * 60 * 60 * 24)) % 365
}