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

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FilteredLocationActivity extends AppCompatActivity implements MPView<MoodPlus> {

    protected ListView moodListView;
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;

    Context context = this;

    protected Button viewMapButton;
    ArrayList<Mood> locationMoodList = new ArrayList<Mood>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        TextView test = (TextView) findViewById(R.id.test_string);
        moodListView = (ListView) findViewById(R.id.listView);

        /* -------------- VIEW MAP BUTTON ---------------*/
        viewMapButton = (Button) findViewById(R.id.view_map_button);

        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilteredLocationActivity.this, MarkerActivity.class);
                intent.putExtra("user_moodProvider", locationMoodList);
                startActivity(intent);
            }
        });

        /* -------------- VIEW MAP BUTTON ---------------*/


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
                Intent intent = new Intent(FilteredLocationActivity.this, ViewMoodActivity.class);
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

        myMoodList = participant.getUserMoodList();
        ArrayList<Mood> tempArrayList = myMoodList.getFilteredLocation();

        Bundle bundle = getIntent().getExtras();
        String dateTest = bundle.getString("filterRecent");
        //String locationBool = bundle.getString("filterLocation");
        Log.i("date",dateTest);

        List<Mood> temp = tempArrayList;

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

        adapter = new MoodListAdapter(FilteredLocationActivity.this,moodArrayList);
        moodListView.setAdapter(adapter);

        // Getting all the moods with locations
        for (int i=0; i<moodArrayList.size();i++){
            ArrayList<Mood> userMoods = moodArrayList;
            if(userMoods.get(i).getAddLocation().equals(true)){
                locationMoodList.add(userMoods.get(i));
            }
        }

        // If there is location in the moodList set button visible
        if(!locationMoodList.isEmpty()){
            viewMapButton.setVisibility(View.VISIBLE);
        }
    }

    public void noMoods() {
        new AlertDialog.Builder(context)
                .setTitle("No Moods")
                .setMessage("No moods with a location were found.")
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
