package com.mood.jenaPlus;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStreamWriter;

import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.NetworkStatus;
import com.novoda.merlin.registerable.bind.Bindable;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;
import com.mood.jenaPlus.presentation.base.MerlinActivity;
import com.mood.jenaPlus.connectivity.display.NetworkStatusDisplayer;
import com.mood.jenaPlus.connectivity.display.NetworkStatusCroutonDisplayer;

import static android.R.attr.name;

/**
 * This is the main activity to add a mood.
 *
 * @author Carlo
 * @author Carrol
 *
 */

public class AddMoodActivity extends MerlinActivity implements MPView<MoodPlus>, Connectable, Disconnectable, Bindable {

    private static final String TAG = "ERROR";
    int idNum;
    int colorNum;
    private String socialSituation;
    private String trigger;
    private String idString;
    private String colorString;


    private Button addButton;
    private EditText message;
    private GridView gridview;
    private ImageButton image;

    Context context = this;

    private Boolean addLocation = false;
    private Location location;
    private String imageString = "";
    private Boolean moodChosen = false;

    private Double latitude;
    private Double longitude;

    private String userName;
    private ImageButton infoButton;
    final int maxBytes =  65536;
    private int previousSelectedPosition = -1;

    private NetworkStatusDisplayer networkStatusDisplayer;

    private MerlinsBeard merlinsBeard;

    private static final String ADD = "add.sav";

    private UserMoodList userMoodList;

    private NetworkMonitorReceiver broadcastReceiver = new NetworkMonitorReceiver();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mood_interface);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        //TextView test = (TextView) findViewById(R.id.addtext);
        networkStatusDisplayer = new NetworkStatusCroutonDisplayer(this);
        merlinsBeard = MerlinsBeard.from(this);

        userMoodList = new UserMoodList();

        registerBroadcastReceiver();

        /*-------DEBUGGING TO SEE USERNAME AND ID ------*/

        String name = participant.getUserName();
        userName = name;
        String id = participant.getId();
        //String who = "UserName: "+ name;
        //test.setText(who);

        /*------------------------------------------------*/

        message = (EditText) findViewById(R.id.message);
        addButton = (Button) findViewById(R.id.AddButton);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        image = (ImageButton) findViewById(R.id.selected_image);

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Image")
                        .setMessage("Do you want to delete an image?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                imageString = "";
                                image.setImageBitmap(null);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

        infoButton = (ImageButton) findViewById(R.id.info);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.icon_legend);

                ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new MoodIconAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MoodIcon mi = new MoodIcon();

                idNum = colorNum = position;
                idString = mi.getMood(idNum);
                colorString = mi.getColor(colorNum);
                moodChosen = true;
                Toast.makeText(AddMoodActivity.this, "Feeling " + idString ,Toast.LENGTH_SHORT).show();

                view.setBackgroundColor(Color.parseColor(colorString));

                //Taken from https://android--code.blogspot.ca/2015/08/android-gridview-selected-item-color.html
                ImageView previousSelectedView = (ImageView) gridview.getChildAt(previousSelectedPosition);

                // If there is a previous selected view exists
                if (previousSelectedPosition != -1)
                {
                    // Set the last selected View to deselect
                    previousSelectedView.setSelected(false);

                    // Set the last selected View background color as deselected item
                    previousSelectedView.setBackgroundColor(Color.parseColor("#00ff0000"));
                }

                // Set the current selected view position as previousSelectedPosition
                previousSelectedPosition = position;

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_camera:
                                cameraIntent();

                                break;
                            case R.id.socialPopup:
                                // Taken from http://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
                                // 04-03-2015 01:16
                                View menuItemView = findViewById(R.id.socialPopup);
                                PopupMenu popup = new PopupMenu(AddMoodActivity.this, menuItemView);
                                //Inflating the Popup using xml file
                                popup.getMenuInflater()
                                        .inflate(R.menu.social_popup, popup.getMenu());

                                //registering popup with OnMenuItemClickListener
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {
                                        Toast.makeText(
                                                AddMoodActivity.this,
                                                "Social Situation : " + item.getTitle(),
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        socialSituation = (String) item.getTitle();
                                        return true;
                                    }
                                });

                                popup.show(); //showing popup menu
                                break;

                            case R.id.action_navigation:
                                getLocation();
                                addLocation = true;
                                break;
                        }
                        return true;
                    }
                }
        );

        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                addMood();
            }
        });
    }

    public void registerBroadcastReceiver() {
        IntentFilter myFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        this.registerReceiver(broadcastReceiver, myFilter);
    }

    public void unregisterBroadcastReceiver() {
        this.unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected Merlin createMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .withLogging(true)
                .build(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
        registerBroadcastReceiver();
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()) {
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {
        Log.i("Debug", "online");
        //sync, (check if the lists are empty, if not, add/edit/delete ...)
        //OfflineDataController offlineController = MoodPlusApplication.getOfflineDataController();
        //offlineController.SyncOffline();
        networkStatusDisplayer.displayConnected();
    }

    @Override
    public void onDisconnect() {
        Log.i("Debug", "offline");
        networkStatusDisplayer.displayDisconnected();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastReceiver();
        networkStatusDisplayer.reset();
    }

    private void cameraIntent(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1234);
    }


    public boolean saveImageToInternalStorage(Bitmap image) {
        //Taken from: http://www.e-nature.ch/tech/saving-loading-bitmaps-to-the-android-device-storage-internal-external/
        //2017-03-26
        try {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos = context.openFileOutput("desiredFilename.png", Context.MODE_PRIVATE);

            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

    public static String BitMapToString(Bitmap bitmap){
        //taken from: http://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
        //2017-03-26
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == 1234){
            if(resultCode == Activity.RESULT_OK){
                //handleBigCameraPhoto();
                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) extras.get("data");
                image.setImageBitmap(photo);
                saveImageToInternalStorage(photo);
                imageString = BitMapToString(photo);

                Double result = 4*Math.ceil((imageString.length()/3));
                Log.i("BYTES",""+result);
                if(result > maxBytes){
                    Log.i("BYTES","IMAGE IS TOO BIG");
                }else{
                    Log.i("BYTES","ADD IMAGE");
                }


                Toast.makeText(AddMoodActivity.this, "Image Added",Toast.LENGTH_SHORT).show();

            }
        }
    }

    public Location getLocation() {

        Location currentLocation = new Location("dummyprovider");

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddMoodActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            //Toast.makeText(context, "You have granted permission", Toast.LENGTH_SHORT).show();
            GPSTracker gps = new GPSTracker(context, AddMoodActivity.this);

            // Check if GPS enabled
            if (gps.canGetLocation()) {

                if (gps == null) {
                    gps.showSettingsAlert();
                } else {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    currentLocation.setLatitude(latitude);
                    currentLocation.setLongitude(longitude);

                    location = currentLocation;

                    Toast.makeText(AddMoodActivity.this, "Location Added",Toast.LENGTH_SHORT).show();
                }

            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }
        return null;
    }

    public EditText getMessage() { return message; }

    public void update(MoodPlus moodPlus){
        // TODO implements update method

    }

    public void addMood() {

        trigger = message.getText().toString();
        Boolean trigCheck = triggerCheck();

        if (merlinsBeard.isConnected()) {
            Boolean offline = false;

            if (addLocation && location == null) {
                getLocation();
            }

            if (trigCheck && moodChosen && addLocation) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                MainMPController mpController = MoodPlusApplication.getMainMPController();
                mpController.addMoodParticipant1(trigger, addLocation, latitude, longitude, idString, socialSituation, imageString, colorString, userName);

                saveToList(offline);

                finish();

            } else if (trigCheck && moodChosen) {
                MainMPController mpController = MoodPlusApplication.getMainMPController();
                mpController.addMoodParticipant2(trigger, addLocation, idString, socialSituation, imageString, colorString, userName);

                saveToList(offline);

                finish();
            } else {

                if (!trigCheck) {
                    trigMessage();
                }
                if (!moodChosen) {
                    idMessage();
                }
            }


        }

            //when disconnected
            else {
                Toast.makeText(AddMoodActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                //trigger = message.getText().toString();
                //Boolean trigCheck = triggerCheck();

                //has location
                if (trigCheck && moodChosen && addLocation) {
                    //Toast.makeText(AddMoodActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                    finish();

                    //no location
                } else if (trigCheck && moodChosen) {

                    Boolean offline = true;

                    saveToList(offline);

                    finish();
                } else {

                    if (!trigCheck) {
                        Log.i("Debug", "not trig");
                        trigMessage();
                    }
                    if (!moodChosen) {
                        Log.i("Debug", "not mood");
                        idMessage();
                    }
                }
        }
    }

    public void saveToList(Boolean offline) {
        // Saves the user's moodlist
        Mood mood = dummyMood(trigger, addLocation, idString, socialSituation, imageString, colorString, userName);

        OfflineDataController offlineController = MoodPlusApplication.getOfflineDataController();
        Participant offlineParticipant = offlineController.getOfflineParticipant();
        UserMoodList offlineMoodList = offlineParticipant.getUserMoodList();

        if(offline == true) {
            offlineMoodList.addUserMood(mood);
        }

        UserMoodList offlineList = offlineController.loadSavedList(getApplicationContext());

        if (offlineList == null) {
            offlineList = new UserMoodList();
        }

        offlineList = offlineMoodList;

        offlineController.saveOfflineList(offlineList, context);

        Toast.makeText(AddMoodActivity.this, "Saved Moods!", Toast.LENGTH_SHORT).show();
        Log.d("In ADDMOOD", "Saving to list");
    }


    public Boolean triggerCheck() {
        Boolean check = true;
        trigger = message.getText().toString();
        int trigLen = trigger.length();
        int wordLen = countWord(trigger);
        if (trigLen>20) {
            check = false;
        }
        if (wordLen >3) {
            check = false;
        }
        return check;
    }

    //Taken from http://javarevisited.blogspot.ca/2015/02/how-to-count-number-of-words-in-string.html
    //11 March 2017 19-41
    public int countWord(String word) {
        if (word == null) {
            return 0;
        }
        String input = word.trim();
        int count = input.isEmpty() ? 0 : input.split("\\s+").length;
        return count;
    }

    public void trigMessage() {
        new AlertDialog.Builder(context)
                .setTitle("Trigger Message")
                .setMessage("Trigger must be less than 20 characters or less than 3 words")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void idMessage() {
        new AlertDialog.Builder(context)
                .setTitle("Mood Message")
                .setMessage("A mood is required to post")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission granted
                    GPSTracker gps = new GPSTracker(context, AddMoodActivity.this);

                    // Check if GPS enabled
                    if (gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        Log.i("tag",""+latitude+longitude);
                    } else {
                        // Can't get location.
                        // GPS or network is not enabled.
                        // Ask user to enable GPS/network in settings.
                        gps.showSettingsAlert();
                    }

                } else {

                    // permission denied, disabled the functionality that depends on this permission.
                    Toast.makeText(context, "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    /**
     * Saves tweets in file in JSON format.
     * @throws FileNotFoundException if folder not exists
     */
    private void saveInFile(String filename, UserMoodList userMoodList) {
        try {
            FileOutputStream fos = openFileOutput(filename,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(userMoodList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    private Mood dummyMood(String trigger, Boolean addLocation, String idString,
                            String socialSituation, String imageString, String colorString, String userName){

        Mood mood = new Mood(trigger, addLocation, idString, socialSituation, imageString, colorString, userName);
        mood.setText(trigger);
        mood.setAddLocation(addLocation);
        mood.setId(idString);
        mood.setSocial(socialSituation);
        mood.setPhoto(imageString);
        mood.setColor(colorString);

        return mood;
    }



}

