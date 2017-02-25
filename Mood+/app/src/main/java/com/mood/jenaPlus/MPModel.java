package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by carrotji on 2017-02-25.
 */

public abstract class MPModel<V extends MPView> {
    private ArrayList<V> views;

    protected MPModel(){
        views = new ArrayList<V>();
    }

    public void addView(V view){
        if(! views.contains(view)){
            views.add(view);
        }
    }

    public void deleteView(V view){
        views.remove(view);
    }

    public void notifyViews(){
        for (V view : views){
            view.update(this);
        }
    }
}
