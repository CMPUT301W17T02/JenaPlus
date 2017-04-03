package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This activity is used to view the follower requests
 * From clicking the menu in the Navigation drawer.
 *
 * @author Carlo
 * @author Carrol
 * @version 1.0
 **/

public class FollowerRequestActivity extends AppCompatActivity implements MPView<MoodPlus> {

    private ArrayList<Participant> participantList;
    private ArrayList<String> participantListStr;
    private ArrayAdapter<Participant> adapter;
    protected ListView participantListView;

    Context context = this;
    protected MainMPController mpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_request);
        participantListView = (ListView) findViewById(R.id.listViewSearch);

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
            mpController = MoodPlusApplication.getMainMPController();
            Participant participant = mpController.getParticipant();
            participantListStr = participant.getPendingFollowers();
            if (participantListStr.size() < 1){
                noFollowerRequests();
            }
            ArrayList<Participant> tempArray = new ArrayList<>();
            for (int i = 0; i<participantListStr.size(); i++) {
                Participant tempParticipant =  eController.getUsingParticipant(participantListStr.get(i));
                tempArray.add(tempParticipant);
            }

            participantList = tempArray;
            adapter = new FollowerRequestAdapter(this,participantList);
            participantListView.setAdapter(adapter);

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }
    }


    public void noFollowerRequests() {
        new AlertDialog.Builder(context)
                .setTitle("Follow Requests")
                .setMessage("No followers are requesting to follow you at this time.")
                .setPositiveButton("Return to main", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Attempt to refresh follower list", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        overridePendingTransition(0, 0);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void update(MoodPlus model) {

    }
}
