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


}
