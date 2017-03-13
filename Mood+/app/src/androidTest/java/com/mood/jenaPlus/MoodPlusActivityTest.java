package com.mood.jenaPlus;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

/**
 * Created by Carlo on 2017-03-12.
 */

public class MoodPlusActivityTest extends ActivityInstrumentationTestCase2{

    public Solo solo;

    public MoodPlusActivityTest() {
        super(com.mood.jenaPlus.WelcomeActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

}

