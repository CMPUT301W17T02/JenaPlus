package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * MP Model
 *
 * @param <V> the type parameter
 */
public abstract class MPModel<V extends MPView> {
    private ArrayList<V> views;

    /**
     * Instantiates a new Mp model.
     */
    protected MPModel(){
        views = new ArrayList<V>();
    }

    /**
     * Add view.
     *
     * @param view the view
     */
    public void addView(V view){
        if(! views.contains(view)){
            views.add(view);
        }
    }

    /**
     * Delete view.
     *
     * @param view the view
     */
    public void deleteView(V view){
        views.remove(view);
    }

    /**
     * Notify views.
     */
    public void notifyViews(){
        for (V view : views){
            view.update(this);
        }
    }
}