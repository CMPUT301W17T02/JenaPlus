package com.mood.jenaPlus;

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

}
