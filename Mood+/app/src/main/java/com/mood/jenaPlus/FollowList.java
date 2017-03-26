package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * NOT USING THIS CLASS FOR NOW. WILL BE USED FOR PART 5.
 * Created by carrotji on 2017-02-26.
 */

public class FollowList {
    private ArrayList<String> pendingFollowing = new ArrayList<String>();
    private ArrayList<String> followingList = new ArrayList<>();
    private ArrayList<String> pendingFollowers = new ArrayList<>();
    private ArrayList<String> followerList = new ArrayList<>();

    public void followingRequest(String userName) {
        pendingFollowing.add(userName);
    }

    public void followingAccepted(String userName) {
        followingList.add(userName);
    }

    public void followingRejected(String userName) {
        pendingFollowing.remove(userName);
    }

    public void followerRequest(String userName) {
        pendingFollowers.add(userName);
    }

    public void followerAccepted(String userName) {
        pendingFollowers.remove(userName);
        //followerList.add(userName);
    }

    public void followerRejected(String userName) {
        pendingFollowers.remove(userName);

    }


    public ArrayList<String> getPendingFollowing() {
        return pendingFollowing;
    }

    public ArrayList<String> getFollowingList() {
        return followingList;
    }

    public ArrayList<String> getPendingFollowers() {
        return pendingFollowers;
    }

    public ArrayList<String> getFollowerList() {
        return followerList;
    }

    public String getFollowingParticipant(int index) {
        return followingList.get(index);
    }

}

