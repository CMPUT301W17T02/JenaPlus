package com.mood.jenaPlus;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.robotium.solo.Solo;

/**
 * Created by Carlo on 2017-03-12.
 */

public class AddMoodActivityTest extends ActivityInstrumentationTestCase2{

    public Solo solo;

    public AddMoodActivityTest() {
        super(com.mood.jenaPlus.WelcomeActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testAddBareMood() {
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginUserName));
        solo.enterText((EditText) solo.getView(R.id.loginUserName), "josefina");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickOnView((solo.getView(R.id.fab)));
        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);


        //clicking on the first mood: "Feeling Surprised" appears on the toast
        GridView gridview = (GridView)solo.getView(R.id.gridView);
        View element = gridview.getChildAt(0);
        solo.clickOnView(element);


        solo.clickOnView((solo.getView(R.id.AddButton)));
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);
        deleteTestMood();
    }

    public void testAddLocationMood() {

        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginUserName));
        solo.enterText((EditText) solo.getView(R.id.loginUserName), "josefina");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickOnView((solo.getView(R.id.fab)));
        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);

        // clicking on the first mood: "Feeling Surprised" appears on the toast
        GridView gridview = (GridView)solo.getView(R.id.gridView);
        View element = gridview.getChildAt(0);
        solo.clickOnView(element);

        // Click the location button
        solo.clickOnView((solo.getView(R.id.action_navigation)));

        // Add mood and return to main activity.
        solo.clickOnView((solo.getView(R.id.AddButton)));
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ViewMoodActivity.class);

        // Click on the navigation button, view the mood, view the location, and go back to main
        solo.clickOnView((solo.getView(R.id.test_location)));
        solo.assertCurrentActivity("Wrong Activity", MapActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ViewMoodActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        // Remove the test mood
        deleteTestMood();


    }

    public void testAddSocialSituationMood() {

        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginUserName));
        solo.enterText((EditText) solo.getView(R.id.loginUserName), "josefina");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickOnView((solo.getView(R.id.fab)));
        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);

        // clicking on the first mood: "Feeling Surprised" appears on the toast
        GridView gridview = (GridView)solo.getView(R.id.gridView);
        View element = gridview.getChildAt(0);
        solo.clickOnView(element);

        solo.clickOnView((solo.getView(R.id.socialPopup)));
        solo.searchText("Alone");
        solo.clickOnText("Alone");

        solo.clickOnView((solo.getView(R.id.AddButton)));
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);
        deleteTestMood();

    }

    public void deleteTestMood() {

        solo.clickLongInList(0);
        solo.clickOnText("Delete");
        solo.clickOnText("Yes");
    }


}

