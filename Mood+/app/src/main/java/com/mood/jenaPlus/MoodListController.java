package com.mood.jenaPlus;


/**
 * Created by Bernice on 2017-02-25.
 */

public class MoodListController {
    private AddMoodActivity theView;
    private Mood theModel;

    public MoodListController(AddMoodActivity view, Mood model) {
        this.theView = view;
        this.theModel = model;

        theModel.setId(theView.getID());

    }





}
