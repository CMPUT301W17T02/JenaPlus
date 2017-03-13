package com.mood.jenaPlus;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

/**
 * Created by carrotji on 2017-03-13.
 */

public class FilterActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public FilterActivityTest() {
        super(WelcomeActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testFilter(){
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.enterText((EditText) solo.getView(R.id.loginUserName),"herb");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", MoodPlusActivity.class);
        solo.setNavigationDrawer(Solo.OPENED);

        //DrawerLayout drawer = (DrawerLayout)solo.getView(R.id.drawer_layout);
        //solo.clickOnActionBarHomeButton();

        //Solo solo = new Solo(getInstrumentation(), getActivity());
        //solo.setNavigationDrawer(Solo.OPENED);

        solo.setNavigationDrawer(Solo.OPENED);
        //solo.waitForView(DrawerLayout.class);
        //ListView listView = (ListView) solo.getView(R.id.left_drawer);
        //View listElement = listView.getChildAt(1);
        //solo.clickOnView(listElement);
        //solo.clickOnMenuItem("Filter by Angry Moods");
    }
    


    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
