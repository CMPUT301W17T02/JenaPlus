package com.mood.jenaPlus;

/**
 * Created by carrotji on 2017-02-25.
 */

public class MoodPlus extends MPModel<MPView> {

    MoodPlus(){
        super();
    }

    public Participant usingParticipant;

    public void getUsingParticipantUsername(String aName) {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        usingParticipant = eController.getUsingParticipant(aName);
        notifyViews();

    }

}
