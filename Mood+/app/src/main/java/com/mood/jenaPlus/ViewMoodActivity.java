package com.mood.jenaPlus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * This is for viewing mood events. It will show all attributes of a mood from a participant,
 * except their user name. If a location is attached to a modd then a navigation marker will
 * appear to view on a map.
 *
 * @author Carlo
 * @author Carrol
 * @version 1.0
 */
public class ViewMoodActivity extends AppCompatActivity implements MPView<MoodPlus> {

    /**
     * The A id.
     */
    protected String aId;
    /**
     * The A text.
     */
    protected String aText;
    /**
     * The A date.
     */
    protected String aDate;
    /**
     * The Add location.
     */
    protected Boolean addLocation;
    /**
     * The A Location.
     */
    protected String aLocation;
    /**
     * The A Latitude.
     */
    protected Double aLatitude;
    /**
     * The A Longitude.
     */
    protected Double aLongitude;
    /**
     * The A social.
     */
    protected String aSocial;
    /**
     * The A photo.
     */
    protected String aPhoto;
    /**
     * The A color.
     */
    protected String aColor;
    /**
     * The Icon.
     */
    protected ImageView icon;
    /**
     * The Situation.
     */
    protected TextView situation;
    /**
     * The Date.
     */
    protected TextView date;
    /**
     * The Message.
     */
    protected TextView message;

    /**
     * The Location.
     */
    protected ImageButton locationButton;

    protected ImageView cameraImage;
    ArrayList<LatLng> tempLocation = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_interface);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        MoodPlus mp = MoodPlusApplication.getMoodPlus();
        mp.addView(this);

        Mood mood = (Mood)getIntent().getSerializableExtra("aMood");
        aId = mood.getId();
        aText = mood.getText();
        aDate = mood.getDate().toString();
        addLocation = mood.getAddLocation();
        aLatitude = mood.getLatitude();
        aLongitude = mood.getLongitude();
        aSocial = mood.getSocial();
        aPhoto = mood.getPhoto();
        aColor = mood.getColor();

        icon = (ImageView) findViewById(R.id.cur_mood);

        /**
         * This gets the resource id
         */
        //Taken from http://stackoverflow.com/questions/3276968/drawable-getresources-getidentifier-problem
        // 13 March 2017 12:01
        int recId = getResources().getIdentifier(aId, "drawable", getApplicationContext().getPackageName());
        icon.setImageResource(recId);

        situation = (TextView) findViewById(R.id.situation);
        situation.setText(aSocial);


        date = (TextView) findViewById(R.id.time);
        date.setText(aDate);

        message = (TextView) findViewById(R.id.message);
        message.setText(aText);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor(aColor));

        cameraImage = (ImageView) findViewById(R.id.camera_image);
        Bitmap photo = StringToBitMap(aPhoto);
        cameraImage.setImageBitmap(photo);

        locationButton = (ImageButton) findViewById(R.id.test_location);

        if(addLocation){
            locationButton.setVisibility(View.VISIBLE); //To set visible
        }
        else{
            System.out.println("DID NOT CLICK LOCATION");

        }

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addLocation){
                    //Taken from http://stackoverflow.com/questions/30106507/pass-longitude-and-latitude-with-intent-to-another-class
                    //2017-03-21
                    LatLng position = new LatLng(aLatitude,aLongitude);

                    Bundle args = new Bundle();
                    args.putParcelable("longLat_dataProvider",position);
                    Intent intent = new Intent(ViewMoodActivity.this, MapActivity.class);
                    intent.putExtras(args);
                    intent.putExtra("participant_moodProvider",tempLocation);
                    startActivity(intent);
                }
                else{
                    Log.i("Location","LOCATION NOT ADDED");
                }

            }
        });


    }

    public static Bitmap StringToBitMap(String encodedString){
        //taken from: http://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
        //2017-03-26
        try {
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Gets id.
     *
     * @param resourceName the resource name
     * @param c            the c
     * @return the id
     */
    // Taken from http://stackoverflow.com/questions/4427608/android-getting-resource-id-from-string
    // 12 Mar 2017 12:42
    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    public void update(MoodPlus moodPlus){
        // TODO implements update method
    }

}
