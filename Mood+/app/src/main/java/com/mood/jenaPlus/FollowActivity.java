package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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



}


