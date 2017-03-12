package com.mood.jenaPlus;

import android.app.Application;

/**
 * Created by carrotji on 2017-03-06.
 */

public class MoodPlusApplication extends Application {
    // Singleton
    transient private static MoodPlus moodPlus = null;

    static MoodPlus getMoodPlus(){
        if (moodPlus == null){
            moodPlus = new MoodPlus();
        }
        return moodPlus;
    }

    // Singleton
    transient private static ElasticsearchMPController elasticsearchMPController = null;

    public static ElasticsearchMPController getElasticsearchMPController(){
        if(elasticsearchMPController == null){
            elasticsearchMPController = new ElasticsearchMPController(getMoodPlus());

        }
        return elasticsearchMPController;
    }

    // Singleton
    transient private static MoodListController moodListController = null;
    static MoodListController getMoodListController() {
        if (moodListController == null) {
            moodListController = new MoodListController(getMoodPlus());
        }
        return moodListController;
    }

    // Singleton
    transient private static MainMPController mainMPController = null;
    static MainMPController getMainMPController() {
        if (mainMPController == null) {
            mainMPController = new MainMPController(getMoodPlus());
        }
        return mainMPController;
    }
}
