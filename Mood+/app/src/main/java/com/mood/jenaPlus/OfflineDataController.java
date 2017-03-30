package com.mood.jenaPlus;

/**
 * Created by Cecilia and Julienne on 2017-03-28.
 */

public class OfflineDataController {

    //singleton
    MoodPlus moodPlus = null;
    SaveOffline saveOffline  = new SaveOffline();


    public OfflineDataController(MoodPlus aMoodPlus){
        this.moodPlus = aMoodPlus;

    }

    public Participant getOfflineParticipant(){
        return moodPlus.getParticipant();
    }

    public void SyncOffline(){
        moodPlus.updateParticipant();
    }


}
