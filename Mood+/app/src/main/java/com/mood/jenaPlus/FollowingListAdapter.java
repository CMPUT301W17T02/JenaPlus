package com.mood.jenaPlus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The FollowingListAdapter class created a listView for FollowingListActivity,
 * this listView containts unfollow button.
 */

public class FollowingListAdapter extends ArrayAdapter<Participant> {

    Context context;
    protected Button unFollowButton;

    public FollowingListAdapter(Context context, ArrayList<Participant> participantList) {
        super(context, 0, participantList);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        final Participant participant = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.unfollow_listview,parent,false);

        TextView userName = (TextView) view.findViewById(R.id.username);

        userName.setText(participant.getUserName());

        unFollowButton = (Button) view.findViewById(R.id.unfollow);

        unFollowButton.setOnClickListener(new View.OnClickListener() {
            MainMPController mpController = MoodPlusApplication.getMainMPController();
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Unfollowing " + participant.getUserName(), Toast.LENGTH_SHORT).show();
                mpController.unfollowParticipant(participant.getUserName());

                v.setEnabled(false);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
