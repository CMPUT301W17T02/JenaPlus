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

        theModel.setId(theModel.getMoodId(theView.getID())); // Setting the mood ID
        theModel.setColor(theModel.getMoodColor(theView.getColorNum())); // Setting the Color
        theModel.setSocial(theView.getSocialSituation()); // Setting the social situation
        theModel.setText(theView.getMessage().toString()); //Setting the message


    }





}
