package com.mood.jenaPlus;

import android.graphics.Color;
import android.location.Location;

import java.util.Date;

/**
 * Created by Helen and Carlo on 2017/2/25.
 */

public class Mood {
    private String text;
    private Date date;
    private Boolean addLocation;
    private Location location;
    private MoodIcon mood;
    private String social;
    private String photo;
    private Color color;

    public enum Moods {
        SURPRISED,DISGUST,FEAR,CONFUSED,HAPPY,ANGRY,SAD,SHAME,ANNOYED
    }
    
    Moods m;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(text!=null) {
            this.text = text;
        }
        else{
            this.text = "";
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date!=null) {
            this.date = date;
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (addLocation == true && location!=null) {
            this.location = location;
        }
        else {
            this.location = null;
        }
    }

    public String getMood() {
        Integer moodNum = null;
        String mood;
        switch(m) {
            case SURPRISED: moodNum = 0;
                break;
            case DISGUST: moodNum = 1;
                break;
            case FEAR: moodNum = 2;
                break;
            case CONFUSED: moodNum = 3;
                break;
            case HAPPY: moodNum = 4;
                break;
            case ANGRY: moodNum = 5;
                break;
            case SAD: moodNum = 6;
                break;
            case SHAME: moodNum = 7;
                break;
            case ANNOYED: moodNum = 8;
                break;
            
        }
        mood = MoodIcon.getMood(moodNum);
        return mood;
    }

    public void setMood(MoodIcon mood) {
        this.mood = mood;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        if (social != null) {
            this.social = social;
        }
        else{
            this.social="";
        }
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        if(photo!=null) {
            this.photo = photo;
        }
        else{
            this.photo = "";
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


}


