package no.gruppe02.hiof.calendown.service.impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.data.AlarmReceiver
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.AlarmSchedulerService
import javax.inject.Inject

/* Solution based on code provided by
    ChatGPT (Prompt: How to schedule a notification with dynamic content at a specific time?)
    and https://medium.com/@nipunvirat0/how-to-schedule-alarm-in-android-using-alarm-manager-7a1c3b23f1bb
    and https://developer.android.com/develop/ui/views/notifications/build-notification#kotlin
 */
class AlarmSchedulerServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmSchedulerService {
    private val TAG = this.javaClass.simpleName


    override val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun getMessageSuffix(title: String): String = context.getString(R.string.is_due, title)

    override suspend fun setAlarm(event: Event) {
        try {
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("title", event.title)
                putExtra("content", event.description)
                putExtra("message", getMessageSuffix(event.title))
            }.let{ intent ->
                PendingIntent.getBroadcast(context, event.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE)
            }

            val alarmTime = event.date.time
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                intent
            )
            Log.d(TAG, "Notification Alarm for event '${event.title}' set for ${(alarmTime - System.currentTimeMillis()) / 1000} seconds in the future")
        } catch (e: SecurityException){
            Log.w(TAG, "App does not have permission to set alarms")
        }

    }


    // fixme: HIGH LIKELIHOOD OF NOT WORKING IF THE EVENT TITLE OR DESCRIPTION HAS BEEN CHANGED
    // fixme: actually it just doesn't work. Oops.
    // Destroys the alarm by providing the exact same intent as when created
    // https://developer.android.com/training/scheduling/alarms#cancel
    override suspend fun cancel(event: Event) {
        try {
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("title", event.title)
                putExtra("content", event.description)
                putExtra("message", getMessageSuffix(event.title))
            }.let{ intent ->
                PendingIntent.getBroadcast(context, event.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE)
            }
            alarmManager.cancel(intent)
            //Log.d(TAG, "Alarm for event ${event.title} has been successfully cancelled")
            Log.e(TAG, "Alarm for event ${event.title} has been successfully cancelled (<-- This is most likely a lie. It doesn't work, unfortunately)")
        } catch (e: Exception) {
            Log.e(TAG, "Exception occurred during cancellation of alarm. Did it not exist beforehand?", e)
        }

    }
}