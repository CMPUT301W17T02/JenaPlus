package com.mood.jenaPlus;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.robotium.solo.Solo;

/**
 * Created by carrotji on 2017-03-13.
 */

public class DeleteMoodActivityTest extends ActivityInstrumentationTestCase2 {

    public Solo solo;

    public DeleteMoodActivityTest() {
        super(com.mood.jenaPlus.WelcomeActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testDeleteMood() {
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.enterText((EditText) solo.getView(R.id.loginUserName), "josefina");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickOnView((solo.getView(R.id.fab)));
        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);

        GridView gridview = (GridView)solo.getView(R.id.gridView);
        View element = gridview.getChildAt(2);
        solo.clickOnView(element);

        solo.clickOnView((solo.getView(R.id.socialPopup)));
        solo.searchText("With a crowd");
        solo.clickOnText("With a crowd");


        solo.clickOnView((solo.getView(R.id.AddButton)));
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);

        solo.clickLongInList(0);
        solo.clickOnText("Delete");
        solo.clickOnText("Yes");

    }
}
