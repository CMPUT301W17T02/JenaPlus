package com.mood.jenaPlus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;

/**
 * The Map activity is used for viewing the participant current location,
 * view mood events that have location and view other participant mood event
 * with location that is 5 km near me.
 *
 * @author Carrol
 * @version 1.0
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    /**
     * The M google api client.
     */
    private GoogleApiClient mGoogleApiClient;
    /**
     * The map location request.
     */
    private LocationRequest mLocationRequest;

    private Marker followingMarker;
    ArrayList<Mood> moodListLocation;
    LatLng allLatLng;
    Marker m ; //reference to the marker

    /**
     * Obtain the SupportMapFragment and get notified when the map is ready to be used.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Place markers of participant mood list that contain location
     * @param map
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            UiSettings mUiSettings = mMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(true);
        }
        moodListLocation = (ArrayList<Mood>) getIntent().getSerializableExtra("participant_moodProvider");

        for (Mood mood: moodListLocation){

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

        buildGoogleApiClient();


    }

    /**
     * Resizing images from drawable
     * Taken from: http://stackoverflow.com/questions/35718103/how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
     * @param recId
     * @param width
     * @param height
     * @return
     */
    public Bitmap resizeMapIcons(Integer recId,int width, int height){
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(recId);
        Bitmap imageBitmap = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(imageBitmap, width, height, false);

        return smallMarker;
    }

    /**
     * Build google api client.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * Getting the Current Location and placing a marker on the map
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,  this);
        }

        Intent i = getIntent();
        LatLng currentPosition = i.getParcelableExtra("longLat_dataProvider");

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15.0f));
        mMap.addMarker(new MarkerOptions().position(currentPosition)
                .title("Current Position"));

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

    }

    /**
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /**
     * The constant MY_PERMISSIONS_REQUEST_LOCATION.
     */
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    /**
     * Ask user for permission to allow access of the map
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}





