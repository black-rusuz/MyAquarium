package com.example.myaquarium;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class NotificationBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String channel_id = intent.getStringExtra("channel_id");
        int notification_id = intent.getIntExtra("notification_id", 101);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id);
        builder.setColor(context.getResources().getColor(R.color.accent))
                .setPriority(PRIORITY_HIGH)
                .setSmallIcon(R.drawable.notification_fish)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.notification_fish))
                .setContentTitle("Пора кормить рыбок!");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channel_id,
                    channel_id,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(notification_id, builder.build());
    }
}