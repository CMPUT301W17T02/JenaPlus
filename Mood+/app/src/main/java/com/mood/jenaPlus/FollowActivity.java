package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * This activity is used when a participant wants to follow another participant.
 * From clicking the top right button on the main activity.
 **/

public class FollowActivity extends AppCompatActivity implements MPView<MoodPlus>,SearchView.OnQueryTextListener{

    private EditText searchUser;
    SearchView searchUsers;
    private ArrayList<Participant> participantList = new ArrayList<Participant>();
    private ArrayAdapter<Participant> adapter;
    protected ListView participantListView ;

    Context context = this;

    protected int longClickedItemIndex;
    private static final int FOLLOW = 0;

    protected MainMPController mpController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        participantListView = (ListView) findViewById(R.id.listViewSearch);

        searchUser = (EditText) findViewById(R.id.searchUsers);
        Button button = (Button) findViewById(R.id.SearchButton);

        /* ------------ Search View ---------------- */
        searchUsers = (SearchView) findViewById(R.id.search_user);
        searchUsers.setQueryHint("Search Users");
        searchUsers.setIconifiedByDefault(false);
        searchUsers.setOnQueryTextListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                int listSize = participantList.size();
                ArrayList<Participant> tempArrayList1 = participantList;
                ArrayList<Participant> tempArrayList2 = new ArrayList<>();
                String m1 = searchUser.getText().toString();
                for(int i =0; i<listSize; i++) {
                    String m2 = tempArrayList1.get(i).getUserName();
                    if (m2.toLowerCase().contains(m1.toLowerCase())) {
                        tempArrayList2.add(tempArrayList1.get(i));
                    }
                }

                if (tempArrayList2.size() < 1) {
                    noUserAlert();
                } else {
                    participantList.clear();
                    participantList.addAll(tempArrayList2);
                    adapter.notifyDataSetChanged();
                }

            }


        });

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
        // TODO Auto-generated method stub
        super.onStart();
        //loadFromFile(); // TODO replace this with elastic search

        ElasticsearchMPController.GetUsersTask getUsersTask = new ElasticsearchMPController.GetUsersTask();
        getUsersTask.execute("");

        try {
            participantList = getUsersTask.get();
            mpController = MoodPlusApplication.getMainMPController();
            Participant participant = mpController.getParticipant();
            Log.i("want to remove", participant.getUserName());
            participantList.remove(participant);
            adapter = new FollowAdapter(this,participantList);
            participantListView.setAdapter(adapter);
            for(int i= 0; i < participantList.size(); i++){
                Log.i("participant",participantList.get(i).getUserName());
            }

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }
    }

    @Override
    public void update(MoodPlus model) {

    }

    public void noUserAlert() {
        new AlertDialog.Builder(context)
                .setTitle("Search Failed")
                .setMessage("No users found.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        participantList.clear();
        if (charText.length() == 0) {
            participantList.addAll(participantList);
        } else {
            for (Participant wp : participantList) {
                if (wp.getUserName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    participantList.add(wp);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        filter(text);

        return false;
    }
}


