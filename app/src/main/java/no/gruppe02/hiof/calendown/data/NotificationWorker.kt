package no.gruppe02.hiof.calendown.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import no.gruppe02.hiof.calendown.R
import kotlin.random.Random

/* Solution based on code provided by
    ChatGPT (Prompt: How to schedule a notification with dynamic content at a specific time?)
    and https://medium.com/@nipunvirat0/how-to-schedule-alarm-in-android-using-alarm-manager-7a1c3b23f1bb
    and https://developer.android.com/develop/ui/views/notifications/build-notification#kotlin
 */
class NotificationWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    companion object {
        private const val CHANNEL_DATE_DUE = "date_due"
    }

    private val notificationManager: NotificationManager =
        context.getSystemService() ?: throw IllegalStateException()
    override fun doWork(): Result {
        // Get data from the workRequest
        val inputData = inputData

        val title = inputData.getString("title") ?: context.getString(R.string.event_reminder)
        val content = inputData.getString("content") ?: context.getString(R.string.your_event_is_due)
        val message = inputData.getString("message") ?: context.getString(R.string.your_event_is_due)
        sendNotification(title, content, message)
        return  Result.success()
    }

    private fun sendNotification(title: String, content: String, message: String) {

        // Create a notification channel if it does not exist
        createNotificationChannel(notificationManager)

        val notificationBuilder = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_DATE_DUE
        )
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_calendar)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val randomId = Random(title.hashCode()).nextInt()
        notificationManager.notify(randomId, notificationBuilder.build())
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (notificationManager.getNotificationChannel(CHANNEL_DATE_DUE) == null) {
            val name = "Due Event Notification"
            val descriptionText = "Notifies user of events that have passed their target date"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_DATE_DUE, name, importance).apply {
                description = descriptionText
            }

            notificationManager.createNotificationChannel(channel)
        }
    }
}