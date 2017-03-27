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
 * Created by carrotji on 2017-03-26.
 */

public class FollowerRequestAdapter extends ArrayAdapter<Participant> {

    Context context;
    protected ImageButton acceptButton;
    protected ImageButton declineButton;
    Participant participant;


    public FollowerRequestAdapter(Context context, ArrayList<Participant> participantList){
        super(context,0,participantList);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        participant = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.follower_request_listview,parent,false);

        TextView nameText = (TextView) view.findViewById(R.id.username);
        //TextView moodIconText = (TextView) view.findViewById(R.id.moodIconString);

        nameText.setText(participant.getUserName());

        acceptButton = (ImageButton) view.findViewById(R.id.accept);
        declineButton = (ImageButton) view.findViewById(R.id.decline);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            MainMPController mpController = MoodPlusApplication.getMainMPController();
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ACCEPT",Toast.LENGTH_SHORT).show();
                mpController.acceptRequest(participant.getUserName());
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            MainMPController mpController = MoodPlusApplication.getMainMPController();
            @Override
            public void onClick(View v) {
                mpController.rejectRequest(participant.getUserName());
            }
        });


        return view;
    }
}
