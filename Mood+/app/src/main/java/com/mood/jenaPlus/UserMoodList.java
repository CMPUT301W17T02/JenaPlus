package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by Carlo on 2017-03-10.
 */

public class UserMoodList {

    private ArrayList<Mood> UserMoodList = new ArrayList<>();

    public ArrayList<Mood> getUserMoodList() {
        return UserMoodList;
    }

    public void setUserMoodList(ArrayList<Mood> userMoodList) {
        UserMoodList = userMoodList;
    }

    public void addUserMood(Mood mood){

        UserMoodList.add(mood);
    }

    public void deleteUserMood(Mood mood){

        UserMoodList.remove(mood);
    }

    public boolean hasUserMood (Mood mood) {
        return UserMoodList.contains(mood);
    }




}
