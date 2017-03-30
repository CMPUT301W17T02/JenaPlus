package com.mood.jenaPlus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MarkerActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private Marker testMarker;

    private GoogleMap mMap;

    private Marker followingMarker;
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
        mMap = map;

        //moodListLocation = (ArrayList<Mood>) getIntent().getSerializableExtra("participant_moodProvider");
        //userMoodLocation = (ArrayList<Mood>) getIntent().getSerializableExtra("user_moodProvider");

        if( getIntent().hasExtra("user_moodProvider")) {
            userMoodLocation = (ArrayList<Mood>) getIntent().getSerializableExtra("user_moodProvider");
            Log.i("TAGGGGGG","PASSING USER MOOD LOCATION" + userMoodLocation);
            Log.i("SECOND TAG","EMPTY"+moodListLocation);

            for (Mood mood: userMoodLocation){
                allLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());

                // Creating markers
                int recId = getResources().getIdentifier(mood.getId(), "drawable", getApplicationContext().getPackageName());

                followingMarker = mMap.addMarker(new MarkerOptions().position(allLatLng)
                        .title("Feeling "+ mood.getId())
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(recId,150,150))));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(allLatLng, 2.0f));
                followingMarker.setTag(0);
            }
        }
        else if(getIntent().hasExtra("participant_moodProvider")){
            moodListLocation = (ArrayList<Mood>) getIntent().getSerializableExtra("participant_moodProvider");
            Log.i("TAGGGGGG","PASSING Participant moods LOCATION" + moodListLocation);
            Log.i("SECOND TAG","EMPTY"+userMoodLocation);

            for (Mood mood: moodListLocation){
                //Log.i("LATLNG!!!!!!","Contents of arrayLocation: " + mood.getLatitude()+mood.getLongitude() );
                Log.i("MOODS ID!!!!!!",mood.getId() + mood.getUserName());
                allLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());

                // Creating markers
                int recId = getResources().getIdentifier(mood.getId(), "drawable", getApplicationContext().getPackageName());

                followingMarker = mMap.addMarker(new MarkerOptions().position(allLatLng)
                        .title(mood.getUserName())
                        .snippet("Feeling "+ mood.getId())
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(recId,150,150))));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(allLatLng, 2.0f));
                followingMarker.setTag(0);
            }

        }


        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    public Bitmap resizeMapIcons(Integer recId, int width, int height){
        // Taken from: http://stackoverflow.com/questions/35718103/how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(recId);
        Bitmap imageBitmap = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(imageBitmap, width, height, false);

        return smallMarker;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

}
