package com.mood.jenaPlus;

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
    private String id;
    private String social;
    private String photo;
    private String color;

    public Mood() {

    }

    public Mood(String id) {
        this.date = new Date();
        this.id = id;
    }

    //@TODO: implement cases for empty fields/inputs
    public Mood(String text, Date date, Boolean addLocation, Location location, String id,
                String social, String photo, String color) {
        this.text = text;
        this.date = date;
        this.addLocation = addLocation;
        this.location = location;
        this.id = id;
        this.social = social;
        this.photo = photo;
        this.color = color;
    };

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
        if (!addLocation && location == null) {
            this.location = null;
        }
        else {
            this.location = location;
        }
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAddLocation() {
        return addLocation;
    }

    public void setAddLocation(Boolean addLocation) {
        this.addLocation = addLocation;
    }

}


