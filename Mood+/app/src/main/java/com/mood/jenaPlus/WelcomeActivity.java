package com.mood.jenaPlus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

/**
 * This is the start up activity, allow the user to log in to
 * the MoodPlus Application. If a user exists they will not be able to log in, and will be
 * prompted to enter in a new user name. A user name cannot be empty.
 *
 * @author Carlo
 * @version 1.0
 */

public class WelcomeActivity extends AppCompatActivity implements MPView<MoodPlus> {

    private EditText userName;
    private ArrayList<Participant> participantList = new ArrayList<Participant>();
    private static final String FILENAME = "moodPlus.sav";


    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (EditText) findViewById(R.id.loginUserName);
        Button button = (Button) findViewById(R.id.Login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Taken from http://stackoverflow.com/questions/7071578/
                // connectivitymanager-to-verify-internet-connection
                // 03-07-2017 23:06
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                String strUser = userName.getText().toString();
                MoodPlus model = MoodPlusApplication.getMoodPlus();
                model.getParticipantElastic(strUser);

                boolean seen = false;
                int pListSize = participantList.size();

                for (int i = 0; i<pListSize; i++) {
                    if (participantList.get(i).getUserName().equals(strUser)){
                        seen = true;
                        break;
                    } else {
                        seen = false;
                    }
                }

                // Will only continue if connected to the internet.
                if (strUser.equals("")){
                    userName.setError("No users have an empty user name.");
                }
                else if(!isConnected) {
                    userName.setError("Not Connected To the Internet");
                }
                else if (!seen){
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

        MoodPlus moodPlus = MoodPlusApplication.getMoodPlus(); // Taken from FillerCreep
        moodPlus.addView(this); // Taken from FillerCreep

        ElasticsearchMPController.GetUsersTask getUsersTask =
                new ElasticsearchMPController.GetUsersTask();
        getUsersTask.execute("");

        try {
            participantList = getUsersTask.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }

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
                    if (participantList.get(i).getUserName().equals(strUser)){
                        seen = true;
                        break;
                        //throw new IllegalArgumentException("Participant exists.");
                    } else {
                        seen = false;
                    }
                }

                // Will only continue if connected to the internet.
                if (strUser.equals("")){
                    userName.setError("Cannot add an empty user name");
                }
                else if (seen){
                    userName.setError("User Name already taken. Try again. ");
                }

                else if (isConnected) {
                    setResult(RESULT_OK);
                    Participant newParticipant = new Participant(strUser);
                    ElasticsearchMPController.AddUsersTask addUser =
                            new ElasticsearchMPController.AddUsersTask();
                    addUser.execute(newParticipant);

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

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //loadFromFile(); // TODO replace this with elastic search

        ElasticsearchMPController.GetUsersTask getUsersTask =
                new ElasticsearchMPController.GetUsersTask();
        getUsersTask.execute("");

        try {
            participantList = getUsersTask.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }
    }

    @Override
    public void update(MoodPlus model) {

    }

}



