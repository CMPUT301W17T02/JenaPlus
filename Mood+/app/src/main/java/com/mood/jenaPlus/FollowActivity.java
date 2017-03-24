package com.mood.jenaPlus;

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
    private ArrayList<Participant> participantList1 = new ArrayList<Participant>();
    private ArrayList<Participant> finalList = new ArrayList<>();
    private ArrayAdapter<Participant> adapter;
    protected ListView participantList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        participantList = (ListView) findViewById(R.id.listViewSearch);

        final int listSize = participantList1.size();

        searchUser = (EditText) findViewById(R.id.searchUsers);
        Button button = (Button) findViewById(R.id.SearchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                ArrayList<Participant> tempArrayList1 = participantList1;
                ArrayList<Participant> tempArrayList2 = new ArrayList<>();
                for(int i =0; i<listSize; i++) {
                    String m1 = searchUser.getText().toString();
                    String m2 = tempArrayList1.get(i).getUserName();
                    Log.i("print", m2);
                    if (m2.toLowerCase().contains(m1.toLowerCase())) {
                        tempArrayList2.add(tempArrayList1.get(i));
                    }
                }
                participantList1.clear();
                participantList1.addAll(finalList);
                adapter.notifyDataSetChanged();


            }


        });

        adapter = new ArrayAdapter<Participant>(this, R.layout.participant_list, finalList);
        participantList.setAdapter(adapter);

    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //loadFromFile(); // TODO replace this with elastic search

        ElasticsearchMPController.GetUsersTask getUsersTask = new ElasticsearchMPController.GetUsersTask();
        getUsersTask.execute("");

        try {
            participantList1 = getUsersTask.get();
            adapter = new ArrayAdapter<Participant>(this, R.layout.participant_list, participantList1);
            participantList.setAdapter(adapter);

        } catch (Exception e) {
            Log.i("Error", "Failed to get the users out of the async object");
        }
    }

    @Override
    public void update(MoodPlus model) {

    }

}


