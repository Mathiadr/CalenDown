package no.gruppe02.hiof.calendown.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

/* Solution based on code provided by
    ChatGPT (Prompt: How to schedule a notification with dynamic content at a specific time?)
    and https://medium.com/@nipunvirat0/how-to-schedule-alarm-in-android-using-alarm-manager-7a1c3b23f1bb
    and https://developer.android.com/develop/ui/views/notifications/build-notification#kotlin
 */
class AlarmReceiver : BroadcastReceiver() {
    companion object{
        private const val EVENT_TITLE = "title"
        private const val EVENT_DESC = "content"
        private const val NOTIFICATION_MESSAGE = "message"
    }
    override fun onReceive(context: Context, intent: Intent) {
        val eventData = Data.Builder()
            .putString(EVENT_TITLE, intent.getStringExtra(EVENT_TITLE))
            .putString(EVENT_DESC, intent.getStringExtra(EVENT_DESC))
            .putString(NOTIFICATION_MESSAGE, intent.getStringExtra(NOTIFICATION_MESSAGE))
            .build()

        val workRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInputData(eventData)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}