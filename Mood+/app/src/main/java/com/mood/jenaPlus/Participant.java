package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by ceciliaxiang on 2017-02-25.
 */

public class Participant extends User {

    private String userName;
    private MoodList moodList;
    private MoodList followingMoodList;


    public Participant(String userName) {
        this.userName = userName;
    }

    

}
