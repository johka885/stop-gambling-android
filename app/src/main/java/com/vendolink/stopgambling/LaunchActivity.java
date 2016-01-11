package com.vendolink.stopgambling;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.leanplum.Leanplum;
import com.leanplum.activities.LeanplumFragmentActivity;
import com.leanplum.annotations.Variable;
import com.leanplum.callbacks.StartCallback;


public class LaunchActivity extends LeanplumFragmentActivity {

    public static int[] notificationDays = {1, 2, 3, 4, 5, 6, 7, 10, 13, 17, 22, 28, 35};
    public static int[] notificationMoneySaved = {1000, 2000, 3000, 5000};
    public static int notifsPerDay = 7;
    public static int notifsPerMoney = 2500;

    @Variable
    public static int notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode(getString(R.string.leamplum_appkey), getString(R.string.leamplum_devkey));
        } else {
            Leanplum.setAppIdForProductionMode(getString(R.string.leamplum_appkey), getString(R.string.leamplum_prodkey));
        }

        Leanplum.enableVerboseLoggingInDevelopmentMode();
        Leanplum.start(this);

        SharedPreferences settings = getSharedPreferences(getString(R.string.storagekey), 0);
        Boolean firstLaunch = settings.getBoolean("first_launch", true);

        if (firstLaunch) {
            final Button start = (Button) findViewById(R.id.start_test);
            start.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent test = new Intent(LaunchActivity.this, TestActivity.class);
                    LaunchActivity.this.startActivity(test);
                }
            });
        } else {
            Intent main = new Intent(LaunchActivity.this, ProgressActivity.class);
            LaunchActivity.this.startActivity(main);
        }

        final Activity ac = this;
        Leanplum.addStartResponseHandler(new StartCallback() {
            @Override
            public void onResponse(boolean success) {
                if(!success) notifications = 1;
                if (notifications == 2) {
                    notificationDays = new int[]{1, 2, 3, 4, 5, 6, 7, 9, 11, 13, 15, 17, 20};
                    notificationMoneySaved = new int[]{1000, 2000, 3000, 4000, 5000, 7500, 10000};
                    notifsPerDay = 3;
                    notifsPerMoney = 1000;
                } else if (notifications == 3) {
                    notificationDays = new int[]{1, 2, 3, 5, 7, 10, 14, 21, 28};
                    notificationMoneySaved = new int[]{1000, 2000, 3000, 5000};
                    notifsPerDay = 14;
                    notifsPerMoney = 5000;
                }

                Intent startIntent = new Intent(getPackageName() + ".START_SCHEDULING");
                sendBroadcast(startIntent);
                ac.finish();
            }
        });

    }
}

