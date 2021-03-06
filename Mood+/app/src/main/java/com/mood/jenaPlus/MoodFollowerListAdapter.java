package com.mood.jenaPlus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This is for creating mood follower list, displaying emotional states,
 * Date, and trigger to the all the FilterActivity.
 * Created by Carlo on 2017-03-25.
 */

public class MoodFollowerListAdapter extends ArrayAdapter<Mood> {

    Context context;

    public MoodFollowerListAdapter(Context context, ArrayList<Mood> moodList){
        super(context,0,moodList);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){


        Mood moodList = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.mood_plus_followerlistview,parent,false);

        TextView dateText = (TextView) view.findViewById(R.id.date);
        TextView messageText = (TextView) view.findViewById(R.id.message);
        ImageView moodIconImage = (ImageView) view.findViewById(R.id.moodIcon);
        TextView nameText = (TextView) view.findViewById(R.id.followerName);
        //TextView moodIconText = (TextView) view.findViewById(R.id.moodIconString);

        String aId = moodList.getId();
        String color = moodList.getColor();
        String userName = moodList.getUserName();

        view.setBackgroundColor(Color.parseColor(color));


        int recId = context.getResources().getIdentifier(aId, "drawable", context.getPackageName());

        dateText.setText(moodList.getDateString());
        messageText.setText(moodList.getText());
        nameText.setText(userName);
        moodIconImage.setImageResource(recId);

        return view;
    }


}
