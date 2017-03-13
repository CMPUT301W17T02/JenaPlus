package com.mood.jenaPlus;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.robotium.solo.Solo;


/**
 * Created by Carlo on 2017-03-13.
 */

public class ViewNewMoodActivityTest  extends ActivityInstrumentationTestCase2 {

    public Solo solo;

    public ViewNewMoodActivityTest() {
        super(com.mood.jenaPlus.WelcomeActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testViewNewMood() {
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginUserName));
        solo.enterText((EditText) solo.getView(R.id.loginUserName), "herb");
        solo.clickOnButton("Log in");
        assertTrue(solo.waitForText("Username: herb"));
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickOnView((solo.getView(R.id.fab)));
        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);


        //clicking on the first mood: "Feeling disgusted" appears on the toast
        GridView gridview = (GridView)solo.getView(R.id.gridView);
        View element = gridview.getChildAt(1);
        solo.clickOnView(element);

        solo.clickOnView((solo.getView(R.id.socialPopup)));
        solo.searchText("Alone");
        solo.clickOnText("Alone");

        solo.clickOnView((solo.getView(R.id.AddButton)));
        solo.waitForActivity("MoodPlusActivity");
        assertTrue(solo.waitForText("Username: herb"));
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickInList(0);
        solo.waitForActivity("ViewMoodActivity");
        assertTrue(solo.waitForText("Alone"));
        solo.assertCurrentActivity("Wrong Activity", ViewMoodActivity.class);


    }

}
