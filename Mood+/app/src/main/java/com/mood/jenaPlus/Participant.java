package com.mood.jenaPlus;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ceciliaxiang on 2017-02-25.
 */

public class Participant extends User {

    private String userName;
    private MoodList userMoodList;
    private MoodList followingMoodList;

    public Participant(String userName) {
        this.userName = userName;
    }

    public void addMoodEvent(String text, Date date, Boolean addLocation, Location location, MoodIcon mood,
                             String social, String photo, MoodColor color) {
        userMoodList.addUserMood(text, date, addLocation, location, mood, social, photo, color);
    }


}
