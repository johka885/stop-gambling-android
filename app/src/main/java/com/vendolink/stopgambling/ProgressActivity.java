package com.vendolink.stopgambling;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leanplum.Leanplum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ProgressActivity extends ActionBarActivity {

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            updateValues();
        }
    };
    Boolean stopped = true;

    static boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        startTimer();

        if( getIntent().getBooleanExtra("notification_clicked", false) ){
            Leanplum.track("notification_clicked");
        }

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.counter_wrapper_big);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(ProgressActivity.this, SettingsActivity.class);
                ProgressActivity.this.startActivity(settings);
                started = true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startTimer();
        started = false;
    }

    private void startTimer(){
        if(stopped) {
            stopped = false;
            runnable.run();
        }
    }

    private void stopTimer(){
        stopped = true;
    }

    private void updateValues(){
        SharedPreferences settings = getSharedPreferences(getString(R.string.storagekey), 0);
        String result = settings.getString("settings_last", "0-0-0");

        Date last = null, date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        try {
            last = format.parse(result);
        } catch (ParseException e) {
            last = date;
            Log.d("ERROR:", "PARSERERROR");
        }

        long diff = date.getTime() - last.getTime();

        long days = diff / (24 * 60 * 60 * 1000);
        diff %= (24 * 60 * 60 * 1000);
        long hours = diff / (60 * 60 * 1000);
        diff %= (60 * 60 * 1000);
        long minutes = diff / (60 * 1000);
        diff %= (60 * 1000);
        long seconds = diff / 1000;



        TextView daysTV = (TextView) findViewById(R.id.days);
        daysTV.setText("" + days);
        TextView hoursTV = (TextView) findViewById(R.id.hours);
        hoursTV.setText("" + hours);
        TextView minutesTV = (TextView) findViewById(R.id.minutes);
        minutesTV.setText("" + minutes);
        TextView secondsTV = (TextView) findViewById(R.id.seconds);
        secondsTV.setText("" + seconds);

        int amount = settings.getInt("settings_amount", 0);
        diff = date.getTime() - last.getTime();
        double months = diff / (1000*60*60*24*(365.25/12));
        double money = months * amount;

        TextView moneySavedLabel = (TextView) findViewById(R.id.money_saved_label);
        moneySavedLabel.setText(getString(R.string.during_this_time));

        TextView moneySaved = (TextView) findViewById(R.id.money_saved);
        moneySaved.setText(String.format("%.2f", money) + " kr");

        int time = settings.getInt("settings_time", 0);
        diff = date.getTime() - last.getTime();
        double week = diff / (1000*60*60*24*7.0);
        double timeSave = week * time;

        TextView timeSaved = (TextView) findViewById(R.id.time_saved);
        timeSaved.setText("Du har även fått " + (int) timeSave + " timmar över till annat.");

        if(!stopped) {
            handler.postDelayed(runnable, 1000);
        }
    }
}
