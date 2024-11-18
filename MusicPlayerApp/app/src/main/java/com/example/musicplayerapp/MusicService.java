package com.example.musicplayerapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;

public class MusicService extends Service {
    private static final String CHANNEL_ID = "music_service_channel";
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the media player to play a song
        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        mediaPlayer.setLooping(true);  // Loop the music
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MusicService", "onStartCommand called");
        startForegroundServiceWithNotification();  // Start service with a notification

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();  // Start the music if not already playing
        }

        return START_STICKY;  // Ensure the service is restarted if it gets killed by the system
    }

    @SuppressLint("ForegroundServiceType")
    private void startForegroundServiceWithNotification() {
        createNotificationChannel();  // Create notification channel for foreground service

        // PendingIntent to launch MainActivity when tapping the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);  // Using FLAG_IMMUTABLE to comply with Android 12+

        // Build the notification to show while the music is playing
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Player")
                .setContentText("Playing music...")
                .setSmallIcon(R.drawable.ic_launcher_foreground)  // Ensure this icon exists in your drawable folder
                .setContentIntent(pendingIntent)  // Intent when the notification is tapped
                .setOngoing(true)  // Make it ongoing so the notification won't be dismissed automatically
                .build();

        // Start the service as a foreground service with the notification
        startForeground(1, notification);  // 1 is the notification ID
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    // This method will stop the music and stop the foreground service
    public void stopMusicAndService() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();  // Stop the music
            mediaPlayer.release();  // Release the media player
        }

        // Stop the foreground service and remove the notification
        stopForeground(true);  // true removes the notification
        stopSelf();  // Stop the service
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Ensure the media player is stopped and released
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  // We're not binding this service
    }
}
