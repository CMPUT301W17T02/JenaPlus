package com.mood.jenaPlus;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * The Marker Activity is for viewing map of mood events,
 * it is used when filtering mood events of the user or
 * the following participants by location.
 * @author Carrol
 */

public class MarkerActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    ArrayList<Mood> moodListLocation;
    LatLng allLatLng;
    ArrayList<Mood> userMoodLocation;

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
        final GoogleMap mMap = map;
        Marker followingMarker;

        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);


        // Get User Mood List with location
        if( getIntent().hasExtra("user_moodProvider")) {
            userMoodLocation = (ArrayList<Mood>) getIntent().getSerializableExtra("user_moodProvider");

            for (Mood mood: userMoodLocation){
                allLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());

                // Creating markers with mood icons
                int recId = getResources().getIdentifier(mood.getId(), "drawable", getApplicationContext().getPackageName());

                // Placing Marker on the map with emotional states
                followingMarker = mMap.addMarker(new MarkerOptions().position(allLatLng)
                        .title("Feeling "+ mood.getId())
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(recId,150,150))));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(allLatLng, 3.0f));
                followingMarker.setTag(0);
            }
        }
        // Get other participants mood list with location
        else if(getIntent().hasExtra("participant_moodProvider")){

            moodListLocation = (ArrayList<Mood>) getIntent().getSerializableExtra("participant_moodProvider");

            for (Mood mood: moodListLocation){

                allLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());

                // Creating markers with mood icons
                int recId = getResources().getIdentifier(mood.getId(), "drawable", getApplicationContext().getPackageName());

                // Placing Marker on the map with user name and emotional states
                followingMarker = mMap.addMarker(new MarkerOptions().position(allLatLng)
                        .title(mood.getUserName())
                        .snippet("Feeling "+ mood.getId())
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(recId,150,150))));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(allLatLng, 3.0f));
                followingMarker.setTag(0);
            }

        }

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /**
     * This method resize images by getting the image id
     * from drawable and specified certain width and height
     *
     * Taken from: http://stackoverflow.com/questions/35718103/how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
     * 2017-03-29
     * @param recId
     * @param width
     * @param height
     * @return
     */
    public Bitmap resizeMapIcons(Integer recId, int width, int height){
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(recId);
        Bitmap imageBitmap = bitmapdraw.getBitmap();

        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

}
