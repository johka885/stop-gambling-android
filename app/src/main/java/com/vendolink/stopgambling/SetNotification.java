package com.vendolink.stopgambling;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.leanplum.Leanplum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class SetNotification extends BroadcastReceiver {
    AlarmManager alarmManager;
    String notificationTitle, notificationMessage;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("RECEIVED ", "" + intent.getBooleanExtra("notify", false));

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent startIntent = new Intent(context.getPackageName() + ".START_SCHEDULING");
        startIntent.putExtra("notify", true);
        PendingIntent startPIntent = PendingIntent.getBroadcast(context, 0, startIntent, 0);

        alarmManager.set(AlarmManager.RTC, getNextNotificationTime(context), startPIntent);

        if(intent.getBooleanExtra("notify", false)) {
            Intent notificationAction = new Intent(context, ProgressActivity.class);
            notificationAction.putExtra("notification_clicked", true);
            PendingIntent notificationPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            notificationAction,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

            Notification notification = new Notification.Builder(context)
                    .setContentTitle("Fri från spel i " + 5 + " dagar")
                    .setContentText("Klicka för att se dina framsteg")
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(notificationPendingIntent)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(R.string.app_name, notification);

            Leanplum.track("notification_sent");
        }
    }

    public long getNextNotificationTime(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.storagekey), Context.MODE_PRIVATE);
        String lastPlayed = sharedPref.getString("settings_last", "2222-01-01 00:00");

        Date d = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        try {
            d = format.parse(lastPlayed);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }

        Date now = new Date();

        long diff = now.getTime() - d.getTime();
        long days = diff / (24 * 60 * 60 * 1000);

        int index = -1;
        for(int i = 0; i < LaunchActivity.notificationDays.length; ++i){
            if( LaunchActivity.notificationDays[i] > days ) {
                index = i;
                break;
            }
        }

        long notificationTime;
        if( index == -1 ){
            notificationTime = now.getTime() + (7*24*60*60*1000);
        } else {
            notificationTime = d.getTime() + (LaunchActivity.notificationDays[index] * (24 * 60 * 60 * 1000));
        }

        int money = sharedPref.getInt("settings_amount", -1);
        long seconds = diff / 1000;
        double moneyPerSecond = money / ((365/12.0) * 24 * 60 * 60);
        long moneySaved = (long) (seconds * moneyPerSecond);

        index = -1;
        for(int i = 0; i < LaunchActivity.notificationMoneySaved.length; ++i){
            if( LaunchActivity.notificationMoneySaved[i] > moneySaved ) {
                index = i;
                break;
            }
        }

        if(index == -1){
            return notificationTime;
        } else {
            long nextNotif = now.getTime() + (long) ((LaunchActivity.notificationMoneySaved[index] - moneySaved) / moneyPerSecond) * 1000;
            return notificationTime > nextNotif ? nextNotif : notificationTime;
        }
    }
}