package com.mood.jenaPlus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


/**
 * This is for creating mood icons into a gridview
 * Taken from https://developer.android.com/guide/topics/ui/layout/gridview.html
 * Created by carrotji on 2017-03-06.
 */

public class MoodIconAdapter extends BaseAdapter {

    private Context mContext;

    public MoodIconAdapter(Context c){
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2,2,2,2);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    public Integer[] mThumbIds = {
            R.drawable.surprised,R.drawable.disgust,R.drawable.fear,
            R.drawable.confused, R.drawable.happy, R.drawable.angry,
            R.drawable.sad,  R.drawable.shame,R.drawable.annoyed,
    };

}
