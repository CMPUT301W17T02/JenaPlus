package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by Carlo on 2017-03-25.
 */

public class PendingFollowing {

    private ArrayList<String> pendingFollowing = new ArrayList<String>();

    public ArrayList<String> getPendingFollowing() {
        return pendingFollowing;
    }

    public void setPendingFollowing(ArrayList<String> pendingFollowing) {
        this.pendingFollowing = pendingFollowing;
    }

    public void addPendingFollowing(String p) {
        pendingFollowing.add(p);
    }
}
