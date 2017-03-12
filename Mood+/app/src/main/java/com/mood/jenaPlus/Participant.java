package com.mood.jenaPlus;

import android.location.Location;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by ceciliaxiang on 2017-02-25.
 */

public class Participant extends User {

    private UserMoodList userMoodList = new UserMoodList();
    private MoodList followingMoodList = new MoodList();
    private FollowList followingParticipants = new FollowList();
    private FollowList followersParticipants = new FollowList();

    @JestId
    private String id;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public void addFollowingParticipant(Participant participant) {
        followingParticipants.addToFollowingList(participant);
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

    public void addNew(Mood aMood) {
        Mood mood = aMood;
        userMoodList.addUserMood(mood);
    }

    public void addNewMood(String text, Boolean addLocation, Location location, String id,
                             String social, String photo, String color) {
        Mood mood = new Mood(text,addLocation,location,id,social,photo,color);

        mood.setText(text);
        mood.setAddLocation(addLocation);
        mood.setLocation(location);
        mood.setId(id);
        mood.setSocial(social);
        mood.setPhoto(photo);
        mood.setColor(color);

        userMoodList.addUserMood(mood);
    }

    public UserMoodList getUserMoodList() {
        return userMoodList;
    }

    public void setUserMoodList(UserMoodList userMoodList) {
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

    @Override public String toString() {
        return userName;
    }

}
