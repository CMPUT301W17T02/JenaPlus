package com.mood.jenaPlus;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by carrotji on 2017-02-25.
 */
public class MoodPlus extends MPModel<MPView> {

    /**
     * The Participant.
     */
    public Participant participant; // The participant who logs in

    /**
     * The User moods.
     */
    protected UserMoodList userMoods; // ArrayList of user moods

    /**
     * Gets user moods.
     *
     * @return the user moods
     */
    public UserMoodList getUserMoods() {
        return userMoods;
    }

    /**
     * Sets moods.
     *
     * @param newMoods the new moods
     */
    public void setMoods(UserMoodList newMoods) {
        this.userMoods = newMoods;
    }

    /**
     * Gets participant.
     *
     * @return the participant
     */
    public Participant getParticipant() {
        return participant;
    }

    /**
     * Sets participant.
     *
     * @param aParticipant the a participant
     */
    public void setParticipant(Participant aParticipant) {
        this.participant = aParticipant;
    }

    /**
     * Add new mood.
     *
     * @param text        the text
     * @param addLocation the add location
     * @param location    the location
     * @param id          the id
     * @param social      the social
     * @param photo       the photo
     * @param color       the color
     */
    public void addNewMood(String text, Boolean addLocation, LatLng location, String id,
                           String social, String photo, String color) {

        participant.addNewMood(text,addLocation,location,id,social,photo,color);
        //userMoods.addUserMood(aMood);
        updateParticipant();
        notifyViews();
    }

    /**
     * Delete mood.
     *
     * @param mood the mood
     */
    public void deleteMood(Mood mood) {
		participant.getUserMoodList().deleteUserMood(mood);
		updateParticipant();
		notifyViews();
	}

    /**
     * Gets participant elastic.
     *
     * @param aName the a name
     */
    public void getParticipantElastic(String aName) {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        participant = eController.getUsingParticipant(aName);
        notifyViews();
    }

    /**
     * Update participant.
     */
    public void updateParticipant() {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        eController.updateUser(participant);
    }


    /**
     * Instantiates a new Mood plus.
     */
    MoodPlus(){
        super();
    }

}
