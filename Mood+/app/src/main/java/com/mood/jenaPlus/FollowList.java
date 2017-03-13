package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * NOT USING THIS CLASS FOR NOW. WILL BE USED FOR PART 5.
 * Created by carrotji on 2017-02-26.
 */

public class FollowList {
    private ArrayList<Participant> pendingFollowing = new ArrayList<Participant>();
    private ArrayList<Participant> followingList = new ArrayList<Participant>();
    private ArrayList<Participant> pendingFollowers = new ArrayList<Participant>();
    private ArrayList<Participant> followerList = new ArrayList<Participant>();

    public void followingRequest(Participant userName) {
        pendingFollowing.add(userName);
    }

    public void addToFollowingList(Participant participant) {

        followingList.add(participant);
    }

    public void followingAccepted(Participant userName) {
        pendingFollowing.remove(userName);
        followingList.add(userName);
    }

    public void followingRejected(Participant userName) {
        pendingFollowing.remove(userName);

    }

    public void followerRequest(Participant userName) {
        pendingFollowers.add(userName);
    }

    public void followerAccepted(Participant userName) {
        pendingFollowers.remove(userName);
        followerList.add(userName);
    }

    public void followerRejected(Participant userName) {
        pendingFollowers.remove(userName);

    }

    public ArrayList<Participant> getPendingFollowing() {
        return pendingFollowing;
    }

    public ArrayList<Participant> getFollowingList() {
        return followingList;
    }

    public ArrayList<Participant> getPendingFollowers() {
        return pendingFollowers;
    }

    public ArrayList<Participant> getFollowerList() {
        return followerList;
    }

    public Participant getFollowingParticipant(int index) {
        return followingList.get(index);
    }

}

