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
 * Created by carrotji on 2017-03-12.
 */
public class MoodListAdapter extends ArrayAdapter<Mood> {

    /**
     * The Context.
     */
    Context context;

    /**
     * Instantiates a new Mood list adapter.
     *
     * @param context  the context
     * @param moodList the mood list
     */
    public MoodListAdapter(Context context, ArrayList<Mood> moodList){
        super(context,0,moodList);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){


        Mood moodList = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.mood_plus_listview,parent,false);

        TextView dateText = (TextView) view.findViewById(R.id.date);
        TextView messageText = (TextView) view.findViewById(R.id.message);
        ImageView moodIconImage = (ImageView) view.findViewById(R.id.moodIcon);
        
        String aId = moodList.getId();
        String color = moodList.getColor();

       view.setBackgroundColor(Color.parseColor(color));


        int recId = context.getResources().getIdentifier(aId, "drawable", context.getPackageName());

        dateText.setText(moodList.getDateString());
        messageText.setText(moodList.getText());
        moodIconImage.setImageResource(recId);

        return view;
    }


}
