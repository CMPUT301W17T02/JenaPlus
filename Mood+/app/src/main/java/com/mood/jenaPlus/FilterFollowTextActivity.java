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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * This activity is used to view all of the moods of people that the participant
 * follows. It will show the mood events that contain certain text or key word.
 **/

public class FilterFollowTextActivity extends AppCompatActivity implements MPView<MoodPlus>{

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
                Intent intent = new Intent(FilterFollowTextActivity.this, MarkerActivity.class);
                intent.putExtra("participant_moodProvider", moodArrayList);
                startActivity(intent);
            }
        });


        moodListView.setAdapter(adapter);
        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FilterFollowTextActivity.this, ViewMoodActivity.class);
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
        String s = moodString;
        String dateTest = bundle.getString("filterRecent");
        ArrayList<Mood> first = new ArrayList<>();

        for (int i = 0; i<participantListStr.size(); i++) {
            Participant tempParticipant =  eController.getUsingParticipant(participantListStr.get(i));
            ArrayList<Mood> tempArrayList = tempParticipant.getUserMoodList().getUserMoodList();

            for (Mood m : tempArrayList) {
                if (m.getText().toLowerCase().contains(s.toLowerCase())) {
                    first.add(m);
                }
            }
        }

        List<Mood> temp = first;

        if (moodId.equals("surprised") || moodId.equals("disgust") || moodId.equals("fear") ||
                moodId.equals("confused") || moodId.equals("happy") || moodId.equals("angry") ||
                moodId.equals("sad") || moodId.equals("shame") || moodId.equals("annoyed")){
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
            if(locationBool.equals("yes")){
                viewMapButton.setVisibility(View.VISIBLE);
            }
        }

        adapter = new MoodFollowerListAdapter(FilterFollowTextActivity.this,moodArrayList);
        getUserMoodOrderedList();

        //String d = "Search keyword: "+moodString;

        moodListView.setAdapter(adapter);

    }

    public void noMoods() {
        new AlertDialog.Builder(context)
                .setTitle("No Moods")
                .setMessage("No moods were found.")
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
