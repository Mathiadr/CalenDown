package no.gruppe02.hiof.calendown.model

import java.util.Date

data class Timer(
    var timestamp: Date

) {
    private val currentTimeInLong: Long = Date().time
    private val timeDifference: Long = timestamp.time - currentTimeInLong

    fun getSeconds(): Number{
        return (timeDifference / 1000) % 60
    }

    fun getMinutes(): Number{
        return (timeDifference / (1000 * 60)) % 60
    }

    fun getHours(): Number{
        return (timeDifference / (1000 * 60 * 60)) % 24
    }

    fun getDays(): Number{
        return (timeDifference / (1000 * 60 * 60 * 24)) % 365
    }
}