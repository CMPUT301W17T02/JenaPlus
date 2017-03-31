package com.mood.jenaPlus;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;
import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * Created by Cecilia and Julienne on 2017-03-28.
 */

public class SaveOffline extends AppCompatActivity{

    private static final String ADD = "add.sav";
    private static final String EDIT = "edit.sav";
    private static final String DELETE = "delete.sav";

    UserMoodList offlineAddList = new UserMoodList();
    UserMoodList offlineEditList = new UserMoodList();
    UserMoodList offlineDeleteList = new UserMoodList();

    ArrayList tmpAdd = new ArrayList();


    private String socialSituation;
    private String trigger;
    private String idString;
    private String colorString;
    private Boolean addLocation = false;
    private String imageString = "";
    private Double latitude;
    private Double longitude;
    private String userName;

    private UserMoodList userMoodList;

    protected MoodPlusApplication moodPlusApplication;
    protected MoodPlus moodPlus;
    protected Participant participant;

	/**
     * Saves the offlineList of the user in a shared preference
     *
     * @param offlineList
     * @param id
     * @param context
     */
    public static void saveOfflineList(UserMoodList offlineList, String id, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(offlineList);
        prefsEditor.putString(id, json);
        prefsEditor.commit();
    }

	/**
	 * Loads an existing offlineList
     *
     * @param id
     * @param context
     * @return
     */
    public static UserMoodList loadOfflineList(String id, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(id, null);
        UserMoodList loadedMoodList = new Gson().fromJson(json, new TypeToken<UserMoodList>(){}.getType());
        return loadedMoodList;
    }



    public UserMoodList loadFromFile(String filename) {
        try {

            FileInputStream fis = openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-26 18:34
            Type listType = new TypeToken<UserMoodList>(){}.getType();
            userMoodList = gson.fromJson(in, listType);
            return userMoodList;


        } catch (FileNotFoundException e) {
            userMoodList = new UserMoodList();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return userMoodList;

    }

    /**
     * Saves tweets in file in JSON format.
     * @throws FileNotFoundException if folder not exists
     */
    public void saveInFile(String filename, UserMoodList userMoodList) {
        try {
            FileOutputStream fos = openFileOutput(filename,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(userMoodList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    private void addToList(){

        offlineAddList = loadFromFile(ADD);
        offlineEditList = loadFromFile(EDIT);
        offlineDeleteList = loadFromFile(DELETE);

    }

    private void addToFile(){

        saveInFile(ADD, offlineAddList);
        saveInFile(EDIT, offlineEditList);
        saveInFile(DELETE, offlineDeleteList);

    }


    public void syncOfflineAdd1(){

        moodPlus = moodPlusApplication.getMoodPlus();
        participant = moodPlus.getParticipant();
        userMoodList = participant.getUserMoodList();

        while(offlineAddList.getSize() != 0) {

            Mood mood1 = offlineAddList.getUserMood(0);
            userMoodList.addUserMood(mood1);
            offlineAddList.deleteUserMood(mood1);
        }

        moodPlus.updateParticipant();

    }

    public void syncOfflineAdd2(){

        moodPlus = moodPlusApplication.getMoodPlus();
        participant = moodPlus.getParticipant();
        userMoodList = participant.getUserMoodList();

        while(offlineAddList.getSize() != 0) {

            Mood mood2 = offlineAddList.getUserMood(0);
            userMoodList.addUserMood(mood2);
            offlineAddList.deleteUserMood(mood2);
        }

        moodPlus.updateParticipant();

    }


    /*
    private Mood dummyMood1(String trigger, Boolean addLocation, Double latitude, Double longitude, String idString,
                           String socialSituation, String imageString, String colorString, String userName){

        Mood mood = new Mood(trigger, addLocation, latitude, longitude, idString, socialSituation, imageString, colorString, userName);
        mood.setText(trigger);
        mood.setAddLocation(addLocation);
        mood.setLatitude(latitude);
        mood.setLongitude(longitude);
        mood.setId(idString);
        mood.setSocial(socialSituation);
        mood.setPhoto(imageString);
        mood.setColor(colorString);

        return mood;
    }


    private Mood dummyMood2(String trigger, Boolean addLocation, String idString,
                            String socialSituation, String imageString, String colorString, String userName){

        Mood mood = new Mood(trigger, addLocation, idString, socialSituation, imageString, colorString, userName);
        mood.setText(trigger);
        mood.setAddLocation(addLocation);
        mood.setId(idString);
        mood.setSocial(socialSituation);
        mood.setPhoto(imageString);
        mood.setColor(colorString);

        return mood;
    }

    */




}
