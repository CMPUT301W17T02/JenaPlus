package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by carrotji on 2017-02-25.
 */

<<<<<<< Updated upstream
<<<<<<< Updated upstream
public abstract class MPModel<V extends MPView> {
    private ArrayList<V> views;

    protected MPModel(){
=======
=======
>>>>>>> Stashed changes
public class MPModel<V extends MPView> {
    private ArrayList<V> views;

    public MPModel(){
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        views = new ArrayList<V>();
    }

    public void addView(V view){
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        if(! views.contains(view)){
=======
        if(!views.contains(view)){
>>>>>>> Stashed changes
=======
        if(!views.contains(view)){
>>>>>>> Stashed changes
            views.add(view);
        }
    }

    public void deleteView(V view){
        views.remove(view);
    }

    public void notifyViews(){
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        for (V view : views){
=======
        for(V view : views){
>>>>>>> Stashed changes
=======
        for(V view : views){
>>>>>>> Stashed changes
            view.update(this);
        }
    }
}
