package com.mood.jenaPlus;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.robotium.solo.Solo;

/**
 *  * ALL TESTS MUST BE RUN ON AN NEXUS 5 WITH AN API OF 22.
 * Created by Carlo on 2017-04-03.
 */

public class FollowerRequestActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public FollowerRequestActivityTest() {
        super(WelcomeActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testViewFollowingTest() {

        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginUserName));
        solo.enterText((EditText) solo.getView(R.id.loginUserName), "josefina");
        solo.clickOnButton("Log in");
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
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);
        deleteTestMood();

        swipeToRight();

        solo.searchText("Follower Requests");
        solo.clickOnText("Follower Requests");

    }

    private void swipeToRight() {
        Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        float xStart = 0 ;
        float xEnd = width / 2;
        solo.drag(xStart, xEnd, height / 2, height / 2, 1);
    }

    public void deleteTestMood() {

        solo.clickLongInList(0);
        solo.clickOnText("Delete");
        solo.clickOnText("Yes");
    }

}


