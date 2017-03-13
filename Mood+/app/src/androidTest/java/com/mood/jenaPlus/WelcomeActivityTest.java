package com.mood.jenaPlus;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by carrotji on 2017-03-13.
 */

public class WelcomeActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public WelcomeActivityTest() {
        super(WelcomeActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testLoginParticipant(){
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.enterText((EditText) solo.getView(R.id.loginUserName),"herb");
        solo.clickOnButton("Log in");

        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);
    }

    public void testNonExistParticipant(){
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.enterText((EditText) solo.getView(R.id.loginUserName),"Non-Exist");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity",WelcomeActivity.class);
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
