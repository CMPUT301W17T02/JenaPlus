package com.mood.jenaPlus;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by ceciliaxiang on 2017-02-25.
 */

public class Participant extends User {

    private String userName;
    private MoodList userMoodList = new MoodList();
    private MoodList followingMoodList = new MoodList();
    private FollowList followingParticipants = new FollowList();
    private FollowList followersParticipants = new FollowList();

    public void addFollowingParticipant(Participant participant) {
        followingParticipants.addToFollowingList(participant);
    }

    public String getUserName() {
        return this.userName;
    }

    public boolean hasUserName(String newName) {
        if (newName.equals(this.userName)) {
            return true;
        } else {
            return false;
        }
    }

    public Participant(String userName) {
        this.userName = userName;
    }

    public void addNewMood(String text, Boolean addLocation, Location location, String id,
                             String social, String photo, String color) {
        Mood mood = new Mood();

        mood.setText(text);
        mood.setAddLocation(addLocation);
        mood.setLocation(location);
        mood.setId(id);
        mood.setSocial(social);
        mood.setPhoto(photo);
        mood.setColor(color);

        userMoodList.addUserMood(mood);
    }

    public MoodList getUserMoodList() {
        return userMoodList;
    }

    public void setUserMoodList(MoodList userMoodList) {
        this.userMoodList = userMoodList;
    }

    public MoodList getFollowingMoodList() {
        return followingMoodList;
    }

    public void setFollowingMoodList(MoodList followingMoodList) {
        this.followingMoodList = followingMoodList;
    }

    public void followingParticipantsAccepted(Participant userName) {
        followingParticipants.followingAccepted(userName);
    }

    public void followingParticipantsRejected(Participant userName) {
        followingParticipants.followingRejected(userName);
    }

    public void followingParticipantsRequest(Participant userName){
        followingParticipants.followingRequest(userName);
    }

    public void followerParticipantsAccepted(Participant userName) {
        followersParticipants.followerAccepted(userName);
    }

    public void followerParticipantsRejected(Participant userName) {
        followersParticipants.followerRejected(userName);
    }

    public void followerParticipantsRequest(Participant userName){
        followersParticipants.followerRequest(userName);
    }

    public ArrayList<Participant> getPendingFollowers(){
        return followersParticipants.getPendingFollowers();
    }
    public ArrayList<Participant> getFollowers(){
        return followersParticipants.getFollowerList();
    }
    public ArrayList<Participant> getFollowing(){
        return followingParticipants.getFollowingList();
    }
    public ArrayList<Participant> getPendingFollowing(){
        return followingParticipants.getPendingFollowing();
    }

    public FollowList getFollowersParticipants() {
        return followersParticipants;
    }

    public void setFollowersParticipants(FollowList followersParticipants) {
        this.followersParticipants = followersParticipants;
    }

    public FollowList getFollowingParticipants() {
        return followingParticipants;
    }

    public void setFollowingParticipants(FollowList followingParticipants) {
        this.followingParticipants = followingParticipants;
    }

}
