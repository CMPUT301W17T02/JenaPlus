package com.mood.jenaPlus;

/**
 * Created by Cecilia and Julienne on 2017-03-28.
 */

public class OfflineDataController {

    //singleton
    SaveOffline saveOffline  = new SaveOffline();
    MoodPlus moodPlus = null;

    public void passFile(String filename){

        saveOffline.loadFromFile(filename);

    }


    public void passingList(){


    }


    public void executeOfflineAdd1(){

        saveOffline.syncOfflineAdd1();

    }

    public void executeOfflineAdd2(){

        saveOffline.syncOfflineAdd2();

    }

    public OfflineDataController(MoodPlus aMoodPlus){
        this.moodPlus = aMoodPlus;

    }





}