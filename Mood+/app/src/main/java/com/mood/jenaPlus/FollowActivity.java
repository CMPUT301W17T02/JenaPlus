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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FollowActivity extends AppCompatActivity implements MPView<MoodPlus>{

    private EditText searchUser;
    private ArrayList<Participant> participantList = new ArrayList<Participant>();
    private ArrayAdapter<Participant> adapter;
    protected ListView participantListView ;

    Context context = this;

    protected int longClickedItemIndex;
    private static final int FOLLOW = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        participantListView = (ListView) findViewById(R.id.listViewSearch);

        searchUser = (EditText) findViewById(R.id.searchUsers);
        Button button = (Button) findViewById(R.id.SearchButton);

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
            adapter = new ArrayAdapter<Participant>(this, R.layout.participant_list, participantList);
            participantListView.setAdapter(adapter);

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

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);
        menu.add(Menu.NONE,FOLLOW,menu.NONE,"FOLLOW");
    }


    /*-------------ALL THE BIG STUFF HAPPENING HERE!-----------------*/

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case FOLLOW:
                Participant participant = (Participant) participantListView.getItemAtPosition(longClickedItemIndex);
                Log.i("fromfollowactivity", participant.getUserName());
                MainMPController mpController = MoodPlusApplication.getMainMPController();
                mpController.addFollowRequest(participant.getUserName());
                mpController.setPendingFollowers(participant.getUserName());
                break;
        }

        return super.onContextItemSelected(item);
    }



}


