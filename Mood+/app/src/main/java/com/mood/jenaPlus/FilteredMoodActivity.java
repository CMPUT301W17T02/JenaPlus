package com.mood.jenaPlus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class FilteredMoodActivity extends AppCompatActivity implements MPView<MoodPlus> {

    protected ListView moodListView;
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;
    ArrayList<Mood> filteredArrayList = new ArrayList<>();
    String moodString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_filtered_mood);
        TextView test = (TextView) findViewById(R.id.test_string);
        moodListView = (ListView) findViewById(R.id.listView);

        /*------------- LOADING THE MOOD  -------------*/

        Bundle bundle = getIntent().getExtras();
        moodString = bundle.getString("moodString");

        /*---------- LOADING THE PARTICIPANT-------------*/

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        String name = participant.getUserName();
        String id = participant.getId();
        String who = "Name: " + name + "\nMood: " + moodString;
        test.setText(who);

        /*------------------------------------------------*/


        moodListView.setAdapter(adapter);
        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FilteredMoodActivity.this, ViewMoodActivity.class);
                intent.putExtra("aMood", (Serializable) moodListView.getItemAtPosition(position));
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        Bundle bundle = getIntent().getExtras();
        moodString = bundle.getString("moodString");

        myMoodList = participant.getUserMoodList();
        moodArrayList = myMoodList.getFilteredMood(moodString);

       //adapter = new ArrayAdapter<Mood>(this, R.layout.mood_plus_listview, moodArrayList);
        adapter = new MoodListAdapter(FilteredMoodActivity.this,moodArrayList);
        moodListView.setAdapter(adapter);

    }

    @Override
    public void update(MoodPlus moodPlus) {

    }
}