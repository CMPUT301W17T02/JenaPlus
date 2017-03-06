package com.mood.jenaPlus;

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

/**
 * Created by carrotji on 2017-03-05.
 */

public class ElasticsearchMPController {

    private static JestDroidClient client;

    public static class AddUsersTask extends AsyncTask<User,Void,Void>{
        @Override
        protected Void doInBackground(User... users){
            // ... : arbitrary number of arguments in Java

            for (User user : users){
                Index index = new Index.Builder(user).index("cmput301w17t2").type("user").build();

                try {
                    // where is client?
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()){
                        user.setId(result.getId());
                    }
                    else{
                        Log.i("Error","Elasticsearch was not able to add the user.");
                    }
                }
                catch (Exception e){
                    Log.i("Error","The application failed to build and send the users");
                }
            }
            return null;
        }
    }

    public static class GetUsersTask extends AsyncTask<String,Void,ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters){
            verifySettings();

            ArrayList<User> users = new ArrayList<User>();

            String query = "" +search_parameters[0]+ "";
            System.out.println(query);

            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput304w17t2")
                    .addType("user")
                    .build();

            try{
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<User> foundUser = result.getSourceAsObjectList(User.class);
                    users.addAll(foundUser);
                }
                else{
                    Log.i("Error","The search query failed to find any users that matched");
                    System.out.println("could not find error");
                }
            }
            catch(Exception e){
                Log.i("Error","Something went wrong when tried to communicate with the elasticsearch server!");
            }
            return users;
        }

    }



    public static void verifySettings(){
        if(client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            //return JestDroidClient after build object
            client = (JestDroidClient) factory.getObject();
        }
    }
}
