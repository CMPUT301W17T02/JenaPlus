package com.mood.jenaPlus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by carrotji on 2017-03-08.
 */

public class MoodListAdapter extends ArrayAdapter<MoodList> {

    public MoodListAdapter(Context context, ArrayList<MoodList> moodList){
        super(context,0,moodList);

    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.mood_plus_listview,parent,false);

        TextView date = (TextView) view.findViewById(R.id.date);
        TextView trigger = (TextView) view.findViewById(R.id.trigger);
        ImageView moodIcon = (ImageView) view.findViewById(R.id.mood_icon);

        return view;

    }
}
