package com.mood.jenaPlus;

import android.location.Location;

/**
 * Created by Carlo on 2017-03-10.
 */

public class MainMPController {

    // Singleton Pattern Design
    MoodPlus moodPlus = null;

    // Constructor
    public MainMPController(MoodPlus aMoodPlus) {
        this.moodPlus = aMoodPlus;
    }

    public Participant getParticipant() {
        return moodPlus.getParticipant();
    }

    public void usingParticipant(String aName) {

        // Get the participant via elastic search
        moodPlus.getParticipantElastic(aName);
    }
    public void addMoodParticipant(String text, Boolean addLocation, Location location, String id,
                                   String social, String photo, String color) {
        moodPlus.addNewMood(text,addLocation,location,id,social,photo,color);
        moodPlus.notifyViews();
    }

}
