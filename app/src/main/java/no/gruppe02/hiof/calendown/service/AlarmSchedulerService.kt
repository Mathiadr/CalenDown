package no.gruppe02.hiof.calendown.service

import android.app.AlarmManager
import no.gruppe02.hiof.calendown.model.Event


/* Solution based on code provided by
    ChatGPT (Prompt: How to schedule a notification with dynamic content at a specific time?)
    and https://medium.com/@nipunvirat0/how-to-schedule-alarm-in-android-using-alarm-manager-7a1c3b23f1bb
    and https://developer.android.com/develop/ui/views/notifications/build-notification#kotlin
 */
interface AlarmSchedulerService {

    val alarmManager: AlarmManager?

    fun getMessageSuffix(title: String): String
    suspend fun setAlarm(event: Event)
    suspend fun cancel(event: Event)
}