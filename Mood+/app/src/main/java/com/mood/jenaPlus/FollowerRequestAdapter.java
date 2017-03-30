package com.mood.jenaPlus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The FollowerRequestAdapter class created a listView for FollowerRequestActivity,
 * this listView containts acceptButton and declineButton.
 */

public class FollowerRequestAdapter extends ArrayAdapter<Participant> {

    Context context;
    protected ImageButton acceptButton;
    protected ImageButton declineButton;


    public FollowerRequestAdapter(Context context, ArrayList<Participant> participantList){
        super(context,0,participantList);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        final Participant participant = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.follower_request_listview,parent,false);

        TextView nameText = (TextView) view.findViewById(R.id.username);

        nameText.setText(participant.getUserName());

        acceptButton = (ImageButton) view.findViewById(R.id.accept);
        declineButton = (ImageButton) view.findViewById(R.id.decline);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            MainMPController mpController = MoodPlusApplication.getMainMPController();
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ACCEPTED " + participant.getUserName(),Toast.LENGTH_SHORT).show();
                mpController.acceptRequest(participant.getUserName());

                notifyDataSetChanged();
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            MainMPController mpController = MoodPlusApplication.getMainMPController();

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "DECLINED " + participant.getUserName(),Toast.LENGTH_SHORT).show();
                mpController.rejectRequest(participant.getUserName());

                notifyDataSetChanged();
            }
        });


        return view;
    }
}
