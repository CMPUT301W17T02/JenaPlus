package com.mood.jenaPlus;
import android.location.Location;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 * Created by Carlo on 2017-02-26.
 */


public class ParticipantTest{

    /**
     * Test has user name.
     *
     * @throws Exception the exception
     */
    @Test
    public void testHasUserName() throws Exception {
        String existingUserName = "testUserName";
        Participant newParticipant1 = new Participant(existingUserName);
        assertTrue(newParticipant1.hasUserName(existingUserName));
    }

    /**
     * Test get user name.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetUserName() throws Exception {
        String userName = "testUserName";
        Participant newParticipant = new Participant(userName);
        assertEquals(newParticipant.getUserName(),userName);
    }

    /**
     * Test add new mood event.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAddNewMoodEvent() throws Exception {
        MoodIcon m = new MoodIcon();
        String userName = "testUserName";
        String text = "I'm happy!";
        Boolean addLocation = false;
        Location location = null;
        String id = m.getMood(8);
        String social = null;
        String photo = null;
        String color = m.getMood(8);

        Participant newParticipant = new Participant(userName);
        newParticipant.addNewMood(text,addLocation,location,id,social,photo,color);
        MoodList moodList = newParticipant.getUserMoodList();

        //Checking if the mood was added
        assertFalse(moodList.userIsEmpty());
    }

    /**
     * Test get user mood list.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetUserMoodList() throws Exception {
        MoodIcon m = new MoodIcon();
        String userName = "testUserName";
        String text = "I'm happy!";
        Boolean addLocation = false;
        Location location = null;
        String id = m.getMood(8);
        String social = null;
        String photo = null;
        String color = m.getMood(8);

        MoodIcon m2 = new MoodIcon();
        String text2 = "I'm Disgusted";
        Boolean addLocation2 = false;
        Location location2 = null;
        String id2 = m2.getMood(3);
        String social2 = null;
        String photo2 = null;
        String color2 = m2.getMood(3);

        Participant newParticipant = new Participant(userName);
        newParticipant.addNewMood(text,addLocation,location,id,social,photo,color);
        newParticipant.addNewMood(text2,addLocation2,location2,id2,social2,photo2,color2);

        MoodList userList = newParticipant.getUserMoodList();
        Mood mood0 = userList.getUserMood(0);
        Mood mood1 = userList.getUserMood(1);

        assertEquals(mood0.getText(),text);
        assertEquals(mood1.getText(),text2);
    }

    /**
     * Test get following mood list.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetFollowingMoodList() throws Exception {
        Participant newParticipant = new Participant("user1");
        Participant follow0 = new Participant("f0");
        Participant follow1 = new Participant("f2");
        Participant follow2 = new Participant("f3");

        follow0.addNewMood("I'm happy", false, null, "happy", null, null, "#A7FFF649");
        follow1.addNewMood("I'm sad", false, null, "sad", null, null, "#FF33B5E5");
        follow2.addNewMood("I'm happy", false, null, "happy", null, null, "#A7FFF649");

        newParticipant.addFollowingParticipant(follow0);
        newParticipant.addFollowingParticipant(follow1);
        newParticipant.addFollowingParticipant(follow2);

        FollowList followList = newParticipant.getFollowingParticipants();

        Participant t0 = followList.getFollowingParticipant(0);
        Participant t1 = followList.getFollowingParticipant(1);
        Participant t2 = followList.getFollowingParticipant(2);

        MoodList t0List;
        MoodList t1List;
        MoodList t2List;

        Mood t0Mood;
        Mood t1Mood;
        Mood t2Mood;

        t0List = t0.getUserMoodList();
        t1List = t1.getUserMoodList();
        t2List = t2.getUserMoodList();

        t0Mood = t0List.getUserMood(0);
        t1Mood = t1List.getUserMood(0);
        t2Mood = t2List.getUserMood(0);

        assertEquals(t0Mood.getColor(),"#A7FFF649");
        assertEquals(t1Mood.getText(),"I'm sad");
        assertEquals(t2Mood.getId(), "happy");
    }

}
