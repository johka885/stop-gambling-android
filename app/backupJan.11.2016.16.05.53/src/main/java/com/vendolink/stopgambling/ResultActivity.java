package com.vendolink.stopgambling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences settings = getSharedPreferences(getString(R.string.storagekey), 0);
        int result = settings.getInt("initial_test_result", 0);

        TextView resultText = (TextView) findViewById(R.id.result);
        TextView resultTitle = (TextView) findViewById(R.id.title);

        resultTitle.setText(getString(R.string.danger));

        if( result >= 260 ){
            resultText.setText(getString(R.string.addicted));
        } else if( result >= 200 ){
            resultText.setText(getString(R.string.partly_addicted));
        } else{
            resultTitle.setText(getString(R.string.congrats));
            resultText.setText(getString(R.string.not_addicted));
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(ResultActivity.this, SettingsActivity.class);
                ResultActivity.this.startActivity(settings);
            }
        });
    }
}
