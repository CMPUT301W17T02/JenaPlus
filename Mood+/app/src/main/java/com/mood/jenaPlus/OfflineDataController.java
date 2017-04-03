package com.mood.jenaPlus;

import android.content.Context;

/**
 * This class is used as a controller that handles offline behavior. It communicates between the
 * various View classes where data inputted by the user needs to be saved locally and synced to the
 * server once connection is re-established and the Model classes where those functionalities
 * are implemented.
 *
 * Created by Cecilia and Julienne on 2017-03-28.
 *
 * @user Julienne
 * @user Cecilia
 * @version 1.0
 */

public class OfflineDataController {

    //singleton
    MoodPlus moodPlus = null;

	/**
     * Instantiates a new Offline Data controller.
     *
     * @param aMoodPlus an occurrence of the app
     */
    public OfflineDataController(MoodPlus aMoodPlus){
        this.moodPlus = aMoodPlus;

    }

	/**
     * Retrieves a Participant to save moods locally to.
     *
     * @return Participant who is currently using the app
     */
    public Participant getOfflineParticipant(){
        return moodPlus.getParticipant();
    }

	/**
     * Synchronizes changes made locally to the server. It loads the list saved locally, uses it to
     * update the user in the elastic search server.
     *
     * @param context the context passed in to know where the user currently is in the app
     */
    public void SyncOfflineList(Context context){
        String participantID = getOfflineParticipant().getId();

        UserMoodList offlineMoodList = SaveOffline.loadOfflineList(participantID, context);

        getOfflineParticipant().setUserMoodList(offlineMoodList);

        moodPlus.updateParticipant();  // synchronizes changes with the elastic search server

    }

	/**
     * Loads the user's saved moodlist. It uses the participant's unique ID to obtain the saved list.
     *
     * @param context the context passed to know where the user currently is in the app
     * @return the UserMoodList that is the user's moodlist that was saved locally
     */
    public UserMoodList loadSavedList(Context context) {
        return SaveOffline.loadOfflineList(getOfflineParticipant().getId(), context);
    }

	/**
	 * Saves the user's moodlist locally. It uses the participant's unique ID to save the list.
     *
     * @param moodList the UserMoodList of moods to be saved locally
     * @param context the context passed to know where the user currently is in the app
     */
    public void saveOfflineList(UserMoodList moodList, Context context) {
        SaveOffline.saveOfflineList(moodList, getOfflineParticipant().getId(), context);
    }


}
