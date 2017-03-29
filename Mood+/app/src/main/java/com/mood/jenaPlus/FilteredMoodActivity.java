package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FilteredMoodActivity extends AppCompatActivity implements MPView<MoodPlus> {

    protected ListView moodListView;
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;
    String moodString = "";

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_filtered_mood);
        moodListView = (ListView) findViewById(R.id.listView);

        /*------------- LOADING THE MOOD  -------------*/

//        Bundle bundle = getIntent().getExtras();
//        moodString = bundle.getString("moodString");

        /*---------- LOADING THE PARTICIPANT-------------*/

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

//        String name = participant.getUserName();
//        String id = participant.getId();
//        String who = "Name: " + name + "\nMood: " + moodString;
//        test.setText(who);
//
//        /*------------------------------------------------*/


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

        String dateTest = bundle.getString("filterRecent");
        String locationBool = bundle.getString("filterLocation");
        Log.i("date",dateTest);

        myMoodList = participant.getUserMoodList();
        ArrayList<Mood> tempArrayList = myMoodList.getFilteredMood(moodString);
        List<Mood> temp = tempArrayList;

        if(locationBool.equals("yes")) {
            for(Iterator<Mood> iterator = temp.iterator(); iterator.hasNext();) {
                Mood mood = iterator.next();
                if (!mood.getAddLocation()) {
                    iterator.remove();
                }
            }
        }

        if (dateTest.equals("yes")) {
            for(Iterator<Mood> iterator = temp.iterator(); iterator.hasNext();) {
                Mood mood = iterator.next();
                Date tempDate = mood.getDate();
                if(!isWithinRange(tempDate)){
                    iterator.remove();
                }
            }
        }

        moodArrayList.addAll(temp);


        if (moodArrayList.size() <1) {
            noMoods();
        }

        adapter = new MoodListAdapter(FilteredMoodActivity.this,moodArrayList);
        moodListView.setAdapter(adapter);


        TextView test = (TextView) findViewById(R.id.test_string);
        String who = "Mood: " + moodString;
        //test.setText(who);

    }

    public void noMoods() {
        new AlertDialog.Builder(context)
                .setTitle("No Moods")
                .setMessage("No " +moodString+" moods were found.")
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

    boolean isWithinRange(Date testDate) {
        Date endDate = new Date();
        Date startDate = new Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000);
        return !(testDate.before(startDate) || testDate.after(endDate));
    }
}