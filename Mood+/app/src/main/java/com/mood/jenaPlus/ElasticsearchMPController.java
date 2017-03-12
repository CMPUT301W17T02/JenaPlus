package com.mood.jenaPlus;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;


import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.core.search.aggregation.PercentileRanksAggregation;

/**
 * Created by carrotji on 2017-03-05.
 */

public class ElasticsearchMPController {

    private static JestDroidClient client;
    MoodPlus moodPlus = null;

    public ElasticsearchMPController(MoodPlus aMoodPlus) {
        this.moodPlus = aMoodPlus; // Adding a model to a controller.
    }

    public static class AddUsersTask extends AsyncTask<Participant, Void, Void> {
        @Override
        protected Void doInBackground(Participant... participants) {
            // ... : arbitrary number of arguments in Java
            verifySettings();

            for (Participant participant : participants) {

                Index index = new Index.Builder(participant).index("cmput301w17t2").type("user").build();

                try {
                    // where is client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        participant.setId(result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the user.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                }
            }
            return null;
        }
    }

    public static class GetUsersTask extends AsyncTask<String, Void, ArrayList<Participant>> {
        @Override
        protected ArrayList<Participant> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Participant> participants = new ArrayList<Participant>();

            String query = "" + search_parameters[0] + "";
            System.out.println(query);


            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t2")
                    .addType("user")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Participant> foundParticipant = result.getSourceAsObjectList(Participant.class);
                    participants.addAll(foundParticipant);
                } else {
                    Log.i("Error", "The search query failed to find any users that matched");
                    System.out.println("could not find error");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when tried to communicate with the elasticsearch server!");
            }
            return participants;
        }

    }


    public static class GetOneUserTask extends AsyncTask<String, Void, Participant> {
        @Override
        protected Participant doInBackground(String... search_parameters) {
            verifySettings();

            Participant participant = new Participant(null);

            //String userName = search_parameters[0];

            String query = search_parameters[0];

            System.out.println(query);


            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t2")
                    .addType("user")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    participant = result.getSourceAsObject(Participant.class);
                    Log.i("Good", "User Found!!");
                } else {
                    Log.i("Error", "No Users matched in the cmput301w17t2 index");
                    System.out.println("could not find error");


                }
            } catch (Exception e) {
                Log.e("Error", "Not returning the participant...");
            }
            return participant;
        }

    }

    public static class UpdateUsersTask extends AsyncTask<Participant, Void, Void> {
        @Override
        protected Void doInBackground(Participant... participants) {
            // ... : arbitrary number of arguments in Java
            verifySettings();

            Participant participant = participants[0];

            String jestId = participant.getId();

            Index index = new Index.Builder(participant).index("cmput301w17t2").type("user").id(jestId).build();

            try {
                // where is client?
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    Log.i("Works","Possibly works?");
                } else {
                    Log.i("Error", "Elasticsearch was not able to update");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the users");
            }

            return null;
        }
    }

    public void updateUser(Participant participant) {
        UpdateUsersTask updateUsersTask = new UpdateUsersTask();
        updateUsersTask.execute(participant);
    }

    public Participant getUsingParticipant(String aName) {
        Participant returnedParticipant = new Participant(null);

        GetOneUserTask getOneUserTask = new GetOneUserTask();

        String q = "{\n" +
                " \"query\" : {\n" +
                " \"match\" : {\n" +
                " \"userName\": \"" + aName + "\"\n"+
                "            }\n" +
                "        }\n" +
                " }";

        getOneUserTask.execute(q);
        try {
            returnedParticipant = getOneUserTask.get();

            String name = returnedParticipant.getUserName();
            Log.i("Username", "Username:"+name);

        } catch (Exception e) {
            Log.i("Error", "Something went wrong when tried to communicate with the elasticsearch server!");

        }
        return returnedParticipant;
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
}