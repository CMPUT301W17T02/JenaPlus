package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by Helen on 2017/3/23.
 */

public class FollowingMoodList {
    private ArrayList<Mood> FollowingMoodList = new ArrayList<>();

    /**
     * Gets following mood list.
     *
     * @return the user mood list
     */
    public ArrayList<Mood> getUserMoodList() {
        return FollowingMoodList;
    }

}