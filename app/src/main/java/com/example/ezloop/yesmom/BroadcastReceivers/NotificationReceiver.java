package com.example.ezloop.yesmom.BroadcastReceivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.ezloop.yesmom.MainActivity;
import com.example.ezloop.yesmom.R;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String NOTIFICATION_CHANNEL_ID = "YesMomNotigChannel";
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If device's OS is O or greater, create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = context.getString(R.string.nc_name);
            String description = context.getString(R.string.nc_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel =
                    new NotificationChannel( NOTIFICATION_CHANNEL_ID,
                            name,
                            importance);
            mChannel.setDescription(description);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                NOTIFICATION_CHANNEL_ID);

        Intent mIntent = new Intent(context, MainActivity.class);
        PendingIntent mPending = PendingIntent.getActivity(context,
                0,
                mIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setAutoCancel(true)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(R.drawable.ic_assignment_late_black_24dp)
                .setContentTitle(context.getString(R.string.hun_title))
                .setContentText(context.getString(R.string.hun_text)).setContentIntent(mPending);
        notificationManager.notify(1, builder.build());
    }
}
