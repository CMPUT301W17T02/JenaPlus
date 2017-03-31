package com.mood.jenaPlus.connectivity.display;

/**
 * Created by ceciliaxiang on 2017-03-26.
 */

public interface NetworkStatusDisplayer {
    void displayConnected();

    void displayDisconnected();

    void reset();
}
