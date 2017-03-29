package com.mood.jenaPlus;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MarkerActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private Marker testMarker;

    private GoogleMap mMap;

    ArrayList<Marker> markers;
    ArrayList<LatLng> arrayLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        Intent intent = getIntent();
        arrayLocation = intent.getParcelableArrayListExtra("longLat_dataProvider");

        // Add some markers to the map, and add a data object to each marker.
        for(LatLng location: arrayLocation) {
            Log.i("LOCATIONNNNNN","Contents of arrayLocation: " + location);
            testMarker = mMap.addMarker(new MarkerOptions().position(location).title("TESTING"));
            testMarker.setTag(0);
        }

        // Add some markers to the map, and add a data object to each marker.
     /*   mPerth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth"));
        mPerth.setTag(0);*/

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

}
