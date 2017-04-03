package com.mood.jenaPlus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Class that stores and manages offline data. The saving and loading of these lists are used to
 * save the user's own moodlist locally on the phone and will be processed and synced with the
 * server once connectivity is re-established.
 *
 * Created by Cecilia and Julienne on 2017-03-28.
 *
 * @author Cecilia
 * @author Julienne
 * @version 1.0
 */

public class SaveOffline extends AppCompatActivity{


    protected MoodPlus moodPlus;
    protected Participant participant;

	/**
     * Saves the offlineList of the user in a shared preference. Keeps track of the user's own
     * moodlist for syncing with the server.
     *
     * @param offlineList the UserMoodList of moods to be saved
     * @param id the unique id of the user, to determine which moodlist is whose
     * @param context the context passed in to know where the user currently is in the app
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
	 * Loads an existing offlineList. Keeps track of the user's own moodlist for syncing with
     * the server.
     *
     * @param id the unique id of the user, to determine which moodlist is whose
     * @param context the context passed in to know where user currently is in the app
     * @return the UserMoodList of moods to be stored locally on the phone
     */
    public static UserMoodList loadOfflineList(String id, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(id, null);
        UserMoodList loadedMoodList = new Gson().fromJson(json, new TypeToken<UserMoodList>(){}.getType());
        return loadedMoodList;
    }


}
