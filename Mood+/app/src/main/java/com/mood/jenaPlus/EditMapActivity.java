package com.mood.jenaPlus;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EditMapActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    Marker newMarker;
    double newLat;
    double newLng;
    LatLng position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }



    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        final GoogleMap mMap = map;
        Marker followingMarker;

        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(newMarker!=null){ //if marker exists (not null or whatever)
                    newMarker.setPosition(latLng);
                }
                else{
                    newMarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Your Destination")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                }
                newLng = latLng.longitude;
                newLat = latLng.latitude;

                Toast.makeText(EditMapActivity.this, latLng.latitude+" "+latLng.longitude, Toast.LENGTH_SHORT).show();

            }
        });

        Button button = (Button) findViewById(R.id.set_location);
        //button.setText("Set New Location");
        //addContentView(button, new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                position = new LatLng(newLat,newLng);
                Log.i("SET LOCATION",""+newLat+" "+newLng);
                Bundle args = new Bundle();
                args.putParcelable("new_position", position);
                Intent intent=new Intent(EditMapActivity.this,EditMoodActivity.class);
                intent.putExtras(args);
                setResult(EditMoodActivity.RESULT_OK,intent);
                finish();

                ;

//                startActivity(intent);

            }
        });

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }
}
