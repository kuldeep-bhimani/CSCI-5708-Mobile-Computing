package com.example.calenderappdemo.service

import android.content.BroadcastReceiver
import android.content.Intent
import com.example.calenderappdemo.MainActivity
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.macs.revitalize.R

class AlaramReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val event = intent.getStringExtra("event")
        val time = intent.getStringExtra("time")
        val notid = intent.getIntExtra("id", 0)
        val activityintent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityintent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val channelId = "channel_id"
        val name: CharSequence = "channel_name"
        val description = "description"
        val channel = NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH)
        channel.description = description
        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(event).setContentText(time).setDeleteIntent(pendingIntent)
            .setGroup("Group_calender_view").build()
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(notid, notification)
    }
}