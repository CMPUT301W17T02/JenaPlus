package com.mood.jenaPlus;

/**
 * Created by Bernice on 2017-02-25.
 */

public class MoodListController {
    private AddMoodActivity theView;
    private Mood theModel;

    public MoodListController(AddMoodActivity theView, Mood theModel) {
        this.theView = theView;
        this.theModel = theModel;
    }

}
