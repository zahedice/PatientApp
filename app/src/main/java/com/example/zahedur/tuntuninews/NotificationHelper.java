package com.example.zahedur.tuntuninews;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

/**
 * Created by zahedur on 2/26/18.
 */

public class NotificationHelper extends ContextWrapper {

    public static final String channel1 =  "channel1d";
    public static final String channeName =  "channel 1";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

        {
            createChannel();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {

        NotificationChannel  channel = new NotificationChannel(channel1,channeName, NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC | Notification.DEFAULT_SOUND);

        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager(){

        if ( mManager == null)
        {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return  mManager;

    }


    public NotificationCompat.Builder getChannelNotification(String title, String message)
    {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Bundle bundle = new Bundle();
        bundle.putString("message",message);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);


        return  new NotificationCompat.Builder(getApplicationContext(),channel1 )
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(R.drawable.ic_one);
    }
}
