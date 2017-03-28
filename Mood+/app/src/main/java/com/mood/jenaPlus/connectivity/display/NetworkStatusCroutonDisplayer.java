package com.mood.jenaPlus.connectivity.display;

import android.app.Activity;



/**
 * Created by ceciliaxiang on 2017-03-26.
 */


public class NetworkStatusCroutonDisplayer implements NetworkStatusDisplayer {

    private final NovodaCrouton novodaCrouton;

    public NetworkStatusCroutonDisplayer(Activity activity) {
        this.novodaCrouton = new NovodaCrouton(activity);
    }

    @Override
    public void displayConnected() {
        novodaCrouton.show(CroutonStyles.CONNECTED);
    }

    @Override
    public void displayDisconnected() {
        if (!novodaCrouton.isShown()) {
            novodaCrouton.show(CroutonStyles.DISCONNECTED);
        }
    }

    @Override
    public void reset() {
        novodaCrouton.close();
    }

}