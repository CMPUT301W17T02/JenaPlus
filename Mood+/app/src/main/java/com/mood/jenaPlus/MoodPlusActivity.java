package com.mood.jenaPlus;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * This is the main activity class of the MoodPlus application. From this activity
 * A participant may add a new mood or view an existing mood.
 * If the participant uses the slider navigation drawer, the user is able to filter
 * moods by text, a certain mood, or by the date (last 7 days).
 * @author Carlo
 * @author Cecelia
 * @author Carrol
 * @author Julienne
 * @author Helen
 * @version 1.0
 */

public class MoodPlusActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MPView<MoodPlus>{

    private static final String FILENAME = "moodPlus.sav";
    protected ListView moodListView;

    private String searchText = "";

    protected MainMPController mpController;

    public ListView getMoodListView(){
        return moodListView;
    }
    TabLayout tabLayout;
    ViewPager viewPager;

    Context context = this;

    private Double latitude;
    private Double longitude;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_plus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        moodListView = (ListView) findViewById(R.id.listView);

        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.menu_tab);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });


        /* LOADING THE LOGGED IN PARTICIPANT */

        final MainMPController mpController = MoodPlusApplication.getMainMPController();

        Participant participant = mpController.getParticipant();

        String name = participant.getUserName();
        String id = participant.getId();

        /*----------------------ADD MOOD BUTTON-----------------------*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MoodPlusActivity.this, AddMoodActivity.class);
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView textName = (TextView)header.findViewById(R.id.username);
        textName.setText(name);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mood_plus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_following) {
            Intent intent = new Intent(MoodPlusActivity.this, FollowActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nearMe) {
            location = getLocation();
            LatLng position = new LatLng(location.getLatitude(),location.getLongitude());

            Bundle args = new Bundle();
            args.putParcelable("longLat_dataProvider",position);
            Intent intent = new Intent(MoodPlusActivity.this, MapActivity.class);
            intent.putExtras(args);
            startActivity(intent);
        } else if(id ==R.id.followingDrawer){
            Intent intent = new Intent(MoodPlusActivity.this, FollowingListActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.request) {
            Intent requestIntent = new Intent(MoodPlusActivity.this, FollowerRequestActivity.class);
            startActivity(requestIntent);
        } else if (id == R.id.menuMyOwnMoodFilter){
            myOwnFiltersDialog();
        } else if(id == R.id.menuMyFollowingFilter){
            myFollowingFiltersDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void update(MoodPlus moodPlus){

    }

    public Location getLocation() {

        Location currentLocation = new Location("dummyprovider");

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MoodPlusActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            //Toast.makeText(context, "You have granted permission", Toast.LENGTH_SHORT).show();
            GPSTracker gps = new GPSTracker(context, MoodPlusActivity.this);

            // Check if GPS enabled
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

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

    public void getTextActivity() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Keyword");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchText = input.getText().toString();
                Log.i("Error","searchtext is: "+searchText);


                Intent intent = new Intent(MoodPlusActivity.this, FilteredTextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("testText",searchText);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private class CustomAdapter extends FragmentPagerAdapter {
        //taken from https://github.com/miskoajkula/viewpager-tablayout/tree/master/app/src/main/java/my/test/myapplication

        private String fragments [] = {"YOU","FOLLOWING"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new MoodListViewActivity();
                case 1:
                    return new FollowingViewActivity();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }


    public void myFollowingFiltersDialog() {
        // Taken from http://stackoverflow.com/questions/30345243/android-dialog-with-multiple-button-how-to-implement-switch-case
        // 2017-03-26 Rajan Bhavsar
        new AlertDialog.Builder(context)
                .setTitle("Filter Following Moods")
                .setItems(new CharSequence[]
                                {"Filter By Most Recent", "Filter By Text", "Filter By Surprised Moods",
                                        "Filter By Disgusted Moods", "Filter By Fearful Moods",
                                "Filter By Confused Moods", "Filter By Happy Moods", "Filter By Angry Moods",
                                "Filter By Sad Moods", "Filter By Shameful Moods", "Filter By Annoyed Moods"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        getDateFiltered2();
                                        Toast.makeText(context, "Filter By Most Recent",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        getTextActivity2();
                                        Toast.makeText(context, "Filter By Text",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        getMoodFiltered2("surprised");
                                        Toast.makeText(context, "Filter By Surprised Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        getMoodFiltered2("disgust");
                                        Toast.makeText(context, "Filter By Disgusted Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4:
                                        getMoodFiltered2("fear");
                                        Toast.makeText(context, "Filter By Fearful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5:
                                        getMoodFiltered2("confused");
                                        Toast.makeText(context, "Filter By Confused Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 6:
                                        getMoodFiltered2("happy");
                                        Toast.makeText(context, "Filter By Happy Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 7:
                                        getMoodFiltered2("angry");
                                        Toast.makeText(context, "Filter By Angry Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 8:
                                        getMoodFiltered2("sad");
                                        Toast.makeText(context, "Filter By Sad Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 9:
                                        getMoodFiltered2("shame");
                                        Toast.makeText(context, "Filter By Shameful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 10:
                                        getMoodFiltered2("annoyed");
                                        Toast.makeText(context, "Filter By Annoyed Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            }
                        })

                .setIcon(android.R.drawable.ic_menu_search)
                .show();
    }

    public void getMoodFiltered2(String mood) {
        Intent intent = new Intent(MoodPlusActivity.this, FilterFollowMoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("moodString",mood);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void getDateFiltered2() {
        Intent intent = new Intent(MoodPlusActivity.this, FilterFollowDateActivity.class);
        startActivity(intent);
    }

    public void getTextActivity2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Keyword");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchText = input.getText().toString();

                Intent intent = new Intent(MoodPlusActivity.this, FilterFollowTextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("testText",searchText);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    public void getMoodFiltered(String mood) {
        Intent intent = new Intent(MoodPlusActivity.this, FilteredMoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("moodString",mood);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void getDateFiltered() {
        Intent intent = new Intent(MoodPlusActivity.this, FilteredDateActivity.class);
        startActivity(intent);
    }


    public void myOwnFiltersDialog() {
        // Taken from http://stackoverflow.com/questions/30345243/android-dialog-with-multiple-button-how-to-implement-switch-case
        // 2017-03-26 Rajan Bhavsar
        new AlertDialog.Builder(context)
                .setTitle("Filter your own moods")
                .setItems(new CharSequence[]
                                {"Filter By Most Recent", "Filter By Text", "Filter By Surprised Moods",
                                        "Filter By Disgusted Moods", "Filter By Fearful Moods",
                                        "Filter By Confused Moods", "Filter By Happy Moods", "Filter By Angry Moods",
                                        "Filter By Sad Moods", "Filter By Shameful Moods", "Filter By Annoyed Moods"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        getDateFiltered();
                                        Toast.makeText(context, "Filter By Most Recent",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        getTextActivity();
                                        Toast.makeText(context, "Filter By Text",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        getMoodFiltered("surprised");
                                        Toast.makeText(context, "Filter By Surprised Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        getMoodFiltered("disgust");
                                        Toast.makeText(context, "Filter By Disgusted Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4:
                                        getMoodFiltered("fear");
                                        Toast.makeText(context, "Filter By Fearful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5:
                                        getMoodFiltered("confused");
                                        Toast.makeText(context, "Filter By Confused Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 6:
                                        getMoodFiltered("happy");
                                        Toast.makeText(context, "Filter By Happy Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 7:
                                        getMoodFiltered("angry");
                                        Toast.makeText(context, "Filter By Angry Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 8:
                                        getMoodFiltered("sad");
                                        Toast.makeText(context, "Filter By Sad Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 9:
                                        getMoodFiltered("shame");
                                        Toast.makeText(context, "Filter By Shameful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 10:
                                        getMoodFiltered("annoyed");
                                        Toast.makeText(context, "Filter By Annoyed Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            }
                        })

                .setIcon(android.R.drawable.ic_menu_search)
                .show();
    }

}
