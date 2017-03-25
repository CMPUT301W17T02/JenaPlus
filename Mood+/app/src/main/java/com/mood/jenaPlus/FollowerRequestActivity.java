package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FollowerRequestActivity extends AppCompatActivity implements MPView<MoodPlus> {

    private ArrayList<Participant> participantList;
    private ArrayList<String> participantListStr;
    private ArrayAdapter<Participant> adapter;
    protected ListView participantListView;

    Context context = this;

    protected int longClickedItemIndex;
    private static final int ACCEPT = 0;
    private static final int REJECT = 1;
    protected MainMPController mpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_request);
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
            mpController = MoodPlusApplication.getMainMPController();
            Participant participant = mpController.getParticipant();
            participantList = participant.getFollowList().getPendingFollowers();
            if (participantList.size() < 1){
                noFollowerRequests();
            }
            adapter = new ArrayAdapter<Participant>(this, R.layout.participant_list, participantList);
            participantListView.setAdapter(adapter);

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }
    }



    public void noFollowerRequests() {
        new AlertDialog.Builder(context)
                .setTitle("Follow Requests")
                .setMessage("No followers are requesting to follow you at this time.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);
        menu.add(Menu.NONE,ACCEPT,menu.NONE,"ACCEPT REQUEST");
        menu.add(Menu.NONE,REJECT,menu.NONE,"REJECT REQUEST");
    }

    public boolean onContextItemSelected(MenuItem item){
        Participant participant = (Participant) participantListView.getItemAtPosition(longClickedItemIndex);
        Log.i("maybe?", participant.getUserName());
        MainMPController mpController = MoodPlusApplication.getMainMPController();
        switch (item.getItemId()){
            case ACCEPT:
                mpController.acceptRequest(participant);
                break;
            case REJECT:
                mpController.rejectRequest(participant);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void update(MoodPlus model) {

    }
}
