package com.example.driveit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

/**
 * @author Emry Sayada
 * A broadcast receiver for notifications
 */
public class AlarmReceiver extends BroadcastReceiver {
    /**
     * constructor with the logic for the notifications
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm is on!", Toast.LENGTH_SHORT).show();
        Intent go = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, go, PendingIntent.FLAG_IMMUTABLE);
        String channelID = "channel_id";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.baseline_car_rental_24)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setContentTitle("Driving lesson!")
                .setContentText("Click to start the lesson")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}