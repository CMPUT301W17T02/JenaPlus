package com.mood.jenaPlus;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by carrotji on 2017-03-13.
 */

public class EditMoodActivityTest extends ActivityInstrumentationTestCase2 {

    public Solo solo;

    public EditMoodActivityTest() {
        super(com.mood.jenaPlus.WelcomeActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testEditMood() {
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginUserName));
        solo.enterText((EditText) solo.getView(R.id.loginUserName), "herb");
        solo.clickOnButton("Log in");
        assertTrue(solo.waitForText("Username: herb"));
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickLongInList(0);
        solo.clickOnText("Edit");
        solo.waitForActivity("EditMoodActivity");
        solo.assertCurrentActivity("Wrong Activity", EditMoodActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.message));
        solo.enterText((EditText) solo.getView(R.id.message),"Test message");

        solo.waitForActivity("EditMoodActivity");
        solo.clickOnText("Save");
        solo.waitForActivity("MoodPlusActivity");
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

    }

}
