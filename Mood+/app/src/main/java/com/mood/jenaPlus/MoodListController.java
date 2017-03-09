package com.mood.jenaPlus;


import android.location.Location;

import java.util.Date;

/**
 * Created by Bernice on 2017-02-25.
 */

public class MoodListController {

    MoodPlus moodPlus = null; // a singleton
    public MoodListController(MoodPlus aMoodPlus) {
        this.moodPlus = aMoodPlus;
    }

    public void addMood(Mood aMood) {
        String text = aMood.getText();
        Date date = aMood.getDate();
        Boolean addLocation = aMood.getAddLocation();
        Location location = aMood.getLocation();
        String id = aMood.getId();
        String social = aMood.getSocial();
        String photo = aMood.getPhoto();
        String color = aMood.getColor();
        moodPlus.usingParticipant.addNewMood(text,addLocation,location,id,social,photo,color);
    }


}
