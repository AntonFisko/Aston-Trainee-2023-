package com.example.musicplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.io.IOException

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    private fun stopForegroundService() {
        mediaPlayer?.stop()
        stopForeground(Service.STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        action?.let {
            when (it) {
                ACTION_PLAY -> {
                    val mediaUriString = intent.getStringExtra(EXTRA_MEDIA_URI)
                    mediaUriString?.let { uri ->
                        val mediaUri = Uri.parse(uri)
                        startForegroundService(mediaUri)
                    }
                }
                ACTION_STOP -> stopForegroundService()
                else -> {}
            }
        }
        return START_NOT_STICKY
    }

    private fun startForegroundService(mediaUri: Uri) {
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
        try {
            mediaPlayer?.apply {
                reset()
                setDataSource(this@MusicService, mediaUri)
                prepareAsync()
                setOnPreparedListener { mp ->
                    mp.start()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun createNotification(): Notification {
        val notificationTitle = "Music Service"
        val notificationChannelId = "MusicChannelId"

        val builder = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle(notificationTitle)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Playing music")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(
                notificationChannelId,
                "Music Player Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        return builder.build()
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    companion object {
        const val ACTION_PLAY = "com.example.app.action.PLAY"
        const val ACTION_STOP = "com.example.app.action.STOP"
        const val EXTRA_MEDIA_URI = "extra_media_uri"
        const val NOTIFICATION_ID = 101
    }
}