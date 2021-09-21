package com.apps.myturn;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getFirebaseMassage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void getFirebaseMassage(String title,String msg){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"mychannel")
                .setSmallIcon(R.drawable.arrow_upward)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(101,builder.build());
    }
}
