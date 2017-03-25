package com.mood.jenaPlus;

import android.location.Location;
import android.util.Log;

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
     * @param id          the id
     * @param social      the social
     * @param photo       the photo
     * @param color       the color
     */

    public void addNewMood1(String text, Boolean addLocation, Double latitude, Double longitude, String id,
                           String social, String photo, String color) {

        participant.addNewMood1(text,addLocation,latitude,longitude,id,social,photo,color);
        //userMoods.addUserMood(aMood);
        updateParticipant();
        notifyViews();
    }

    public void addNewMood2(String text, Boolean addLocation, String id,
                            String social, String photo, String color) {

        participant.addNewMood2(text,addLocation,id,social,photo,color);
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

    public void addFollowRequest(Participant aParticipant) {
        participant.getFollowList().followingRequest(aParticipant);
        updateParticipant();
        notifyViews();
    }

    public void updateParticipant2(Participant aParticipant) {
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        eController.updateUser(aParticipant);
    }

    public void setPendingFollowers(Participant aParticipant) {

        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        Participant otherParticipant = eController.getUsingParticipant(aParticipant.getUserName());
        Log.i("printing", otherParticipant.getUserName());
        otherParticipant.getFollowList().followerRequest(participant);
        updateParticipant2(otherParticipant);
        notifyViews();
    }

    public void acceptRequest(Participant aParticipant) {
        participant.getFollowList().followerAccepted(aParticipant);
        updateParticipant();
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        Participant otherParticipant = eController.getUsingParticipant(aParticipant.getUserName());
        Log.i("printing", otherParticipant.getUserName());
        otherParticipant.getFollowList().followingRejected(participant);
        otherParticipant.getFollowList().followingAccepted(participant);
        updateParticipant2(otherParticipant);
        notifyViews();
    }

    public void rejectRequest(Participant aParticipant) {
        participant.getFollowList().followingRejected(aParticipant);
        updateParticipant();
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
        Participant otherParticipant = eController.getUsingParticipant(aParticipant.getUserName());
        Log.i("printing", otherParticipant.getUserName());
        otherParticipant.getFollowList().followingRejected(participant);
        updateParticipant2(otherParticipant);
        notifyViews();
    }








    /**
     * Instantiates a new Mood plus.
     */
    MoodPlus(){
        super();
    }

}
