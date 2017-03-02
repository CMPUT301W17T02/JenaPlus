package com.mood.jenaPlus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by carrotji on 2017-02-25.
 */

public class AddMoodActivity extends AppCompatActivity {

    ImageView t1, t2, t3, t4, t5, t6, t7, t8, t9;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mood_interface);
        t1 = (ImageView) findViewById(R.id.surprised);
        t2 = (ImageView) findViewById(R.id.disgust);
        t3 = (ImageView) findViewById(R.id.fear);
        t4 = (ImageView) findViewById(R.id.confused);
        t5 = (ImageView) findViewById(R.id.happy);
        t6 = (ImageView)findViewById(R.id.angry);
        t7 = (ImageView) findViewById(R.id.sad);
        t8 = (ImageView) findViewById(R.id.shame);
        t9 = (ImageView) findViewById(R.id.annoyed);

        t1.setClickable(true);
        t2.setClickable(true);
        t3.setClickable(true);
        t4.setClickable(true);
        t5.setClickable(true);
        t6.setClickable(true);
        t7.setClickable(true);
        t8.setClickable(true);
        t9.setClickable(true);

        t1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "[Surprised Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "Disgust Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "[Fear Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "Confused Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "Happy Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        t6.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "Angry Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        t7.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "Sad Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        t8.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "[Shame Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        t9.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), "Annoyed Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}

