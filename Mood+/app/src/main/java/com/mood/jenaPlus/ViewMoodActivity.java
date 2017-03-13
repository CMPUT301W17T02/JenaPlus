package com.mood.jenaPlus;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.Date;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by carrotji on 2017-03-06.
 */


public class ViewMoodActivity extends Activity implements MPView<MoodPlus> {

    protected String aId;
    protected String aText;
    protected String aDate;
    protected Boolean addLocation;
    protected LatLng aLocation;
    protected String aSocial;
    protected String aPhoto;
    protected String aColor;

    protected ImageView icon;
    protected TextView situation;
    protected TextView date;
    protected TextView message;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_interface);

        MoodPlus mp = MoodPlusApplication.getMoodPlus();
        mp.addView(this);

        Mood mood = (Mood)getIntent().getSerializableExtra("aMood");
        aId = mood.getId();
        aText = mood.getText();
        aDate = mood.getDate().toString();
        addLocation = mood.getAddLocation();
        aLocation = mood.getLocation();
        aSocial = mood.getSocial();
        aPhoto = mood.getPhoto();
        aColor = mood.getColor();

        icon = (ImageView) findViewById(R.id.cur_mood);
        int recId = getResources().getIdentifier(aId, "drawable", getApplicationContext().getPackageName());
        icon.setImageResource(recId);

        situation = (TextView) findViewById(R.id.situation);
        situation.setText(aSocial);


        date = (TextView) findViewById(R.id.time);
        date.setText(aDate);

        message = (TextView) findViewById(R.id.message);
        message.setText(aText);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor(aColor));

    }

    // Taken from http://stackoverflow.com/questions/4427608/android-getting-resource-id-from-string
    // 12 Mar 2017 12:42
    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    public void update(MoodPlus moodPlus){
        // TODO implements update method
    }
}
