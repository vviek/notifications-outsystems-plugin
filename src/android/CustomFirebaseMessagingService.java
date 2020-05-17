package org.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;


import android.util.Log;

import com.gems.pt.MainActivity;
import com.gems.pt.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

   @Override
   public void onMessageReceived(RemoteMessage remoteMessage) {
       super.onMessageReceived(remoteMessage);
       Log.e("onMessageReceived", "onMessageReceived: "+remoteMessage.getData() );
       sendNotification("Notification Header","Notification Body");
   }

    private void sendNotification(String header, String messageBody) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.mipmap.ic_launcher;
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        i,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );
        String message = messageBody;
        String title = header;
        //Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.meeting_sound);
        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        Notification.Builder mBuilder = new Notification.Builder(
                this);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new Notification.BigTextStyle().bigText(message))
                .setContentIntent(resultPendingIntent)
               // .setSound(customSoundUri)
                .setVibrate(pattern)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                .setContentText(message).build();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("NETLINKIT", "General Notification", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            mBuilder.setChannelId("NETLINKIT");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }
}