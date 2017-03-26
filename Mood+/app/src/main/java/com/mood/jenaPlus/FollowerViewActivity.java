package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class FollowerViewActivity extends AppCompatActivity implements MPView<MoodPlus>{

    protected ListView moodListView;
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;

    protected MainMPController mpController;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_view);
        TextView test = (TextView) findViewById(R.id.test_string);
        moodListView = (ListView) findViewById(R.id.listView);


        /*---------- LOADING THE PARTICIPANT-------------*/

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        String name = participant.getUserName();
        String id = participant.getId();
        String who = "Username: " + name ;
        test.setText(who);

        /*------------------------------------------------*/


        moodListView.setAdapter(adapter);
        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FollowerViewActivity.this, ViewMoodActivity.class);
                intent.putExtra("aMood", (Serializable) moodListView.getItemAtPosition(position));
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();

        mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();
        ArrayList<String> participantListStr = participant.getFollowingList();
        if (participantListStr.size() < 1){
            noMoods();
        }
        //ArrayList<Participant> tempArrayParticipant = new ArrayList<>();
        //ArrayList<Mood> tempArrayMood = new ArrayList<>();
        for (int i = 0; i<participantListStr.size(); i++) {
            Participant tempParticipant =  eController.getUsingParticipant(participantListStr.get(i));
            ArrayList<Mood> partMoods = tempParticipant.getUserMoodList().getUserMoodList();
            for (Mood m : partMoods) {
                moodArrayList.add(m);
            }
        }

        adapter = new MoodFollowerListAdapter(FollowerViewActivity.this,moodArrayList);
        moodListView.setAdapter(adapter);

    }

    public void noMoods() {
        new AlertDialog.Builder(context)
                .setTitle("No Moods")
                .setMessage("All of your followers do not have any available moods.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



    @Override
    public void update(MoodPlus moodPlus) {

    }

}
