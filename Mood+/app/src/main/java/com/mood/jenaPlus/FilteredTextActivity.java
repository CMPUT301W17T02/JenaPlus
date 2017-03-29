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

public class FilteredTextActivity extends AppCompatActivity implements MPView<MoodPlus>{

    protected ListView moodListView;
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;
    ArrayList<Mood> filteredArrayList = new ArrayList<>();
    String keyword = "";

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        TextView test = (TextView) findViewById(R.id.test_string);
        moodListView = (ListView) findViewById(R.id.listView);

        /*------------- LOADING THE KEYWORD  -------------*/

        Bundle bundle = getIntent().getExtras();
        keyword = bundle.getString("testText");

        //getNewList();

        /*---------- LOADING THE PARTICIPANT-------------*/

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        String name = participant.getUserName();
        String id = participant.getId();
        String who = "Name: "+ name + "\nkeyword: " + keyword;
        //test.setText(who);

        /*------------------------------------------------*/


        moodListView.setAdapter(adapter);
        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FilteredTextActivity.this, ViewMoodActivity.class);
                intent.putExtra("aMood", (Serializable) moodListView.getItemAtPosition(position));
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        moodArrayList.clear();
        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        Bundle bundle = getIntent().getExtras();
        keyword = bundle.getString("testText");

        myMoodList = participant.getUserMoodList();
        ArrayList<Mood> tempArrayList = myMoodList.getFilteredMoodText(keyword);
        List<Mood> temp = tempArrayList;
        String dateTest = bundle.getString("filterRecent");
        String moodId = bundle.getString("moodString");
        String locationBool = bundle.getString("filterLocation");

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

        if (moodArrayList.size() < 1) {
            noMoods();
        }

        adapter = new MoodListAdapter(FilteredTextActivity.this,moodArrayList);
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
    public void update(MoodPlus moodPlus){

    }

    boolean isWithinRange(Date testDate) {
        Date endDate = new Date();
        Date startDate = new Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000);
        return !(testDate.before(startDate) || testDate.after(endDate));
    }

}
