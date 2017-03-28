package com.mood.jenaPlus;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.NetworkStatus;
import com.novoda.merlin.registerable.bind.Bindable;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;
import com.mood.jenaPlus.presentation.base.MerlinActivity;
import com.mood.jenaPlus.connectivity.display.NetworkStatusDisplayer;
import com.mood.jenaPlus.connectivity.display.NetworkStatusCroutonDisplayer;




/**
 * This is the main activity to edit an existing mood.
 *
 * @author Cecelia
 */

public class EditMoodActivity extends MerlinActivity implements MPView<MoodPlus>, Connectable, Disconnectable, Bindable {


    private Button socialPopup;
    private Button save;
    private ImageView selected_image;

    int idNum;
    int colorNum;
    String socialSituation;
    String trigger;
    String idString;
    String colorString;

    protected String aId;
    protected String aText;
    protected String aDate;
    protected Boolean addLocation;
    protected Double aLatitude;
    protected Double aLongitude;
    protected String aSocial;
    protected String aPhoto;
    private Boolean updatePhoto = false;
    private String imageString = "";
    protected String aColor;

    protected ImageView icon;
    protected TextView situation;
    protected TextView date;
    protected EditText message;

    protected ImageView cameraImage;

    private NetworkStatusDisplayer networkStatusDisplayer;
    private MerlinsBeard merlinsBeard;



    private Calendar dateEditor = Calendar.getInstance();
    int year, month, day;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_mood_interface);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        final MoodPlus moodPlus = MoodPlusApplication.getMoodPlus();
        moodPlus.addView(this);


        final Mood mood = (Mood)getIntent().getSerializableExtra("editMood");

        Bundle bundle = getIntent().getExtras();
        final int position = bundle.getInt("pos");

        networkStatusDisplayer = new NetworkStatusCroutonDisplayer(this);
        merlinsBeard = MerlinsBeard.from(this);

        save = (Button) findViewById(R.id.AddButton);

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        aId = mood.getId();
        icon = (ImageView) findViewById(R.id.cur_mood);
        int recId = getResources().getIdentifier(aId, "drawable", getApplicationContext().getPackageName());
        icon.setImageResource(recId);

        aDate = mood.getDate().toString();
        date = (EditText) findViewById(R.id.time);
        date.setText(aDate);

        aText = mood.getText().toString();
        message = (EditText) findViewById(R.id.message);
        message.setText(aText);

        aSocial = mood.getSocial().toString();
        situation = (TextView) findViewById(R.id.situation);
        situation.setText(aSocial);

        aPhoto = mood.getPhoto().toString();

        aColor = mood.getColor();
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor(aColor));

        addLocation = mood.getAddLocation();
        aLatitude = mood.getLatitude();
        aLongitude = mood.getLongitude();


        cameraImage = (ImageView) findViewById(R.id.selected_image);
        Bitmap photo = ViewMoodActivity.StringToBitMap(aPhoto);
        cameraImage.setImageBitmap(photo);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditMoodActivity.this, myDateListener,
                        year, day,
                        day).show();
            }
        });

        year = dateEditor.get(Calendar.YEAR);
        month = dateEditor.get(Calendar.MONTH);
        day = dateEditor.get(Calendar.DAY_OF_MONTH);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        selected_image = (ImageView) findViewById(R.id.selected_image);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_camera:
                                System.out.println("do camera");
                                cameraIntent();
                                break;
                            case R.id.socialPopup:
                                // Taken from http://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
                                // 04-03-2015 01:16
                                View menuItemView = findViewById(R.id.socialPopup);
                                PopupMenu popup = new PopupMenu(EditMoodActivity.this, menuItemView );
                                //Inflating the Popup using xml file
                                popup.getMenuInflater()
                                        .inflate(R.menu.social_popup, popup.getMenu());

                                //registering popup with OnMenuItemClickListener
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {
                                        Toast.makeText(
                                                EditMoodActivity.this,
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
                                System.out.println("do navigation");
                                break;
                        }
                        return true;
                    }
                }
        );

        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                trigger = message.getText().toString();
                Boolean trigCheck = triggerCheck();

                if (!trigCheck) {
                    trigMessage();

                }

                else {
                    MainMPController mpController = new MainMPController(moodPlus);
                    UserMoodList userMoodList = mpController.getParticipant().getUserMoodList();

                    Mood editedMood = userMoodList.getUserMood(position);

                    editedMood.setText(message.getText().toString());
                    editedMood.setAddLocation(addLocation);
                    editedMood.setLatitude(aLatitude);
                    editedMood.setLongitude(aLongitude);
                    editedMood.setId(aId);
                    editedMood.setDate(dateEditor.getTime());
                    editedMood.setSocial(socialSituation);
                    if(updatePhoto){
                        editedMood.setPhoto(imageString);
                    }else{
                        editedMood.setPhoto(aPhoto);
                    }
                    editedMood.setColor(aColor);

                    moodPlus.updateParticipant();
                    finish();
                }

            }
        });


    }


    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateEditor.set(Calendar.YEAR, year);
            dateEditor.set(Calendar.MONTH, monthOfYear);
            dateEditor.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };

    private void cameraIntent(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1234);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == 1234){
            if(resultCode == Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) extras.get("data");
                selected_image.setImageBitmap(photo);
                imageString = AddMoodActivity.BitMapToString(photo);
                updatePhoto = true;
                Toast.makeText(EditMoodActivity.this, "Image Updated",Toast.LENGTH_SHORT).show();
            }
        }
    }

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


    public Boolean triggerCheck() {
        Boolean check = true;
        trigger = message.getText().toString();
        int trigLen = trigger.length();
        int wordLen = countWord(trigger);
        if (trigLen>20) {
            check = false;
        }
        else if (wordLen >3) {
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


    public void update(MoodPlus moodPlus){
        // TODO implements update method
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
        //updateOfflineRequest();
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkStatusDisplayer.reset();
    }


}