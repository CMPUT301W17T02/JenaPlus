package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by carrotji on 2017-02-25.
 */

public class MoodPlus extends MPModel<MPView> {

    protected ArrayList<Mood> moods;

    public ArrayList<Mood> getMoods() {
        return moods;
    }

    public void setMoods(ArrayList<Mood> newMoods) {
        this.moods = newMoods;
    }

    public void addNewMood(Mood aMood) {
        moods.add(aMood);
        notifyViews();
    }

    public Participant usingParticipant;

    public void getUsingParticipantUsername(String aName) {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        usingParticipant = eController.getUsingParticipant(aName);
        notifyViews();

    }



    MoodPlus(){
        super();
    }

}
