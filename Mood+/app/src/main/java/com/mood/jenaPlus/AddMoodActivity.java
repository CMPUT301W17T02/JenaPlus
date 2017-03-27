package com.mood.jenaPlus;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.mood.jenaPlus.NetworkStatusCroutonDisplayer;
import com.mood.jenaPlus.NetworkStatusDisplayer;
import com.mood.jenaPlus.MerlinActivity;
import com.novoda.merlin.registerable.bind.Bindable;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;

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
    private ImageView image;

    Context context = this;

    private Boolean addLocation = false;
    private Location location;
    private String imageString = "";
    private Boolean moodChosen = false;

    private Double latitude;
    private Double longitude;

    private String userName;

    private NetworkStatusDisplayer networkStatusDisplayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mood_interface);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        TextView test = (TextView) findViewById(R.id.addtext);
        networkStatusDisplayer = new NetworkStatusCroutonDisplayer(this);

        /*-------DEBUGGING TO SEE USERNAME AND ID ------*/

        String name = participant.getUserName();
        userName = name;
        String id = participant.getId();
        String who = "UserName: "+ name;
        test.setText(who);

        /*------------------------------------------------*/

        message = (EditText) findViewById(R.id.message);
        addButton = (Button) findViewById(R.id.AddButton);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        image = (ImageView) findViewById(R.id.selected_image);

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
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_camera:
                                cameraIntent();
                                //galleryIntent();

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
                                //Intent intent = new Intent(AddMoodActivity.this, MapActivity.class);
                                //startActivity(intent);
                                location = getLocation();
                                addLocation = true;

                                Toast.makeText(AddMoodActivity.this, "Location Added: "+location.getLatitude()
                                        +","+location.getLongitude() ,Toast.LENGTH_SHORT).show();

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
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    String mCurrentPhotoPath;
    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            mCurrentPhotoPath = null;
        }

    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = image.getWidth();
        int targetH = image.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        image.setImageBitmap(bitmap);
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
                Toast.makeText(AddMoodActivity.this, "Image Added",Toast.LENGTH_SHORT).show();


                //Intent intent = new Intent(AddMoodActivity.this, ViewMoodActivity.class);
                //intent.putExtra("bmp_img", photo);

                // Uri selectedImage = data.getData();
                // image.setImageURI(selectedImage);

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

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                currentLocation.setLatitude(latitude);
                currentLocation.setLongitude(longitude);

                return currentLocation;

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

        if (trigCheck && moodChosen && addLocation) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            MainMPController mpController = MoodPlusApplication.getMainMPController();
            mpController.addMoodParticipant1(trigger,addLocation,latitude,longitude,idString,socialSituation,imageString,colorString,userName);
            finish();
        } else if (trigCheck && moodChosen){
            MainMPController mpController = MoodPlusApplication.getMainMPController();
            mpController.addMoodParticipant2(trigger,addLocation,idString,socialSituation,imageString,colorString,userName);
            finish();
        }

        else {

            if (!trigCheck) {
                trigMessage();
            }
            if (!moodChosen){
                idMessage();
            }
        }

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

    private void galleryIntent(){
        // Taken from http://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
        // 2017-03-10 5:32
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
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
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()) {
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {
        networkStatusDisplayer.displayConnected();
    }

    @Override
    public void onDisconnect() {
        networkStatusDisplayer.displayDisconnected();
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkStatusDisplayer.reset();
    }

}

