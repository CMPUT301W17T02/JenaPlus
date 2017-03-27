package com.mood.jenaPlus;

import android.app.Activity;

import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * Created by ceciliaxiang on 2017-03-26.
 */


public class NetworkStatusCroutonDisplayer implements NetworkStatusDisplayer {

    private final JenaCrouton JenaCrouton;

    public NetworkStatusCroutonDisplayer(Activity activity) {
        this.JenaCrouton = new JenaCrouton(activity);
    }

    @Override
    public void displayConnected() {
        JenaCrouton.show(CroutonStyles.CONNECTED);
    }

    @Override
    public void displayDisconnected() {
        if (!JenaCrouton.isShown()) {
            JenaCrouton.show(CroutonStyles.DISCONNECTED);
        }
    }

    @Override
    public void reset() {
        JenaCrouton.close();
    }

}