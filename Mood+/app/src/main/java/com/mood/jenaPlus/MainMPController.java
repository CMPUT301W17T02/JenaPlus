package com.mood.jenaPlus;

import android.location.Location;

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
     * @param id          the id
     * @param social      the social
     * @param photo       the photo
     * @param color       the color
     */

    public void addMoodParticipant1(String text, Boolean addLocation, Double latitude,Double longitude, String id,
                                   String social, String photo, String color) {
        moodPlus.addNewMood1(text,addLocation,latitude,longitude,id,social,photo,color);
        moodPlus.notifyViews();
    }

    public void addMoodParticipant2(String text, Boolean addLocation, String id,
                                    String social, String photo, String color) {
        moodPlus.addNewMood2(text,addLocation,id,social,photo,color);
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

    public void addFollowRequest(Participant participant) {
        moodPlus.addFollowRequest(participant);
        moodPlus.notifyViews();
    }

    public void setPendingFollowers(Participant aParticipant) {
        moodPlus.setPendingFollowers(aParticipant);
        moodPlus.notifyViews();
    }

    public void acceptRequest(Participant aParticipant) {
        moodPlus.acceptRequest(aParticipant);
        moodPlus.notifyViews();
    }

    public void rejectRequest(Participant aParticipant) {
        moodPlus.rejectRequest(aParticipant);
        moodPlus.notifyViews();
    }




}
