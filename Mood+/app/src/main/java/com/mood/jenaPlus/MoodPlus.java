package com.mood.jenaPlus;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MoodPlus extends MPModel<MPView> {

    public Participant participant; // The participant who logs in

    protected UserMoodList userMoods; // ArrayList of user moods

    public UserMoodList getUserMoods() {
        return userMoods;
    }

    public void setMoods(UserMoodList newMoods) {
        this.userMoods = newMoods;
    }

    public Participant getParticipant() {
        return participant;
    }


    public void setParticipant(Participant aParticipant) {
        this.participant = aParticipant;
    }

    public void addNewMood1(String text, Boolean addLocation, Double latitude, Double longitude, String id,
                           String social, String photo, String color, String userName) {

        participant.addNewMood1(text,addLocation,latitude,longitude,id,social,photo,color,userName);
        updateParticipant();
        notifyViews();
    }

    public void addNewMood2(String text, Boolean addLocation, String id,
                            String social, String photo, String color, String userName) {

        participant.addNewMood2(text,addLocation,id,social,photo,color,userName);
        updateParticipant();
        notifyViews();
    }


    public void deleteMood(Mood mood) {
		participant.getUserMoodList().deleteUserMood(mood);
		updateParticipant();
		notifyViews();
	}

    public void getParticipantElastic(String aName) {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        participant = eController.getUsingParticipant(aName);
        notifyViews();
    }

    public void updateParticipant() {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        eController.updateUser(participant);
    }

    public void addFollowRequest(String aParticipant) {
        participant.addPendingFollowing(aParticipant);
        updateParticipant();
        notifyViews();
    }

    public void updateParticipant2(Participant aParticipant) {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        eController.updateUser(aParticipant);
    }

    public void setPendingFollowers(String aParticipant) {

        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        Participant otherParticipant = eController.getUsingParticipant(aParticipant);
        Log.i("otherpartSETPENDING", otherParticipant.getUserName());
        otherParticipant.addPendingFollowers(participant.getUserName());
        updateParticipant2(otherParticipant);
        notifyViews();
    }

    public void acceptRequest(String aParticipant) {

        participant.acceptFollower(aParticipant);
        updateParticipant();

        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        Participant otherParticipant = eController.getUsingParticipant(aParticipant);
        Log.i("otherParticipant", otherParticipant.getUserName());
        otherParticipant.acceptedFollowing(participant.getUserName());
        updateParticipant2(otherParticipant);
        notifyViews();
    }

    public void rejectRequest(String aParticipant) {
        participant.removePendingFollowers(aParticipant);
        updateParticipant();

        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        Participant otherParticipant = eController.getUsingParticipant(aParticipant);
        Log.i("printing", otherParticipant.getUserName());
        otherParticipant.removePendingFollowing(participant.getUserName());
        updateParticipant2(otherParticipant);
        notifyViews();
    }

    public void unfollowParticipant(String aParticipant) {
        participant.removeFollowingList(aParticipant);
        updateParticipant();

        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        Participant otherParticipant = eController.getUsingParticipant(aParticipant);
        otherParticipant.removeFollowerList(participant.getUserName());
        updateParticipant2(otherParticipant);
        notifyViews();
    }



    MoodPlus(){
        super();
    }

}
