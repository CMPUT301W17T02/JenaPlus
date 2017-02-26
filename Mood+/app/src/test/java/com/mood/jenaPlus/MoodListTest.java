package com.mood.jenaPlus;

import java.util.Date;

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
        Mood returnedMood = userList.getUserMood(0);
        assertEquals(returnedMood.getId(),userMood.getId());
        assertEquals(returnedMood.getDate(),userMood.getDate());
        assertEquals(returnedMood.getText(),userMood.getText());
        assertEquals(returnedMood.getLocation(),null);
        assertEquals(returnedMood.getColor(),userMood.getColor());
        assertEquals(returnedMood.getSocial(),userMood.getSocial());
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

        Mood returnedMood = userList.getUserMood(0);

        userList.deleteUserMood(returnedMood);

        assertFalse(userList.hasUserMood(returnedMood));

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

}
