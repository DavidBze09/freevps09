package com.example.phonetradingagent

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder

class AgentForegroundService : Service() {
    override fun onCreate() {
        super.onCreate()
        val channelId = "agent_status"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Agent status", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
        val notification = Notification.Builder(this, channelId)
            .setContentTitle("Phone Trading Agent")
            .setContentText("Local monitoring service is running. Trading is disabled.")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .build()
        startForeground(7, notification)
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY
    override fun onBind(intent: Intent?): IBinder? = null
}
