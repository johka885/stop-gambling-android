package com.vendolink.stopgambling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends ActionBarActivity {

    int questionNumber = 1;
    int totalScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        for(int i = 1; i <= 5; ++i){
            int id = getApplicationContext().getResources().getIdentifier("option" + i, "id", getApplicationContext().getPackageName());
            final Button option = (Button) findViewById(id);
            final int value = (10-2*i)*(10-2*i);
            option.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int titleId = getApplicationContext().getResources().getIdentifier("question" + questionNumber + "_title", "string", getApplicationContext().getPackageName());
                    String title = getString(titleId);
                    SharedPreferences settings = getSharedPreferences(getString(R.string.storagekey), 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("initial_test_" + title, value);

                    totalScore += value;
                    if(questionNumber >= 9){
                        Intent result = new Intent(TestActivity.this, ResultActivity.class);
                        TestActivity.this.startActivity(result);

                        editor.putInt("initial_test_result", totalScore);
                    } else {
                        NextQuestion();
                    }

                    editor.commit();
                }
            });
        }
    }


    private void NextQuestion(){
        questionNumber++;

        int titleId = getApplicationContext().getResources().getIdentifier("question" + questionNumber + "_title", "string", getApplicationContext().getPackageName());
        String title = getString(titleId);
        TextView questionTitle = (TextView) findViewById(R.id.question_title);
        questionTitle.setText(title);

        int contentId = getApplicationContext().getResources().getIdentifier("question" + questionNumber, "string", getApplicationContext().getPackageName());
        String content = getString(contentId);
        TextView questionContent = (TextView) findViewById(R.id.question_content);
        questionContent.setText(content);
    }
}
