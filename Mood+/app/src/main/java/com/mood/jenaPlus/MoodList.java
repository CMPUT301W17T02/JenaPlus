package com.mood.jenaPlus;

import android.graphics.Color;
import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ceciliaxiang on 2017-02-25.
 */

public class MoodList {

    private ArrayList<Mood> UserMoodList = new ArrayList<>();
    private ArrayList<Mood> FollowingMoodList = new ArrayList<>();


    public void addUserMood(Mood mood){
        
        UserMoodList.add(mood);
    }

    public void deleteUserMood(Mood mood){

        UserMoodList.remove(mood);
    }


    public Mood getUserIndex(int index){
        return UserMoodList.get(index);
    }

    public Mood getFollowingIndex(int index){
        return FollowingMoodList.get(index);
    }

    public Mood getUserMood(int index) {
        return UserMoodList.get(index);
    }

    public ArrayList<Mood> getUserMood() {
        return UserMoodList;
    }

    public ArrayList<Mood> getFollowingMood() {
        return FollowingMoodList;
    }

    public boolean userIsEmpty() {
        if(UserMoodList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean followingIsEmpty() {
        if(FollowingMoodList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
