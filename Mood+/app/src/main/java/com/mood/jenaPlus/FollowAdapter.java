package com.mood.jenaPlus;

import android.content.Context;
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
 * Created by carrotji on 2017-03-27.
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
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"FOLLOWING " + participant.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
