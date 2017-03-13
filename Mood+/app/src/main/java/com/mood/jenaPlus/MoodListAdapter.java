package com.mood.jenaPlus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by carrotji on 2017-03-12.
 */

public class MoodListAdapter extends ArrayAdapter<Mood> {

    public MoodListAdapter(Context context, ArrayList<Mood> moodList){
        super(context,0,moodList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        Mood moodList = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.mood_plus_listview,parent,false);

        TextView dateText = (TextView) view.findViewById(R.id.date);
        TextView messageText = (TextView) view.findViewById(R.id.message);
        ImageView moodIconImage = (ImageView) view.findViewById(R.id.moodIcon);
        TextView moodIconText = (TextView) view.findViewById(R.id.moodIconString);

        //int recId = getResources().getIdentifier(aId, "drawable", getApplicationContext().getPackageName());

        dateText.setText(moodList.getDateString());
        messageText.setText(moodList.getText());
        moodIconText.setText(moodList.getId());


        return view;
    }
}
