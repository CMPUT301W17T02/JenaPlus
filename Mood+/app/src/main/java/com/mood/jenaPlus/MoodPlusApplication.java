package com.mood.jenaPlus;

import android.app.Application;

/**
 *
 * @author Carrol
 * @author Carlo
 */
public class MoodPlusApplication extends Application {
    // Singleton
    transient private static MoodPlus moodPlus = null;

    /**
     * Get mood plus mood plus.
     *
     * @return the mood plus
     */
    static MoodPlus getMoodPlus(){
        if (moodPlus == null){
            moodPlus = new MoodPlus();
        }
        return moodPlus;
    }

    // Singleton
    transient private static ElasticsearchMPController elasticsearchMPController = null;

    /**
     * Get elasticsearch mp controller elasticsearch mp controller.
     *
     * @return the elasticsearch mp controller
     */
    public static ElasticsearchMPController getElasticsearchMPController(){
        if(elasticsearchMPController == null){
            elasticsearchMPController = new ElasticsearchMPController(getMoodPlus());

        }
        return elasticsearchMPController;
    }

    // Singleton
    transient private static MoodListController moodListController = null;

    /**
     * Gets mood list controller.
     *
     * @return the mood list controller
     */
    static MoodListController getMoodListController() {
        if (moodListController == null) {
            moodListController = new MoodListController(getMoodPlus());
        }
        return moodListController;
    }

    // Singleton
    transient private static MainMPController mainMPController = null;

    /**
     * Gets main mp controller.
     *
     * @return the main mp controller
     */
    static MainMPController getMainMPController() {
        if (mainMPController == null) {
            mainMPController = new MainMPController(getMoodPlus());
        }
        return mainMPController;
    }
}
