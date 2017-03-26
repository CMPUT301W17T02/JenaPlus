package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FollowerViewActivity extends Fragment implements MPView<MoodPlus>{

    protected ListView moodListView;
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;

    protected MainMPController mpController;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        return  inflater.inflate(R.layout.activity_follower_view,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView test = (TextView) getView().findViewById(R.id.test_string);
        moodListView = (ListView) getView().findViewById(R.id.listView);


        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        String name = participant.getUserName();
        String id = participant.getId();
        String who = "Username: " + name ;
        test.setText(who);



        moodListView.setAdapter(adapter);
        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewMoodActivity.class);
                intent.putExtra("aMood", (Serializable) moodListView.getItemAtPosition(position));
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        ElasticsearchMPController eController = MoodPlusApplication.getElasticsearchMPController();

        mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();
        ArrayList<String> participantListStr = participant.getFollowingList();
        if (participantListStr.size() < 1){
            noMoods();
        }

        for (int i = 0; i<participantListStr.size(); i++) {
            Participant tempParticipant =  eController.getUsingParticipant(participantListStr.get(i));
            ArrayList<Mood> partMoods = tempParticipant.getUserMoodList().getUserMoodList();
            for (Mood m : partMoods) {
                moodArrayList.add(m);
            }
        }

        adapter = new MoodFollowerListAdapter(getActivity(),moodArrayList);
        getUserMoodOrderedList();

        moodListView.setAdapter(adapter);

    }

    public void noMoods() {
        new AlertDialog.Builder(context)
                .setTitle("No Moods")
                .setMessage("All of your followers do not have any available moods.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public ArrayList<Mood> getUserMoodOrderedList() {

        Collections.sort(moodArrayList, new Comparator<Mood>() {

            public int compare(Mood o1, Mood o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return this.moodArrayList;
    }



    @Override
    public void update(MoodPlus moodPlus) {

    }

}
