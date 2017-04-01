package com.mood.jenaPlus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Monitors changes in network connectivity. Uses Broadcast Receiver.
 * Checks for when reconnected to Wi-Fi network.
 *
 * Created by Bernice on 2017-03-27.
 */

public class NetworkMonitorReceiver extends BroadcastReceiver {

	MoodPlus moodPlus = null;

	// Taken from http://stackoverflow.com/questions/15698790/broadcast-receiver-for-checking-internet-connection-in-android-app
	// 03-27-2017 21:45
	@Override
	public void onReceive(final Context context, final Intent intent) {

		final ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		// Checks if there is network connectivity.
		if (wifi.isConnected() || mobile.isConnected()) {
			//Toast.makeText(context, "Network Available; Syncing", Toast.LENGTH_SHORT).show();

			OfflineDataController offlineController = MoodPlusApplication.getOfflineDataController();

			UserMoodList tempList = offlineController.loadSavedList(context);

			if(tempList != null) {
				offlineController.SyncOfflineList(context);
				Toast.makeText(context, "Syncing changes from LAST SESSION", Toast.LENGTH_SHORT).show();
				Log.d("Network Available", "Syncing");
			}

			/*
			offlineController.SyncOffline();
			Toast.makeText(context, "Syncing THIS SESSION", Toast.LENGTH_SHORT);
			Log.d("THIS SESSION", "Syncing");
			*/

			//Log.d("TRIGGERED", "Network Available; SYNC");
		}
		else {
			Toast.makeText(context, "Network Unavailable", Toast.LENGTH_SHORT).show();
		}

	}

}
