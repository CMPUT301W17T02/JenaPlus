package com.mood.jenaPlus;


<<<<<<< HEAD

import android.location.Location;

import java.util.Date;
=======
import android.location.Location;

import java.util.Date;

>>>>>>> master
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
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

    public static class GetMoodsTask extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query = "" + search_parameters[0] + "";
            System.out.println(query);


            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t2")
                    .addType("mood")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Mood> foundMood = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundMood);
                } else {
                    Log.i("Error", "The search query failed to find any users that matched");
                    System.out.println("could not find error");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when tried to communicate with the elasticsearch server!");
            }
            return moods;
        }

    }


    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            //return JestDroidClient after build object
            client = (JestDroidClient) factory.getObject();
        }
    }


<<<<<<< HEAD
=======


>>>>>>> master
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
