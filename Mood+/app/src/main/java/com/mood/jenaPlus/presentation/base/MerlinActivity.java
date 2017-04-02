package com.mood.jenaPlus.presentation.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.registerable.bind.Bindable;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;

/**
 * This is the Merlin activity class of the connectivity display.
 * Merlin will register connectable and disconnectable and bindable
 * and a merlin will be bind onStart and unbind onStop.
 *
 * This class is originally implemented by Novoda merlin: https://github.com/novoda/merlin
 * and some small changes has been made to meet our own needs.
 *
 * @author Novoda Merlin
 */


public abstract class MerlinActivity extends AppCompatActivity {

    protected Merlin merlin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        merlin = createMerlin();
    }

    protected abstract Merlin createMerlin();

    protected void registerConnectable(Connectable connectable) {
        merlin.registerConnectable(connectable);
    }

    protected void registerDisconnectable(Disconnectable disconnectable) {
        merlin.registerDisconnectable(disconnectable);
    }

    protected void registerBindable(Bindable bindable) {
        merlin.registerBindable(bindable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        merlin.bind();
    }

    @Override
    protected void onStop() {
        super.onStop();
        merlin.unbind();
    }

}