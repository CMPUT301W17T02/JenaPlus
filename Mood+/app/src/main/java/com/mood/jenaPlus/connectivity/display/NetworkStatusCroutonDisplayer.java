package com.mood.jenaPlus.connectivity.display;

import android.app.Activity;

/**
 * This is the NetworkStatus Crouton Displayer class of the connectivity display.
 * Ideally, NetworkStatusCroutonDisplayer will construct a new novodaCrouton,
 * and three methods displayConnected and displayDisconnected will display network
 * by crouton accordingly.
 * The displayConnected will show crouton with crouton style "connected";
 * and displayDisconnected will check if a crouton already shows up,
 * and if no then it will show a crouton with crouton style "disconnected";
 * finally a reset method that will remove the crouton from displaying.
 *
 * This class is originally implemented by Novoda merlin: https://github.com/novoda/merlin
 * and some small changes has been made to meet our own needs.
 *
 * @author Novoda Merlin
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