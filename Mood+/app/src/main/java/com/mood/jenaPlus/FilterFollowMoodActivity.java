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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * This activity shows only one type of mood that was specified from the option in the main
 * activity. The moods come from all of the people the participant is following.
 **/

public class FilterFollowMoodActivity extends AppCompatActivity implements MPView<MoodPlus>{

    protected ListView moodListView;
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private ArrayAdapter<Mood> adapter;
    String moodString;

    Context context = this;

    protected Button viewMapButton;
    protected MainMPController mpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        moodListView = (ListView) findViewById(R.id.listView);

        /* -------------- VIEW MAP BUTTON ---------------*/
        viewMapButton = (Button) findViewById(R.id.view_map_button);

        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterFollowMoodActivity.this, MarkerActivity.class);
                intent.putExtra("participant_moodProvider", moodArrayList);
                startActivity(intent);
            }
        });


        moodListView.setAdapter(adapter);
        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FilterFollowMoodActivity.this, ViewMoodActivity.class);
                intent.putExtra("aMood", (Serializable) moodListView.getItemAtPosition(position));
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();
        moodArrayList.clear();
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();

        mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();
        ArrayList<String> participantListStr = participant.getFollowingList();

        Bundle bundle = getIntent().getExtras();
        moodString = bundle.getString("testText");
        String moodId = bundle.getString("moodString");
        String locationBool = bundle.getString("filterLocation");
        String dateTest = bundle.getString("filterRecent");
        ArrayList<Mood> first = new ArrayList<>();

        for (int i = 0; i<participantListStr.size(); i++) {
            Participant tempParticipant =  eController.getUsingParticipant(participantListStr.get(i));
            ArrayList<Mood> tempArrayList = tempParticipant.getUserMoodList().getUserMoodList();

            for (Mood m : tempArrayList) {
                if (m.getId().equals(moodId)) {
                    first.add(m);
                }
            }
        }

        List<Mood> temp = first;

        if (!moodId.equals("no")){
            Log.i("moodstring", moodId);
            for(Iterator<Mood> iterator = temp.iterator(); iterator.hasNext();) {
                Mood mood = iterator.next();
                if(!mood.getId().equals(moodId)){
                    iterator.remove();
                }
            }

        }

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
        } else {
            if (locationBool.equals("yes")) {
                viewMapButton.setVisibility(View.VISIBLE);
            }
        }

        adapter = new MoodFollowerListAdapter(FilterFollowMoodActivity.this,moodArrayList);
        getUserMoodOrderedList();

        moodListView.setAdapter(adapter);

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

    public ArrayList<Mood> getUserMoodOrderedList() {

        Collections.sort(moodArrayList, new Comparator<Mood>() {

            public int compare(Mood o1, Mood o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return this.moodArrayList;
    }

    boolean isWithinRange(Date testDate) {
        Date endDate = new Date();
        Date startDate = new Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000);
        return !(testDate.before(startDate) || testDate.after(endDate));
    }

}
