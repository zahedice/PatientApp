package com.example.zahedur.tuntuninews;

import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by zahedur on 2/26/18.
 */

public class FirebaseMessagingService extends  com.google.firebase.messaging.FirebaseMessagingService {

    private  NotificationHelper mNotificationHelper;
    public  String message=" ";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        mNotificationHelper = new NotificationHelper(this);

        //showNotification(remoteMessage.getData().get("message"));

        //sendMessage(remoteMessage.getData().get("message"));

        message = remoteMessage.getData().get("message");


        showNotification(message);

    }


    private void showNotification(String message) {

        String title = "Message from the doctor";
        sendOnChannel(title,message);

    }


    private void sendOnChannel(String title, String message) {


        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(title,message);


        mNotificationHelper.getManager().notify(1,nb.build());
    }




}
