package com.mood.jenaPlus.connectivity.display;

import android.app.Activity;
import de.keyboardsurfer.android.widget.crouton.Style;
import com.mood.jenaPlus.R;


/**
 * This is the crouton style class of the connectivity display. From this activity
 * The style of a crouton includes text, color and duration.
 * Ideally, for onConnect a crouton will show in a holoBlueLight color, with text "Network connected Offline Synced"
 * and stays for 2 seconds;
 * for onDisconnect a crouton will show in a holoRedLight color, with text "Network disconnected" and stays until
 * the device get internet back.
 *
 * This class is originally implemented by Novoda merlin: https://github.com/novoda/merlin
 * and some small changes has been made to meet our own needs.
 *
 * @author Novoda Merlin
 */
public enum CroutonStyles {

    /**
     * The Connected.
     */
    CONNECTED(R.string.network_connected) {
        @Override
        Style getStyle(Activity activity) {
            return createConnectedStyle(activity);
        }
    },
    /**
     * The Disconnected.
     */
    DISCONNECTED(R.string.network_disconnected) {
        @Override
        Style getStyle(Activity activity) {
            return createDisconnectedStyle(activity);
        }
    };

    private static final int CONNECTED_CROUTON_DURATION = 2000;

    private final int stringResId;

    CroutonStyles(int stringResId) {
        this.stringResId = stringResId;
    }

    /**
     * Gets string res id.
     *
     * @return the string res id
     */
    public int getStringResId() {
        return stringResId;
    }

    /**
     * Gets style.
     *
     * @param activity the activity
     * @return the style
     */
    abstract Style getStyle(Activity activity);

    private static Style createConnectedStyle(Activity activity) {
        return new Style.Builder()
                .setDuration(CONNECTED_CROUTON_DURATION)
                .setBackgroundColorValue(Style.holoBlueLight)
                .build();
    }

    private static Style createDisconnectedStyle(Activity activity) {
        return new Style.Builder()
                .setDuration(Style.DURATION_INFINITE)
                .setBackgroundColorValue(Style.holoRedLight)
                .build();
    }

}
