package com.mood.jenaPlus;

import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 * Created by Carlo on 2017-04-02.
 */

public class MoodTest {

    @Test
    public void TestMoodHasUserName() {
        String moodId = "happy";
        String color = "#A7FFF649";
        Mood newMood = new Mood("I'm happy today!",false,moodId,"","",color,"testUserName");
        assertTrue(newMood.getUserName().equals("testUserName"));
    }

    @Test
    public void TestMoodHasAddLocation() {
        String moodId = "happy";
        String color = "#A7FFF649";
        Mood newMood = new Mood("I'm happy today!",false,moodId,"","",color,"testUserName");
        assertTrue(newMood.getAddLocation().equals(false));
    }

    @Test
    public void TestMoodHasLocation() {
        String moodId = "happy";
        String color = "#A7FFF649";
        Double lat = 45.9;
        Double lon = 13.9;
        Mood newMood = new Mood("I'm happy today!",true,lat,lon,moodId,"","",color,"testUserName");
        assertTrue(newMood.getLatitude().equals(lat));
        assertTrue(newMood.getLongitude().equals(lon));
    }

    @Test
    public void TestMoodHasID() {
        String moodId = "happy";
        String color = "#A7FFF649";
        Double lat = 45.9;
        Double lon = 13.9;
        Mood newMood = new Mood("I'm happy today!",true,lat,lon,moodId,"","",color,"testUserName");
        assertTrue(newMood.getId().equals(moodId));
    }

    @Test
    public void TestMoodHasSocial() {
        String moodId = "happy";
        String color = "#A7FFF649";
        Double lat = 45.9;
        Double lon = 13.9;
        Mood newMood = new Mood("I'm happy today!",true,lat,lon,moodId,"Alone","",color,"testUserName");
        assertTrue(newMood.getSocial().equals("Alone"));
    }

    @Test
    public void TestMoodHasPhoto() {
        String moodId = "happy";
        String color = "#A7FFF649";
        Double lat = 45.9;
        Double lon = 13.9;
        Mood newMood = new Mood("I'm happy today!",true,lat,lon,moodId,"Alone","fdsja;fdsafds",color,"testUserName");
        assertTrue(newMood.getPhoto().equals("fdsja;fdsafds"));
    }



}
