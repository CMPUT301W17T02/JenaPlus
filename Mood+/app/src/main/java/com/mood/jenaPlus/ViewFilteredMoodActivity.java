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
    ArrayList<Mood> filteredArrayList = new ArrayList<>();
    String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_filtered_mood);
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
        String who = "Name: "+ name + ", id: "+id+"\nkeyword: " + keyword;
        test.setText(who);

        /*------------------------------------------------*/

    }

    @Override
    protected void onStart(){
        super.onStart();

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        Bundle bundle = getIntent().getExtras();
        keyword = bundle.getString("testText");

        myMoodList = participant.getUserMoodList();
        moodArrayList = myMoodList.getFilteredMoodText(keyword);

        adapter = new ArrayAdapter<Mood>(this, R.layout.mood_plus_listview, moodArrayList);
        moodListView.setAdapter(adapter);
    }

    @Override
    public void update(MoodPlus moodPlus){

    }

    public void getNewList() {
        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        myMoodList = participant.getUserMoodList();
        int listSize = moodArrayList.size();

        for (int i = 0; i<listSize; i++){
            String m1 = keyword;
            String m2 = myMoodList.getUserMood(i).getText();
            if (m2.toLowerCase().contains(m1.toLowerCase())) {
                filteredArrayList.add(myMoodList.getUserMood(i));
            }
        }

        moodArrayList.clear();
        moodArrayList.addAll(filteredArrayList);
        adapter.notifyDataSetChanged();
    }
}
