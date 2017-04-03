package com.mood.jenaPlus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * This is the class that defines the mood list of a user.
 *
 * @author Julienne and Carlo Created on 2017-03-10.
 */
public class UserMoodList {

    private ArrayList<Mood> UserMoodList = new ArrayList<>();

    /**
     * Gets user mood list.
     *
     * @return the user mood list
     */
    public ArrayList<Mood> getUserMoodList() {
        return UserMoodList;
    }

    /**
     * Sets user mood list.
     *
     * @param userMoodList the user mood list
     */
    public void setUserMoodList(ArrayList<Mood> userMoodList) {
        UserMoodList = userMoodList;
    }

    /**
     * Add user mood.
     *
     * @param mood the mood
     */
    public void addUserMood(Mood mood){

        UserMoodList.add(mood);
    }

    public int getSize() {
        return UserMoodList.size();
    }

    /**
     * Delete user mood.
     *
     * @param mood the mood
     */
    public void deleteUserMood(Mood mood){

        UserMoodList.remove(mood);
    }

    /**
     * Gets user mood.
     *
     * @param index the index
     * @return the user mood
     */
    public Mood getUserMood(int index) {
        return UserMoodList.get(index);
    }

    /**
     * Has user mood boolean.
     *
     * @param mood the mood
     * @return the boolean
     */
    public boolean hasUserMood (Mood mood) {
        return UserMoodList.contains(mood);
    }

    /**
     * User is empty boolean.
     *
     * @return the boolean
     */
    public boolean userIsEmpty() {
        if(UserMoodList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets user mood ordered list.
     *
     * @return the user mood ordered list
     */
// sorted Mood List in reverse chronological order (most recent coming first).
	public ArrayList<Mood> getUserMoodOrderedList() {

		Collections.sort(UserMoodList, new Comparator<Mood>() {

			public int compare(Mood o1, Mood o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		});

		return this.UserMoodList;
	}

    /**
     * Gets filtered mood text.
     *
     * @param keyword the keyword
     * @return the filtered mood text
     */
    public ArrayList<Mood> getFilteredMoodText(String keyword) {
        int listSize = UserMoodList.size();
        ArrayList<Mood> tempArrayList1 = UserMoodList;
        ArrayList<Mood> tempArrayList2 = new ArrayList<>();

        for (int i = 0; i<listSize; i++){
            String m1 = keyword;
            String m2 = tempArrayList1.get(i).getText();
            if (m2.toLowerCase().contains(m1.toLowerCase())) {
                tempArrayList2.add(tempArrayList1.get(i));
            }
        }

        Collections.sort(tempArrayList2, new Comparator<Mood>() {

            public int compare(Mood o1, Mood o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return tempArrayList2;
    }

    /**
     * Gets filtered moods based on selection from the participant.
     *
     * @param keyword the actual mood
     * @return A list of filtered moods
     */
    public ArrayList<Mood> getFilteredMood(String keyword) {
        int listSize = UserMoodList.size();
        ArrayList<Mood> tempArrayList1 = UserMoodList;
        ArrayList<Mood> tempArrayList2 = new ArrayList<>();

        for (int i = 0; i<listSize; i++){
            String m1 = keyword;
            String m2 = tempArrayList1.get(i).getId();
            if (m2.equals(m1)) {
                tempArrayList2.add(tempArrayList1.get(i));
            }
        }

        Collections.sort(tempArrayList2, new Comparator<Mood>() {

            public int compare(Mood o1, Mood o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return tempArrayList2;
    }

    /**
     * Gets filtered moods from current date back to 7 days ago.
     *
     * @return the filtered date
     */
    public ArrayList<Mood> getFilteredDate() {
        int listSize = UserMoodList.size();
        ArrayList<Mood> tempArrayList1 = UserMoodList;
        ArrayList<Mood> tempArrayList2 = new ArrayList<>();

        for (int i = 0; i<listSize; i++) {
            Date tempDate = tempArrayList1.get(i).getDate();
            if (isWithinRange(tempDate)){
                tempArrayList2.add(tempArrayList1.get(i));
            }
        }

        Collections.sort(tempArrayList2, new Comparator<Mood>() {
            public int compare(Mood o1, Mood o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return tempArrayList2;
    }

    public ArrayList<Mood> getFilteredLocation() {
        int listSize = UserMoodList.size();
        ArrayList<Mood> tempArrayList1 = UserMoodList;
        ArrayList<Mood> tempArrayList2 = new ArrayList<>();

        for (int i = 0; i<listSize; i++) {
            if (tempArrayList1.get(i).getAddLocation()){
                tempArrayList2.add(tempArrayList1.get(i));
            }
        }

        Collections.sort(tempArrayList2, new Comparator<Mood>() {
            public int compare(Mood o1, Mood o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return tempArrayList2;

    }

    /**
     * Is within range boolean.
     *
     * @param testDate the test date
     * @return the boolean
     */
// Taken from http://stackoverflow.com/questions/494180/java-how-do-i-check-if-a-date-is-within-a-certain-range
    // 12 March 2017 19:18
    boolean isWithinRange(Date testDate) {
        Date endDate = new Date();
        Date startDate = new Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000);
        return !(testDate.before(startDate) || testDate.after(endDate));
    }
}
