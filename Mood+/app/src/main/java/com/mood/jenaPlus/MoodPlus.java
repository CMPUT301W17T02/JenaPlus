package com.mood.jenaPlus;

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



    public void addNewMood(Mood aMood) {
        userMoods.addUserMood(aMood);
        notifyViews();
    }

    public void getParticipantElastic(String aName) {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        participant = eController.getUsingParticipant(aName);
        notifyViews();
    }






    MoodPlus(){
        super();
    }

}
