package com.gdscedirne.toplan.presentation.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.gdscedirne.toplan.MainActivity
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.ui.theme.DarkRed20
import javax.inject.Inject

class NotificationMaker @Inject constructor(
) {

    @Inject
    lateinit var context: Context

    fun sendOnDefaultChannel(
        context: Context,
        notificationId: String,
        notificationImage: Int,
        notificationTitle: String,
        notificationContent: String,
    ){

        notificationChannel(context)

        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        notifyIntent.putExtra(context.getString(R.string.notification_extra), true)
        notifyIntent.putExtra(context.getString(R.string.notification_id), notificationId)

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(notificationImage)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(DarkRed20.hashCode())

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId.toInt(), builder.build())
    }

    private fun notificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,importance).apply {
                description = context.getString(R.string.app_name)
                enableLights(true)
                lightColor = android.graphics.Color.RED
            }

            val notificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object{
        private const val CHANNEL_ID = "default_channel"
        private const val CHANNEL_NAME = "Default Channel"
    }

}
