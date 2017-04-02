package com.mood.jenaPlus.connectivity.display;

/**
 * This is the NetworkStatus Displayer controller class of the connectivity display.
 * This Displayer will display current network status by displayConnected,
 * displayDisconnected, and reset.
 *
 * This class is originally implemented by Novoda merlin: https://github.com/novoda/merlin
 * and some small changes has been made to meet our own needs.
 *
 * @author Novoda Merlin
 */

public interface NetworkStatusDisplayer {
    void displayConnected();

    void displayDisconnected();

    void reset();
}
