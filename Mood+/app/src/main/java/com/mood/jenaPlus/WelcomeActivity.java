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

    private ParticipantList checkList;
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

<<<<<<< HEAD
                String strUser = userName.getText().toString();
                MoodPlus model = MoodPlusApplication.getMoodPlus();
                model.getUsingParticipantUsername(strUser);

                // Will only continue if connected to the internet. 
                if (isConnected)  {
=======
                // Will only continue if connected to the internet.
                if (isConnected) {
                    String strUser = userName.getText().toString();
                    MoodPlus model = MoodPlusApplication.getMoodPlus();
                    model.getUsingParticipantUsername(strUser);
>>>>>>> master
                    Intent intent = new Intent(WelcomeActivity.this, MoodPlusActivity.class);
                    startActivity(intent);
                } else {
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
                String text = userName.getText().toString();
                ElasticsearchMPController.GetOneUserTask getOneUserTask = new ElasticsearchMPController.GetOneUserTask();
                getOneUserTask.execute(text);

                try {
                    participantList.clear();
                    //participantList.addAll((Collection<? extends Participant>) getOneUserTask.get());
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /*@Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //loadFromFile(); // TODO replace this with elastic search
        ElasticsearchMPController.GetUsersTask getUsersTask = new ElasticsearchMPController.GetUsersTask();
        getUsersTask.execute("");
        try{
            participantList = getUsersTask.get();
        }catch (Exception e){
            Log.i("Error","Failed to get the users out of the async object");
        }
        adapter = new ArrayAdapter<Participant>(this, R.layout.participant_list, participantList);
        participants.setAdapter(adapter);
    }*/


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
    public void update(MoodPlus model) {

    }
}



