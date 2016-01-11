package com.vendolink.stopgambling;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class LaunchActivity extends ActionBarActivity {

    public static int[] notificationDays = {1,2,3,4,5,6,7,10,13,17,22,28,35};
    public static int[] notificationMoneySaved = {1000,2000,3000,5000,10000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Intent startIntent = new Intent(getPackageName() + ".START_SCHEDULING");
        sendBroadcast(startIntent);

        SharedPreferences settings = getSharedPreferences(getString(R.string.storagekey), 0);
        Boolean firstLaunch = settings.getBoolean("first_launch", true);

        if(firstLaunch) {
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
    }
}
