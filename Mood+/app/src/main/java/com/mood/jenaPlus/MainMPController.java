package com.mood.jenaPlus;

import com.google.android.gms.maps.model.LatLng;

/**
 * Main controller used in the App.
 * @author Carlo
 */
public class MainMPController {

    /**
     * The Mood plus.
     */
// Singleton Pattern Design
    MoodPlus moodPlus = null;

    /**
     * Instantiates a new Main mp controller.
     *
     * @param aMoodPlus the a mood plus
     */
// Constructor
    public MainMPController(MoodPlus aMoodPlus) {
        this.moodPlus = aMoodPlus;
    }

    /**
     * Gets participant.
     *
     * @return the participant
     */
    public Participant getParticipant() {
        return moodPlus.getParticipant();
    }

    /**
     * Add mood participant.
     *
     * @param text        the text
     * @param addLocation the add location
     * @param location    the location
     * @param id          the id
     * @param social      the social
     * @param photo       the photo
     * @param color       the color
     */
    public void addMoodParticipant(String text, Boolean addLocation, LatLng location, String id,
                                   String social, String photo, String color) {
        moodPlus.addNewMood(text,addLocation,location,id,social,photo,color);
        moodPlus.notifyViews();
    }

    /**
     * Delete mood participant.
     *
     * @param mood the mood
     */
    public void deleteMoodParticipant(Mood mood) {
        moodPlus.deleteMood(mood);
        moodPlus.notifyViews();
    }



}
