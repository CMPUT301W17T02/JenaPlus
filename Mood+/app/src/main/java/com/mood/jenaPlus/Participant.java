package com.mood.jenaPlus;

import android.graphics.Color;
import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ceciliaxiang on 2017-02-25.
 */

public class Participant extends User {

    private String userName;
    private MoodList userMoodList = new MoodList();
    private MoodList followingMoodList = new MoodList();
    private FollowingList followingParticipants = new FollowingList();

    public String getUserName() {
        return this.userName;
    }

    public boolean hasUserName(String newName) {
        if (newName.equals(this.userName)) {
            return true;
        } else {
            return false;
        }
    }

    public Participant(String userName) {
        this.userName = userName;
    }

    public void addMoodEvent(String text, Boolean addLocation, Location location, String id,
                             String social, String photo, String color) {
        Mood mood = new Mood();

        mood.setText(text);
        mood.setAddLocation(addLocation);
        mood.setLocation(location);
        mood.setId(id);
        mood.setSocial(social);
        mood.setPhoto(photo);
        mood.setColor(color);

        userMoodList.addUserMood(mood);
    }

    public MoodList getUserMoodList() {
        return userMoodList;
    }

    public void setUserMoodList(MoodList userMoodList) {
        this.userMoodList = userMoodList;
    }

    public MoodList getFollowingMoodList() {
        return followingMoodList;
    }

    public void setFollowingMoodList(MoodList followingMoodList) {
        this.followingMoodList = followingMoodList;
    }

    public void followingParticipantsAccepted(Participant userName) {
        followingParticipants.followingAccepted(userName);
    }




}
