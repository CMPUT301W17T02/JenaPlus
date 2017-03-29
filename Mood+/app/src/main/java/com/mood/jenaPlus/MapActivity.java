package com.mood.jenaPlus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
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
 * The type Map activity.
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
     * The M last location.
     */
    private Location mLastLocation;
    /**
     * The current location marker.
     */
    private Marker mCurrLocationMarker;
    /**
     * The map location request.
     */
    private LocationRequest mLocationRequest;

    ArrayList<LatLng> arrayLocation;
    private Marker followingMarker;
    ArrayList<Mood> moodList;
    LatLng allLatLng;
    ArrayList<LatLng> allLocations = new ArrayList<>();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     *
     * @param map
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;


        Intent intent = getIntent();
        //arrayLocation = intent.getParcelableArrayListExtra("following_latLongProvider");

        moodList = (ArrayList<Mood>) getIntent().getSerializableExtra("participant_moodProvider");
        Log.i("MOOOOOOOOODS","ALL MOODS WITH LOCATION: " + moodList);

        for (Mood mood: moodList){
            Log.i("LOCATION!!!!!!","Contents of arrayLocation: " + moodList + mood.getLatitude()+mood.getLongitude() );
            allLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());
            allLocations.add(allLatLng);
        }

        // Add some markers to the map, and add a data object to each marker.
        for(LatLng location: allLocations) {
            followingMarker = mMap.addMarker(new MarkerOptions().position(location)
                    .title("MOODS").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            followingMarker.setTag(0);
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            UiSettings mUiSettings = mMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(true);
        }

        buildGoogleApiClient();


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
     *
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 11.0f));
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
     * Check location permission boolean.
     *
     * @return the boolean
     */
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     *
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





