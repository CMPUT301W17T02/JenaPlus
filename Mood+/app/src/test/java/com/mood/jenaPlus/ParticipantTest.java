package com.mood.jenaPlus;


import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by Carlo on 2017-02-26.
 */


public class ParticipantTest{

    @Test
    public void testNewParticipantName() throws Exception {
        String existingUserName = "testUserName";
        Participant newParticipant1 = new Participant(existingUserName);
        assertTrue(newParticipant1.hasUserName(existingUserName));
    }

    @Test
    public void testAddNewMoodEvent() throws Exception {
        MoodIcon m = new MoodIcon();
        String userName = "testUserName";
        String text = "I'm happy!";
        Boolean addlocation = false;
        Location location = null;
        String id = m.getMood(8);
        String social = null;
        String photo = null;
        String color = m.getMood(8);

        Participant newParticipant = new Participant(userName);
        newParticipant.addMoodEvent(text,addlocation,location,id,social,photo,color);
        MoodList moodList = newParticipant.getUserMoodList();
        Mood firstMood = (Mood) moodList.getMood(0);

        //Checking if the mood was added
        assertFalse(moodList.userIsEmpty());

    }
}
