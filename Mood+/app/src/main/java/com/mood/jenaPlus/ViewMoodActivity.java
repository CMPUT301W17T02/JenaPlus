package com.mood.jenaPlus;

import android.app.Activity;
import android.os.Bundle;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by carrotji on 2017-03-06.
 */

public class ViewMoodActivity extends Activity implements MPView<MoodPlus> {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_interface);

        MoodPlus mp = MoodPlusApplication.getMoodPlus();
        mp.addView(this);

    }

    public void update(MoodPlus moodPlus){
        // TODO implements update method
    }
}
