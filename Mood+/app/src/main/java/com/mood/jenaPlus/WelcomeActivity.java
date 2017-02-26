package com.mood.jenaPlus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;

/**
 * Created by carrotji on 2017-02-25.
 */

public class WelcomeActivity extends Activity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_interface);

        Button button = (Button) findViewById(R.id.Login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, moodPlusActivity.class);
                startActivity(intent);

            }
        });
    }
}