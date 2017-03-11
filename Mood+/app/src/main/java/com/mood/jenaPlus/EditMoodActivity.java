package com.mood.jenaPlus;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class EditMoodActivity extends Activity implements MPView<MoodPlus> {


    private EditText message;
    private Button socialPopup;
    private Button save;

    int idNum;
    int colorNum;
    String socialSituation;
    String trigger;
    String idString;
    String colorString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_mood_interface);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MoodPlus moodPlus = MoodPlusApplication.getMoodPlus();
        moodPlus.addView(this);

        message = (EditText) findViewById(R.id.message);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_camera:
                                System.out.println("do camera");
                                galleryIntent();
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



        save = (Button) findViewById(R.id.AddButton);
        save.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                
                Intent intent = new Intent(EditMoodActivity.this, MoodPlusActivity.class);
                startActivityForResult(intent,1);
            }
        });


    }



    private void galleryIntent(){
        // Taken from http://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
        // 2017-03-10 5:32
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }


    public void update(MoodPlus moodPlus){
        // TODO implements update method
    }

}
