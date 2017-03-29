package com.mood.jenaPlus;

/**
 * Created by Cecilia and Julienne on 2017-03-28.
 */

public class OfflineDataController {

    //singleton
    SaveOffline saveOffline  = new SaveOffline();

    public void passFile(String filename){

        saveOffline.loadFromFile(filename);

    }


    public void executeOfflineAdd1(){

        saveOffline.syncOfflineAdd1();

    }

    public void executeOfflineAdd2(){

        saveOffline.syncOfflineAdd2();

    }




}
