package com.mood.jenaPlus;

import android.app.Activity;

/**
 * Created by ceciliaxiang on 2017-03-26.
 */


public class NetworkStatusCroutonDisplayer implements NetworkStatusDisplayer {

    private final NovodaCrouton NovodaCrouton;

    public NetworkStatusCroutonDisplayer(Activity activity) {
        this.NovodaCrouton = new NovodaCrouton(activity);
    }

    @Override
    public void displayConnected() {
        NovodaCrouton.show(CroutonStyles.CONNECTED);
    }

    @Override
    public void displayDisconnected() {
        if (!NovodaCrouton.isShown()) {
            NovodaCrouton.show(CroutonStyles.DISCONNECTED);
        }
    }

    @Override
    public void reset() {
        NovodaCrouton.close();
    }

}