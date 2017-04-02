package com.mood.jenaPlus;

import android.content.Context;

/**
 * Created by Cecilia and Julienne on 2017-03-28.
 */

public class OfflineDataController {

    //singleton
    MoodPlus moodPlus = null;


    public OfflineDataController(MoodPlus aMoodPlus){
        this.moodPlus = aMoodPlus;

    }

    public Participant getOfflineParticipant(){
        return moodPlus.getParticipant();
    }

    public void SyncOfflineList(Context context){
        String participantID = getOfflineParticipant().getId();

        UserMoodList offlineMoodList = SaveOffline.loadOfflineList(participantID, context);

        getOfflineParticipant().setUserMoodList(offlineMoodList);

        moodPlus.updateParticipant();

    }

    public UserMoodList loadSavedList(Context context) {
        return SaveOffline.loadOfflineList(getOfflineParticipant().getId(), context);
    }

    public void saveOfflineList(UserMoodList moodList, Context context) {
        SaveOffline.saveOfflineList(moodList, getOfflineParticipant().getId(), context);
    }


}
