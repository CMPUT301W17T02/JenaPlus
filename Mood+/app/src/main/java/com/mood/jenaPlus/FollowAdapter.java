package com.mood.jenaPlus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * The FollowAdapter class created a listView for FollowActivity,
 * this listView containts follow button.
 */

public class FollowAdapter extends ArrayAdapter<Participant> {

    Context context;
    protected Button followButton;

    public FollowAdapter(Context context, ArrayList<Participant> participantList) {
        super(context, 0, participantList);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        final Participant participant = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.following_listview,parent,false);

        TextView userName = (TextView) view.findViewById(R.id.username);

        userName.setText(participant.getUserName());

        followButton = (Button) view.findViewById(R.id.follow);

        followButton.setOnClickListener(new View.OnClickListener() {
            MainMPController mpController = MoodPlusApplication.getMainMPController();
            Participant mainParticipant = mpController.getParticipant();
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"FOLLOWING " + participant.getUserName(), Toast.LENGTH_SHORT).show();

                Boolean seen = false;
                Boolean seen2 = false;
                ArrayList<String> users = mainParticipant.getFollowingList();
                for(String s: users) {
                    if (s.equals(participant.getUserName())) {
                        seen = true;
                        break;
                    }
                }
                ArrayList<String> users2 = mainParticipant.getPendingFollowing();
                for(String s: users2) {
                    if (s.equals(participant.getUserName())) {
                        seen2 = true;
                        break;
                    }
                }

                if(seen) {
                    alreadyFollowing(participant.getUserName());
                }
                if(seen2) {
                    pendingFollowing(participant.getUserName());
                }
                if(mainParticipant.getUserName().equals(participant.getUserName())) {
                    ownFollow();
                }
                else {
                    Log.i("fromfollowactivity", participant.getUserName());
                    MainMPController mpController = MoodPlusApplication.getMainMPController();
                    mpController.addFollowRequest(participant.getUserName());
                    mpController.setPendingFollowers(participant.getUserName());
                }
            }
        });

        return view;
    }

    public void alreadyFollowing(String s) {
        new AlertDialog.Builder(context)
                .setTitle("Already Following")
                .setMessage("You are already following "+s)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void pendingFollowing(String s) {
        new AlertDialog.Builder(context)
                .setTitle("Already Requesting")
                .setMessage("You are already requesting to follow "+s)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void ownFollow() {
        new AlertDialog.Builder(context)
                .setTitle("Follow yourself")
                .setMessage("Sorry, you cannot follow yourself")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
