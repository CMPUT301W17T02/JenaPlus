package com.mood.jenaPlus;

import android.location.Location;
import android.os.Parcelable;


import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the Mood class that instantiates a new Mood.
 * A Mood object has all of the information needed about a mood.
 * Created on 2017/2/25
 *
 * @author Helen and Carlo
 * @version 1.0.0
 * @since 1.0
 */

public class Mood implements Serializable{
    private String id;
    private String userName;
    private String text;
    private Date date;
    private Boolean addLocation;
    private Double latitude;
    private Double longitude;
    private String social;
    private String photo;
    private String color;

    /**
     * Instantiates a new Mood.
     *
     * @param text        The trigger which will be < = 20 characters/<4 words
     * @param addLocation Boolean if a location is selected
     * @param id          The mood icon ID
     * @param social      The social situation of participant
     * @param photo       Photo taken
     * @param color       Color of the associated mood
     */

    public Mood(String text, Boolean addLocation, Double Latitude, Double Longitude, String id,
                String social, String photo, String color, String userName) {
        this.text = text;
        this.addLocation = addLocation;
        this.latitude = Latitude;
        this.longitude = Longitude;
        this.id = id;
        this.social = social;
        this.photo = photo;
        this.color = color;
        this.date = new Date();
        this.userName = userName;


        //this.save();    // saves to elastic search server
    }

    public Mood(String text, Boolean addLocation, String id,
                String social, String photo, String color, String userName) {
        this.text = text;
        this.addLocation = addLocation;
        this.id = id;
        this.social = social;
        this.photo = photo;
        this.color = color;
        this.date = new Date();
        this.userName = userName;

        //this.save();    // saves to elastic search server
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        if(text!=null) {
            this.text = text;
        }
        else{
            this.text = "";
        }
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets date string.
     *
     * @return the date string
     */
    public String getDateString() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = formatter.format(date);

        return s;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        if (date!=null) {
            this.date = date;
        }
    }

    /**
     * Gets mood id.
     *
     * @param moodId the mood id
     * @return the mood id
     */
    public String getMoodId(int moodId) {
        MoodIcon m = new MoodIcon();
        id = m.getMood(moodId);
        return id;
    }

    /**
     * Gets mood color.
     *
     * @param moodId the mood id
     * @return the mood color
     */
    public String getMoodColor(int moodId) {
        MoodIcon m = new MoodIcon();
        color = m.getColor(moodId);
        return color;
    }

    /**
     * Gets location.
     *
     * @return the location
     */

   /* public NewLocation getLocation() {

        return newLocation;
    }*/

    /**
     * Sets location.
     *
     */

    /*public void setLocation(NewLocation location) {

        if (!addLocation && location == null) {
            this.newLocation = "";
        }
        else {
            this.newLocation = location;
        }
    }*/

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double lat) {
        this.latitude = lat;
    }

    public void setLongitude(Double lon) {
        this.longitude = lon;
    }

    /**
     * Gets social.
     *
     * @return the social
     */
    public String getSocial() {
        return social;
    }

    /**
     * Sets social.
     *
     * @param social the social
     */
    public void setSocial(String social) {
        if (social != null) {
            this.social = social;
        }
        else{
            this.social="";
        }
    }

    /**
     * Gets photo.
     *
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets photo.
     *
     * @param photo the photo
     */
    public void setPhoto(String photo) {
        if(photo!=null) {
            this.photo = photo;
        }
        else{
            this.photo = "";
        }
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * Gets add location.
     *
     * @return the add location
     */
    public Boolean getAddLocation() {
        return addLocation;
    }

    /**
     * Sets add location.
     *
     * @param addLocation the add location
     */
    public void setAddLocation(Boolean addLocation) {
        this.addLocation = addLocation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }




    /**
     * New string string.
     *
     * @return the string
     */
    public String newString() {
        String dateStr = getDateString();
        String output = dateStr +"\nMood: " + id + ", Color: " + color;
        return output;

    }


    @Override
    public String toString() {
        return newString();
    }


}


