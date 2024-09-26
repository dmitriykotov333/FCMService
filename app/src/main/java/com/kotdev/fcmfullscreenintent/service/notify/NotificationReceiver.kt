package com.kotdev.fcmfullscreenintent.service.notify

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.kotdev.fcmfullscreenintent.MainActivity
import com.kotdev.fcmfullscreenintent.R
import kotlin.random.Random

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notify = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and
                    Intent.FLAG_ACTIVITY_SINGLE_TOP and
                    Intent.FLAG_ACTIVITY_CLEAR_TASK and
                    Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("notification", "WORKING")
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notify,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_launcher_foreground
                )
            )
            .setWhen(0)
            .setContentText("ALARM")
            .setContentText("ALARM")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLocalOnly(true)
            .setFullScreenIntent(pendingIntent, true)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        manager.createNotificationChannel(notificationChannel)
        manager.notify(Random.nextInt(), builder)
        val start = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(start)
    }

    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_CHANNEL"
        private const val CHANNEL_NAME = "com.kotdev.fcmfullscreenintent.service"
    }
}
