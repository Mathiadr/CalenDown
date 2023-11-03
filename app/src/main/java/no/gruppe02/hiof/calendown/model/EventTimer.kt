package no.gruppe02.hiof.calendown.model

import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import no.gruppe02.hiof.calendown.api.getDays
import no.gruppe02.hiof.calendown.api.getHours
import no.gruppe02.hiof.calendown.api.getMinutes
import no.gruppe02.hiof.calendown.api.getMonths
import no.gruppe02.hiof.calendown.api.getRemainingTime
import no.gruppe02.hiof.calendown.api.getSeconds
import no.gruppe02.hiof.calendown.api.getYears

class EventTimer(
    var targetTimeInMillis: Long,
    val remainingTimeInMillis: MutableLongState = mutableLongStateOf(getRemainingTime(targetTimeInMillis)),
    val seconds: MutableState<String> = mutableStateOf(getSeconds(remainingTimeInMillis.longValue).toString()),
    val minutes: MutableState<String> = mutableStateOf(getMinutes(remainingTimeInMillis.longValue).toString()),
    val hours: MutableState<String> = mutableStateOf(getHours(remainingTimeInMillis.longValue).toString()),
    val days: MutableState<String> = mutableStateOf(getDays(remainingTimeInMillis.longValue).toString()),
    val months: MutableState<String> = mutableStateOf(getMonths(remainingTimeInMillis.longValue).toString()),
    val years: MutableState<String> = mutableStateOf(getYears(remainingTimeInMillis.longValue).toString()),
) {
    fun update(){
        remainingTimeInMillis.longValue = getRemainingTime(targetTimeInMillis)
        seconds.value = getSeconds(remainingTimeInMillis.longValue).toString()
        minutes.value = getMinutes(remainingTimeInMillis.longValue).toString()
        hours.value = getHours(remainingTimeInMillis.longValue).toString()
        days.value = getDays(remainingTimeInMillis.longValue).toString()
        months.value = getMonths(remainingTimeInMillis.longValue).toString()
        years.value = getYears(remainingTimeInMillis.longValue).toString()
    }
}