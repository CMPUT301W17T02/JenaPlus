package com.mood.jenaPlus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Carlo on 2017-03-10.
 */

public class UserMoodList {

    private ArrayList<Mood> UserMoodList = new ArrayList<>();

    public ArrayList<Mood> getUserMoodList() {
        return UserMoodList;
    }

    public void setUserMoodList(ArrayList<Mood> userMoodList) {
        UserMoodList = userMoodList;
    }

    public void addUserMood(Mood mood){

        UserMoodList.add(mood);
    }

    public void deleteUserMood(Mood mood){

        UserMoodList.remove(mood);
    }

    public Mood getUserMood(int index) {
        return UserMoodList.get(index);
    }

    public boolean hasUserMood (Mood mood) {
        return UserMoodList.contains(mood);
    }

    public boolean userIsEmpty() {
        if(UserMoodList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

	// sorted Mood List in reverse chronological order (most recent coming first).
	public ArrayList<Mood> getUserMoodOrderedList() {

		Collections.sort(UserMoodList, new Comparator<Mood>() {

			public int compare(Mood o1, Mood o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		});

		return this.UserMoodList;
	}





}
