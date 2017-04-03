package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This class is used to view all the participants someone follows.
 * From this class you can unfollow using long click, or if you short
 * click on a participant you can view their mood list.
 * **/

public class FollowingListActivity extends AppCompatActivity implements MPView<MoodPlus> {

    private ArrayList<Participant> participantList;
    private ArrayList<String> participantListStr;
    private ArrayAdapter<Participant> adapter;
    protected ListView participantListView;

    Context context = this;

    protected int longClickedItemIndex;
    protected MainMPController mpController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        participantListView = (ListView) findViewById(R.id.listViewSearch);

        registerForContextMenu(participantListView);
        participantListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();
            mpController = MoodPlusApplication.getMainMPController();
            Participant participant = mpController.getParticipant();
            participantListStr = participant.getFollowingList();
            if (participantListStr.size() < 1){
                noFollowerRequests();
            }
            ArrayList<Participant> tempArray = new ArrayList<>();
            for (int i = 0; i<participantListStr.size(); i++) {
                Participant tempParticipant =  eController.getUsingParticipant(participantListStr.get(i));
                tempArray.add(tempParticipant);
            }

            participantList = tempArray;
            adapter = new FollowingListAdapter(this,participantList);
            participantListView.setAdapter(adapter);

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }
    }

    public void noFollowerRequests() {
        new AlertDialog.Builder(context)
                .setTitle("Following")
                .setMessage("You are currently not following anyone.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void update(MoodPlus model) {

    }
}


