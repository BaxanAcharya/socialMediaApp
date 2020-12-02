package com.biplav.socialmedia.broadcast;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.biplav.socialmedia.R;
import com.biplav.socialmedia.notification.CreateChannel;



public class BroadcastReciverWifi extends BroadcastReceiver {
    private NotificationManagerCompat notificationManagerCompat;

    private WifiManager wifiManager;
    AlertDialog.Builder builder;

    public static boolean wifi_status;

    @Override
    public void onReceive(final Context context, Intent intent) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        CreateChannel createChannel = new CreateChannel(context);
        createChannel.createChannel();

        wifiManager= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);


        boolean noConnectivity;
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                    false
            );

         //   checking for wifi connection
            if (noConnectivity) {
                //showing alert if wifi disconnected
                builder = new AlertDialog.Builder(context);


                builder.setMessage("Do you want to on the wifi? ").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wifiManager.setWifiEnabled(true);
                                wifi_status=true;
                                DisplayPopUpNotification(context, "Wifi status", "Connected");

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wifi_status=false;
                                dialog.cancel();

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();


            }

        }
    }


    private void DisplayPopUpNotification(Context context, String title, String message) {
        Notification notification = new NotificationCompat.Builder(context, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notification_black)
                .setContentTitle(title)
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();


        notificationManagerCompat.notify(0, notification);
    }





}
