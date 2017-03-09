package com.mood.jenaPlus;


import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.params.Parameters;

import static com.mood.jenaPlus.ElasticsearchMPController.verifySettings;

/**
 * Created by Bernice on 2017-02-25.
 */

public class MoodListController {

    private static JestDroidClient client;
    MoodPlus moodPlus = null; // a singleton

    public static class AddMoodTask extends AsyncTask<Mood, Void, Void> {
        // add mood event to elastic search
        @Override

        protected Void doInBackground(Mood... moods) {
            verifySettings();

            for (Mood mood : moods) {
                Index index = new Index.Builder(mood)
                        .setParameter(Parameters.REFRESH, true)
                        .index("cmput301w17t2")
                        .type("mood").build();

                try {
                    // where is client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        mood.setId(result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the mood.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to add mood to elastic search");
                }
            }
            return null;
        }
    }



    public MoodListController(MoodPlus aMoodPlus) {
        this.moodPlus = aMoodPlus;
    }
}
