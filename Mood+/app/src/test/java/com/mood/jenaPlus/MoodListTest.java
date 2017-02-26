package com.mood.jenaPlus;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by Carlo on 2017-02-25.
 */

public class MoodListTest  {
    @Test
    public void testAddMood() throws Exception{
        String moodId = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodId);

        //Adding a text
        userMood.setText("I'm happy today!");

        //Adding a null location
        userMood.setAddLocation(false);
        userMood.setLocation(null);

        //Adding a color
        userMood.setColor(color);

        //Adding a social situation
        userMood.setSocial("I'm alone");

        userList.addUserMood(userMood);

        assertTrue(userList.hasUserMood(userMood));

    }

    /*@Test
    public void testAddMood() throws Exception{

    }*/

    @Test
    public void testDeleteMood() throws Exception {
        String moodId = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodId);

        //Adding a text
        userMood.setText("I'm happy today!");

        //Adding a null location
        userMood.setAddLocation(false);
        userMood.setLocation(null);

        //Adding a color
        userMood.setColor(color);

        //Adding a social situation
        userMood.setSocial("I'm alone");

        userList.addUserMood(userMood);

        userList.deleteUserMood(userMood);

        assertFalse(userList.hasUserMood(userMood));

    }

    @Test
    public void testHasMood() throws Exception {
        String moodId = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodId);

        //Adding a text
        userMood.setText("I'm happy today!");

        //Adding a null location
        userMood.setAddLocation(false);
        userMood.setLocation(null);

        //Adding a color
        userMood.setColor(color);

        //Adding a social situation
        userMood.setSocial("I'm alone");

        assertFalse(userList.hasUserMood(userMood));

        userList.addUserMood(userMood);

        assertTrue(userList.hasUserMood(userMood));

    }

    @Test
    public void testGetMood() throws Exception {
        String moodId = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodId);

        //Adding a text
        userMood.setText("I'm happy today!");

        //Adding a null location
        userMood.setAddLocation(false);
        userMood.setLocation(null);

        //Adding a color
        userMood.setColor(color);

        //Adding a social situation
        userMood.setSocial("I'm alone");

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(userList.hasUserMood(userMood),userList.hasUserMood(returnedMood));

    }

    @Test
    public void testGetColor() throws Exception {
        String moodId = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodId);

        //Adding a color
        userMood.setColor(color);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getColor(), userMood.getColor());

    }

    @Test
    public void testGetPhoto() throws Exception {
        String moodID = "happy";
        String color = "#A7FFF649";
        String photo = "smile.png";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodID);

        // adding a color
        userMood.setColor(color);

        // adding a photo
        userMood.setPhoto(photo);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getPhoto(), userMood.getPhoto());
    }

    @Test
    public void testGetSocial() throws Exception {
        String moodID = "happy";
        String color = "#A7FFF649";
        String social = "I'm alone";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodID);

        // adding a color
        userMood.setColor(color);

        // adding a social situation
        userMood.setSocial(social);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getId(), userMood.getId());

    }

    @Test
    public void testGetID() throws Exception {
        String moodID = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodID);

        // adding a color
        userMood.setColor(color);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getId(), userMood.getId());

    }

    @Test
    public void testGetLocation() throws Exception {
        String moodID = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodID);

        // adding a color
        userMood.setColor(color);

        //Adding a null location
        userMood.setAddLocation(false);
        userMood.setLocation(null);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getLocation(), userMood.getLocation());

    }

    @Test
    public void testGetAddLocation() throws Exception {
        String moodID = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodID);

        //Adding a null location
        userMood.setAddLocation(false);
        userMood.setLocation(null);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertFalse(returnedMood.getAddLocation());

    }

    @Test
    public void testGetDate() throws Exception {
        String moodID = "happy";
        String color = "#A7FFF649";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodID);

        // adding a color
        userMood.setColor(color);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getDate(), userMood.getDate());

    }

    @Test
    public void testGetText() throws Exception {
        String moodID = "happy";
        String color = "#A7FFF649";
        String text = "I had fun today.";
        MoodList userList = new MoodList();
        Mood userMood = new Mood(moodID);

        // adding a color
        userMood.setColor(color);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getId(), userMood.getId());

    }

    @Test
    public void testGetUserIndex() throws Exception {
        String moodID1 = "happy";
        String color1 = "#A7FFF649";

        String moodID2 = "sad";
        String color2 = "FF33B5E5";

        MoodList userList = new MoodList();
        Mood userMood1 = new Mood(moodID1);
        Mood userMood2 = new Mood(moodID2);

        userMood1.setColor(color1);
        userMood2.setColor(color2);

        userList.addUserMood(userMood1);
        userList.addUserMood(userMood2);

        int returnedIndex1 = userList.getUserIndex(userMood1);
        int returnedIndex2 = userList.getUserIndex(userMood2);

        assertEquals(returnedIndex1, 0);
        assertEquals(returnedIndex2, 1);
    }

    @Test
    public void testGetUserMoodList() throws Exception {
        String moodID1 = "happy";
        String color1 = "#A7FFF649";

        String moodID2 = "sad";
        String color2 = "FF33B5E5";

        String moodID3 = "shame";
        String color3 = "AFDE30C9";

        MoodList userList = new MoodList();
        Mood userMood1 = new Mood(moodID1);
        Mood userMood2 = new Mood(moodID2);
        Mood userMood3 = new Mood(moodID3);

        userMood1.setColor(color1);
        userMood2.setColor(color2);

        userList.addUserMood(userMood1);
        userList.addUserMood(userMood2);

        ArrayList<Mood> returnedUserMoodList = userList.getUserMoodList();

        assertEquals(userList.getUserMoodList(), returnedUserMoodList);

        assertFalse(returnedUserMoodList.contains(userMood3));

        userList.addUserMood(userMood3);

        returnedUserMoodList = userList.getUserMoodList();

        assertEquals(userList.getUserMoodList(), returnedUserMoodList);

    }

}
