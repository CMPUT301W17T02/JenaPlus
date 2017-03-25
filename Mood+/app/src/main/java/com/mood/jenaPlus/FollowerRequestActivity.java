package com.mood.jenaPlus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FollowerRequestActivity extends AppCompatActivity implements MPView<MoodPlus> {

    private ArrayList<Participant> participantList;
    private ArrayAdapter<Participant> adapter;
    protected ListView participantListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_request);
        participantListView = (ListView) findViewById(R.id.listViewSearch);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //loadFromFile(); // TODO replace this with elastic search

        try {
            adapter = new ArrayAdapter<Participant>(this, R.layout.participant_list, participantList);
            participantListView.setAdapter(adapter);

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }
    }

    @Override
    public void update(MoodPlus model) {

    }
}
