package com.mood.jenaPlus;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewFilteredMoodActivity extends AppCompatActivity implements MPView<MoodPlus>{

    protected ListView moodListView;
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();


    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_filtered_mood);
        TextView test = (TextView) findViewById(R.id.test_string);

        /*---------- LOADING THE PARTICIPANT-------------*/

        final MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        String name = participant.getUserName();
        String id = participant.getId();
        String who = "Name: "+ name + ", id: "+id;
        test.setText(who);

        /*------------------------------------------------*/

    }

    @Override
    protected void onStart(){
        super.onStart();

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();
        myMoodList = participant.getUserMoodList();
        moodArrayList = myMoodList.getUserMoodOrderedList();

        adapter = new ArrayAdapter<Mood>(this, R.layout.mood_plus_listview, moodArrayList);
        moodListView.setAdapter(adapter);
    }


    @Override
    public void update(MoodPlus moodPlus){

    }
}
