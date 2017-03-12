package com.mood.jenaPlus;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by carrotji on 2017-02-25.
 */

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

    public void addNewMood(String text, Boolean addLocation, Location location, String id,
                           String social, String photo, String color) {

        participant.addNewMood(text,addLocation,location,id,social,photo,color);
        //userMoods.addUserMood(aMood);
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






    MoodPlus(){
        super();
    }

}
