package com.mood.jenaPlus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class WelcomeActivity extends AppCompatActivity implements MPView<MoodPlus> {

    private EditText userName;
    private ArrayList<Participant> participantList = new ArrayList<Participant>();
    private ArrayAdapter<Participant> adapter;
    private static final String FILENAME = "moodPlus.sav";
    private ListView participants; // List view for testing and debugging

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (EditText) findViewById(R.id.loginUserName);
        Button button = (Button) findViewById(R.id.Login_button);
        participants = (ListView) findViewById(R.id.participantList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Taken from http://stackoverflow.com/questions/7071578/connectivitymanager-to-verify-internet-connection
                // 03-07-2017 23:06
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                String strUser = userName.getText().toString();
                MoodPlus model = MoodPlusApplication.getMoodPlus();
                model.getParticipantElastic(strUser);

                boolean seen = true;
                int pListSize = participantList.size();

                for (int i = 0; i<pListSize; i++) {
                    String user = participantList.get(i).getUserName();
                    Log.i("error","Participant: "+ user);
                }

                for (int i = 0; i<pListSize; i++) {
                    if (participantList.get(i).getUserName().equals(strUser)){
                        Log.i("Error:","THE PARTICIPANT EXISTSSSSS OMG");
                        seen = true;
                        break;
                        //throw new IllegalArgumentException("Participant exists.");
                    } else {
                        seen = false;
                    }
                }

                Log.i("Error", "seen is: " + seen);

                // Will only continue if connected to the internet.
                if (seen== false){
                    userName.setError("User Doesn't Exist, Press Add New ");
                }

                else if (isConnected) {
                    model.getParticipantElastic(strUser); // model is a MoodPlus
                    Intent intent = new Intent(WelcomeActivity.this, MoodPlusActivity.class);
                    startActivity(intent);
                }
                else {
                    userName.setError("Not Connected To the Internet");
                }

            }
        });

        /* FOR DEBUGGING PURPOSES - WANT TO SEE USERS IN THE INDEX */

        MoodPlus moodPlus = MoodPlusApplication.getMoodPlus(); // Taken from FillerCreep
        moodPlus.addView(this); // Taken from FillerCreep

        ElasticsearchMPController.GetUsersTask getUsersTask = new ElasticsearchMPController.GetUsersTask();
        getUsersTask.execute("");

        try {
            participantList = getUsersTask.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }

        adapter = new ArrayAdapter<Participant>(this, R.layout.participant_list, participantList);
        participants.setAdapter(adapter);


        Button getButton = (Button) findViewById(R.id.get_users);

        getButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String strUser = userName.getText().toString();

                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                MoodPlus model = MoodPlusApplication.getMoodPlus();
                model.getParticipantElastic(strUser);

                boolean seen = true;
                int pListSize = participantList.size();

                for (int i = 0; i<pListSize; i++) {
                    String user = participantList.get(i).getUserName();
                    Log.i("error","Participant: "+ user);
                }

                for (int i = 0; i<pListSize; i++) {
                    if (participantList.get(i).getUserName().equals(strUser)){
                        Log.i("Error:","THE PARTICIPANT EXISTSSSSS OMG");
                        seen = true;
                        break;
                        //throw new IllegalArgumentException("Participant exists.");
                    } else {
                        seen = false;
                    }
                }

                Log.i("Error", "seen is: " + seen);

                // Will only continue if connected to the internet.
                if (seen== true){
                    userName.setError("User Name already taken. Try again. ");
                }

                else if (isConnected) {
                    setResult(RESULT_OK);
                    Participant newParticipant = new Participant(strUser);
                    ElasticsearchMPController.AddUsersTask addUser = new ElasticsearchMPController.AddUsersTask();
                    addUser.execute(newParticipant);
                    //finish();
                    //startActivity(getIntent());

                    model.getParticipantElastic(strUser);
                    Intent intent = new Intent(WelcomeActivity.this, MoodPlusActivity.class);
                    startActivity(intent);
                }
                else {
                    userName.setError("Not Connected To the Internet");
                }
            }
        });
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
            Type listType = new TypeToken<ArrayList<Participant>>() {
            }.getType();
            participantList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            participantList = new ArrayList<Participant>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //loadFromFile(); // TODO replace this with elastic search

        ElasticsearchMPController.GetUsersTask getUsersTask = new ElasticsearchMPController.GetUsersTask();
        getUsersTask.execute("");

        try {
            participantList = getUsersTask.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }

        adapter = new ArrayAdapter<Participant>(this, R.layout.participant_list, participantList);
        participants.setAdapter(adapter);

    }

    @Override
    public void update(MoodPlus model) {

    }

}



