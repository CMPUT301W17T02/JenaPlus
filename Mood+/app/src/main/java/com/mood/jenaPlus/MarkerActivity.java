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

        //Intent intent = getIntent();
        //arrayLocation = intent.getParcelableArrayListExtra("longLat_dataProvider");

        moodListLocation = (ArrayList<Mood>) getIntent().getSerializableExtra("participant_moodProvider");

        for (Mood mood: moodListLocation){
            //Log.i("LATLNG!!!!!!","Contents of arrayLocation: " + mood.getLatitude()+mood.getLongitude() );
            //Log.i("MOODS ID!!!!!!",mood.getId() + mood.getUserName());
            allLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());

            // Creating markers
            int recId = getResources().getIdentifier(mood.getId(), "drawable", getApplicationContext().getPackageName());

            followingMarker = mMap.addMarker(new MarkerOptions().position(allLatLng)
                    .title(mood.getUserName())
                    .snippet("Feeling "+ mood.getId())
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(recId,150,150))));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(allLatLng, 15.0f));
            followingMarker.setTag(0);

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
