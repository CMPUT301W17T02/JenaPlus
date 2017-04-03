package com.mood.jenaPlus;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * This is the main participant class which contains a Jest Id for elastic search,
 * a moodlist that contains their own list of moods, a following moodlist, and 2 lists for
 * who they follow and who is following them.
 *
 * @author Cecelia
 * @author Carlo
 */
public class Participant extends User {

    @JestId
    private String id;

    private UserMoodList userMoodList = new UserMoodList();
    private ArrayList<String> pendingFollowing = new ArrayList<String>();
    private ArrayList<String> followingList = new ArrayList<>();
    private ArrayList<String> pendingFollowers = new ArrayList<>();
    private ArrayList<String> followerList = new ArrayList<>();


    /**
     * Get id string.
     *
     * @return the string
     */
    public String getId(){
        return id;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(String id){
        this.id = id;
    }

    public boolean hasUserName(String newName) {
        if (newName.equals(this.userName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Instantiates a new Participant.
     *
     * @param userName the user name
     */
    public Participant(String userName) {

        this.userName = userName;
    }

    /**
     * Add new.
     *
     * @param aMood the a mood
     */
    public void addNew(Mood aMood) {
        Mood mood = aMood;
        userMoodList.addUserMood(mood);
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
                           String social, String photo, String color,String userName) {
        Mood mood = new Mood(text,addLocation,latitude,longitude,id,social,photo,color,userName);

        mood.setText(text);
        mood.setAddLocation(addLocation);
        mood.setLatitude(latitude);
        mood.setLongitude(longitude);
        mood.setId(id);
        mood.setSocial(social);
        mood.setPhoto(photo);
        mood.setColor(color);

        userMoodList.addUserMood(mood);
    }
    public void addNewMood2(String text, Boolean addLocation, String id,
                            String social, String photo, String color, String userName) {
        Mood mood = new Mood(text,addLocation,id,social,photo,color,userName);

        mood.setText(text);
        mood.setAddLocation(addLocation);
        mood.setId(id);
        mood.setSocial(social);
        mood.setPhoto(photo);
        mood.setColor(color);

        userMoodList.addUserMood(mood);
    }

    /**
     * Gets user mood list.
     *
     * @return the user mood list
     */
    public UserMoodList getUserMoodList() {
        return userMoodList;
    }

    /**
     * Sets user mood list.
     *
     * @param userMoodList the user mood list
     */
    public void setUserMoodList(UserMoodList userMoodList) {
        this.userMoodList = userMoodList;
    }


    /* ----------------------FOLLOWER/FOLLOWING LISTS ---------------------------*/

    public ArrayList<String> getPendingFollowing() {
        return pendingFollowing;
    }

    public void setPendingFollowing(ArrayList<String> pendingFollowing) {
        this.pendingFollowing = pendingFollowing;
    }

    public ArrayList<String> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(ArrayList<String> followingList) {
        this.followingList = followingList;
    }

    public ArrayList<String> getPendingFollowers() {
        return pendingFollowers;
    }

    public void setPendingFollowers(ArrayList<String> pendingFollowers) {
        this.pendingFollowers = pendingFollowers;
    }

    public ArrayList<String> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(ArrayList<String> followerList) {
        this.followerList = followerList;
    }

    public void addPendingFollowing(String p) {
        pendingFollowing.add(p);
    }
    public void addPendingFollowers(String p) { pendingFollowers.add(p); }
    public void addFollowingList(String p) { followingList.add(p); }
    public void addFollowerList(String p) { followerList.add(p); }
    public void removePendingFollowing(String p) { pendingFollowing.remove(p); }
    public void removePendingFollowers(String p) { pendingFollowers.remove(p); }
    public void removeFollowingList(String p) { followingList.remove(p); }
    public void removeFollowerList(String p) { followerList.remove(p); }
    public void acceptFollower(String p) {
        removePendingFollowers(p);
        addFollowerList(p);
    }
    public void acceptedFollowing(String p) {
        removePendingFollowing(p);
        addFollowingList(p);
    }

    /* ----------------------FOLLOWER/FOLLOWING LISTS ---------------------------*/




    @Override public String toString() {
        return userName;
    }

}
