package com.mood.jenaPlus;

import android.location.Location;

import java.util.Date;

/**
 * Created by Helen on 2017/2/25.
 */

public class Mood {
    private String text;
    private Date date;
    private Boolean addLocation;
    private Location location;
    private MoodIcon mood;
    private String social;
    private String photo;
    private MoodColor color;

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

    public MoodIcon getMood() {
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

    public MoodColor getColor() {
        return color;
    }

    public void setColor(MoodColor color) {
        this.color = color;
    }
}
