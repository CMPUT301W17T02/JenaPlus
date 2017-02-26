package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by carrotji on 2017-02-26.
 */

public class Following {
    private ArrayList<Participant> pending = new ArrayList<Participant>();
    private ArrayList<Participant> followingList = new ArrayList<Participant>();

    public void followingRequest(Participant userName){
        pending.add(userName);
    }

    public void followingAccepted(Participant userName){
        pending.remove(userName);
        followingList.add(userName);
    }

    public void followingRejected(Participant userName){
        pending.remove(userName);

    }

    public ArrayList<Participant> getPending() {
        return pending;
    }

    public ArrayList<Participant> getFollowingList() {
        return followingList;
    }
}
