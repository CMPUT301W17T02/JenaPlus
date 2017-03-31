package com.mood.jenaPlus;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.gms.maps.model.LatLng;
import com.mood.jenaPlus.connectivity.display.NetworkStatusCroutonDisplayer;
import com.mood.jenaPlus.connectivity.display.NetworkStatusDisplayer;
import com.mood.jenaPlus.presentation.base.MerlinActivity;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.NetworkStatus;
import com.novoda.merlin.registerable.bind.Bindable;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.mood.jenaPlus.MapActivity.MY_PERMISSIONS_REQUEST_LOCATION;

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

public class MoodPlusActivity extends MerlinActivity
        implements NavigationView.OnNavigationItemSelectedListener, MPView<MoodPlus>, Connectable, Disconnectable, Bindable {

    private static final String FILENAME = "moodPlus.sav";
    protected ListView moodListView;
    private String searchText = "";
    private String moodId = "";
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

    protected Merlin merlin;

    private MerlinActivity merlinActivity;

    private NetworkStatusDisplayer networkStatusDisplayer;
    private MerlinsBeard merlinsBeard;

    Boolean searching = false;
    Boolean recent = false;
    Boolean moody = false;
    Boolean locationBool = false;

    ArrayList options1 = new ArrayList();
    ArrayList<Mood> locationMoodList = new ArrayList<Mood>();

    UserMoodList offlineList = new UserMoodList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_plus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        moodListView = (ListView) findViewById(R.id.listView);

        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));

        LineChart LineChart = (LineChart) findViewById(R.id.line_chart);

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

        networkStatusDisplayer = new NetworkStatusCroutonDisplayer(this);
        merlinsBeard = MerlinsBeard.from(this);

        /*-------------------- REQUEST LOCATION PERMISSION ------------ */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        /*------------------------------------------------*/



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
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
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
            // Ends current session, has to save offlineList
            super.onBackPressed();

            if(!merlinsBeard.isConnected()) {
                OfflineDataController offlineController = MoodPlusApplication.getOfflineDataController();
                offlineList = offlineController.loadSavedList(MoodPlusActivity.this);

                if (offlineList == null) {
                    offlineList = new UserMoodList();
                }

                offlineList = offlineController.getOfflineParticipant().getUserMoodList();

                offlineController.saveOfflineList(offlineList, context);

                Log.d("ENDING SESSION", "ATTEMPTED TO SAVE");
            }
            else {
                Toast.makeText(context, "ONLINE; don't need to save to file", Toast.LENGTH_SHORT);

                Log.d("HAD INTERNET", "NO NEED TO SAVE BC ALREADY SYNCED");
            }
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
            
            /*----------------------- PASSING CURRENT LOCATION---------------------*/
            location = getLocation();
            LatLng position = new LatLng(location.getLatitude(),location.getLongitude());

            /*----------------------- PASSING CURRENT LOCATION---------------------*/

            ElasticsearchMPController.GetUsersTask getUsersTask = new ElasticsearchMPController.GetUsersTask();
            getUsersTask.execute("");
            ArrayList<Participant> participantList = new ArrayList<Participant>();

            try {
                participantList = getUsersTask.get();
                mpController = MoodPlusApplication.getMainMPController();

            } catch (Exception e) {
                Log.i("Error", "Failed to get the users out of the async object");
            }

            /*----------------------- PASSING FOLLOWING LOCATION---------------------*/

            for(int i=0; i <participantList.size();i++){

                try{
                    ArrayList<Mood> userMoods = participantList.get(i).getUserMoodList().getUserMoodList();
                    getUserMoodOrderedList(userMoods);
                    if(userMoods.get(0).getAddLocation()){

                        double distance;

                        // Create new location for other participants
                        Location locationB = new Location("point B");

                        locationB.setLatitude(userMoods.get(0).getLatitude());
                        locationB.setLongitude(userMoods.get(0).getLongitude());

                        // Check for distance between current location and following location
                        distance = location.distanceTo(locationB);
                        //Log.i("DISTANCEEE!!!","DISTANCE IN METER: " + distance);

                        if(distance <= 5000) {
                            locationMoodList.add(userMoods.get(0));
                        }
                    }
                }catch (Exception e){
                    Log.i("Error","Some participants has no mood");
                }

            }

            for (Mood mood: locationMoodList){
                Log.i("PARTTTTTTTTTTYYYY!!!","Contents of arrayLocation: " + locationMoodList + mood.getUserName() );}


            /*--------------- PASSING LIST OF LOCATIONS TO MAP ACTIVITY ----------------*/
            Bundle args = new Bundle();
            args.putParcelable("longLat_dataProvider",position);
            Intent intent = new Intent(MoodPlusActivity.this, MapActivity.class);
            intent.putExtras(args);
            intent.putExtra("participant_moodProvider", locationMoodList);
            /*--------------- PASSING LIST OF LOCATIONS ----------------*/

            startActivity(intent);

        } else if(id ==R.id.followingDrawer){
            Intent intent = new Intent(MoodPlusActivity.this, FollowingListActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.request) {
            Intent requestIntent = new Intent(MoodPlusActivity.this, FollowerRequestActivity.class);
            startActivity(requestIntent);
        } else if (id == R.id.graph){
            Intent graphIntent = new Intent(MoodPlusActivity.this, GraphActivity.class);
            startActivity(graphIntent);
        } else if (id == R.id.menuMyOwnMoodFilter){
            testFilters();
        } else if(id == R.id.menuMyFollowingFilter){
            testFilters2();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<Mood> getUserMoodOrderedList(ArrayList<Mood> moodArray) {

        Collections.sort(moodArray, new Comparator<Mood>() {

            public int compare(Mood o1, Mood o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return moodArray;
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

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MoodPlusActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

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

    /*-------------------------------------OWN FILTER MOODS---------------------------------------*/

    public void testFilters() {
        final CharSequence[] items = {" Text "," Most Recent "," Mood ", " Location "};
        // arraylist to keep the selected items
        final ArrayList selectedItems=new ArrayList();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select the Filters for your moods")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on OK
                        //  You can write the code  to save the selected item here
                        options1 = selectedItems;
                        for (int i = 0; i <selectedItems.size(); i++) {
                            if (selectedItems.get(i).equals(0)){
                                searching = true;
                            }
                            if(selectedItems.get(i).equals(1)) {
                                recent = true;
                            }
                            if (selectedItems.get(i).equals(2)) {
                                moody = true;
                            }
                            if (selectedItems.get(i).equals(3)) {
                                locationBool = true;
                            }
                        }

                        if (searching && moody) {
                            getMoodDialog1();
                        }
                        else if (searching) {
                            getTextActivity();
                        }
                        else if (moody) {
                            myOwnFiltersDialog();
                        }
                        else if (locationBool){
                            getLocationsFiltered();
                        }
                        else if (recent) {
                            getDateFiltered();
                        }

                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        searching = recent = moody = locationBool = false;
                    }
                }).create();
        dialog.show();
    }

    public void getLocationsFiltered() {
        Intent intent = new Intent(MoodPlusActivity.this, FilteredLocationActivity.class);
        Bundle bundle = new Bundle();
        if (recent) {
            bundle.putString("filterRecent", "yes");
        } else {
            bundle.putString("filterRecent", "no");
        }
        intent.putExtras(bundle);
        searching = recent = moody = locationBool = false;
        moodId = "";
        startActivity(intent);
    }

    public void getMoodFiltered(String mood) {
        Intent intent = new Intent(MoodPlusActivity.this, FilteredMoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("moodString",mood);
        if (recent) {
            bundle.putString("filterRecent", "yes");
        } else {
            bundle.putString("filterRecent", "no");
        }
        if(locationBool){

            bundle.putString("filterLocation", "yes");
        } else {
            bundle.putString("filterLocation", "no");
        }

        intent.putExtras(bundle);
        searching = recent = moody = locationBool = false;
        moodId = "";
        startActivity(intent);

    }


    public void getDateFiltered() {
        Intent intent = new Intent(MoodPlusActivity.this, FilteredDateActivity.class);
        Bundle bundle = new Bundle();
        if (recent) {
            bundle.putString("filterRecent", "yes");
        } else {
            bundle.putString("filterRecent", "no");
        }
        if(locationBool){

            bundle.putString("filterLocation", "yes");
        } else {
            bundle.putString("filterLocation", "no");
        }

        intent.putExtras(bundle);
        startActivity(intent);
        searching = recent = moody = locationBool = false;
    }


    public void filterMoodText() {
        getMoodDialog1();
    }


    public void myOwnFiltersDialog() {
        // Taken from http://stackoverflow.com/questions/30345243/android-dialog-with-multiple
        // -button-how-to-implement-switch-case
        // 2017-03-26 Rajan Bhavsar
        new AlertDialog.Builder(context)
                .setTitle("Filter your own moods")
                .setItems(new CharSequence[]
                                {"Filter By Surprised Moods",
                                        "Filter By Disgusted Moods", "Filter By Fearful Moods",
                                        "Filter By Confused Moods", "Filter By Happy Moods",
                                        "Filter By Angry Moods",
                                        "Filter By Sad Moods", "Filter By Shameful Moods",
                                        "Filter By Annoyed Moods"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        getMoodFiltered("surprised");
                                        Toast.makeText(context, "Filter By Surprised Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        getMoodFiltered("disgust");
                                        Toast.makeText(context, "Filter By Disgusted Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        getMoodFiltered("fear");
                                        Toast.makeText(context, "Filter By Fearful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        getMoodFiltered("confused");
                                        Toast.makeText(context, "Filter By Confused Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4:
                                        getMoodFiltered("happy");
                                        Toast.makeText(context, "Filter By Happy Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5:
                                        getMoodFiltered("angry");
                                        Toast.makeText(context, "Filter By Angry Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 6:
                                        getMoodFiltered("sad");
                                        Toast.makeText(context, "Filter By Sad Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 7:
                                        getMoodFiltered("shame");
                                        Toast.makeText(context, "Filter By Shameful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 8:
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

    public void getMoodDialog1() {
        // Taken from http://stackoverflow.com/questions/30345243/android-dialog-with-multiple-
        // button-how-to-implement-switch-case
        // 2017-03-26 Rajan Bhavsar
        new AlertDialog.Builder(context)
                .setTitle("Choose a mood to filter")
                .setItems(new CharSequence[]
                                {"Filter By Surprised Moods",
                                        "Filter By Disgusted Moods", "Filter By Fearful Moods",
                                        "Filter By Confused Moods", "Filter By Happy Moods",
                                        "Filter By Angry Moods", "Filter By Sad Moods",
                                        "Filter By Shameful Moods", "Filter By Annoyed Moods"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        moodId = "surprised";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Surprised Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        moodId ="disgust";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Disgusted Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        moodId ="fear";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Fearful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        moodId ="confused";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Confused Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4:
                                        moodId ="happy";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Happy Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5:
                                        moodId ="angry";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Angry Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 6:
                                        moodId ="sad";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Sad Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 7:
                                        moodId ="shame";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Shameful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 8:
                                        moodId ="annoyed";
                                        getTextActivityWithMood();
                                        Toast.makeText(context, "Filter By Annoyed Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        })

                .setIcon(android.R.drawable.ic_menu_search)
                .show();
    }

    public void getTextActivity() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Keyword");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password,
        // and will mask the text
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
                if (recent) {
                    bundle.putString("filterRecent", "yes");
                } else {
                    bundle.putString("filterRecent", "no");
                }
                if(locationBool){
                    bundle.putString("filterLocation", "yes");
                } else {
                    bundle.putString("filterLocation", "no");
                }
                bundle.putString("testText",searchText);
                bundle.putString("moodString","no");
                intent.putExtras(bundle);
                searching = recent = moody = locationBool = false;

                startActivity(intent);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searching = recent = moody = false;
                dialog.cancel();

            }
        });

        builder.show();

    }

    public void getTextActivityWithMood() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Keyword");

        final EditText input = new EditText(this);
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
                bundle.putString("moodString", moodId);
                bundle.putString("testText",searchText);
                if (recent) {
                    bundle.putString("filterRecent", "yes");
                } else {
                    bundle.putString("filterRecent", "no");
                }
                if(locationBool){
                    bundle.putString("filterLocation", "yes");
                } else {
                    bundle.putString("filterLocation", "no");
                }
                intent.putExtras(bundle);
                searching = recent = moody = locationBool = false;
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searching = recent = moody = locationBool = false;
                moodId = "";
                dialog.cancel();
            }
        });

        builder.show();

    }
    /*---------------------------------- FOLLOWING FILTER ----------------------------------------*/

    public void testFilters2() {
        final CharSequence[] items = {" Text "," Most Recent "," Mood ", " Location "};
        // arraylist to keep the selected items
        final ArrayList selectedItems=new ArrayList();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Filter your following moods")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on OK
                        //  You can write the code  to save the selected item here
                        options1 = selectedItems;
                        for (int i = 0; i <selectedItems.size(); i++) {
                            if (selectedItems.get(i).equals(0)){
                                searching = true;
                            }
                            if(selectedItems.get(i).equals(1)) {
                                recent = true;
                            }
                            if (selectedItems.get(i).equals(2)) {
                                moody = true;
                            }
                            if (selectedItems.get(i).equals(3)){
                                locationBool = true;
                            }
                        }

                        if (searching && moody) {
                            getMoodDialog2();
                        }
                        else if (searching) {
                            getTextActivity2();
                        }
                        else if (moody) {
                            myFollowingFiltersDialog();
                        }
                        else if (locationBool) {
                            getLocationsFiltered2();
                        }
                        else if (recent) {
                            getDateFiltered2();
                        }


                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        searching = recent = moody = locationBool = false;
                    }
                }).create();
        dialog.show();
    }

    public void getLocationsFiltered2() {
        Intent intent = new Intent(MoodPlusActivity.this, FilterFollowLocationActivity.class);
        Bundle bundle = new Bundle();
        if (recent) {
            bundle.putString("filterRecent", "yes");
        } else {
            bundle.putString("filterRecent", "no");
        }
        intent.putExtras(bundle);
        searching = recent = moody = locationBool = false;
        moodId = "";
        startActivity(intent);
    }

    public void myFollowingFiltersDialog() {
        // Taken from http://stackoverflow.com/questions/30345243/android-dialog-with-multiple-
        // button-how-to-implement-switch-case
        // 2017-03-26 Rajan Bhavsar
        new AlertDialog.Builder(context)
                .setTitle("Filter Following Moods")
                .setItems(new CharSequence[]
                                {"Filter By Surprised Moods",
                                        "Filter By Disgusted Moods", "Filter By Fearful Moods",
                                        "Filter By Confused Moods", "Filter By Happy Moods",
                                        "Filter By Angry Moods",
                                        "Filter By Sad Moods", "Filter By Shameful Moods",
                                        "Filter By Annoyed Moods"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        getMoodFiltered2("surprised");
                                        Toast.makeText(context, "Filter By Surprised Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        getMoodFiltered2("disgust");
                                        Toast.makeText(context, "Filter By Disgusted Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        getMoodFiltered2("fear");
                                        Toast.makeText(context, "Filter By Fearful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        getMoodFiltered2("confused");
                                        Toast.makeText(context, "Filter By Confused Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4:
                                        getMoodFiltered2("happy");
                                        Toast.makeText(context, "Filter By Happy Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5:
                                        getMoodFiltered2("angry");
                                        Toast.makeText(context, "Filter By Angry Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 6:
                                        getMoodFiltered2("sad");
                                        Toast.makeText(context, "Filter By Sad Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 7:
                                        getMoodFiltered2("shame");
                                        Toast.makeText(context, "Filter By Shameful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 8:
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
        if (recent) {
            bundle.putString("filterRecent", "yes");
        } else {
            bundle.putString("filterRecent", "no");
        }
        if(locationBool){
            bundle.putString("filterLocation", "yes");
        } else {
            bundle.putString("filterLocation", "no");
        }
        intent.putExtras(bundle);
        searching = recent = moody = locationBool = false;
        moodId = "";
        startActivity(intent);
    }


    public void getDateFiltered2() {
        Intent intent = new Intent(MoodPlusActivity.this, FilterFollowDateActivity.class);
        Bundle bundle = new Bundle();
        if(locationBool){

            bundle.putString("filterLocation", "yes");
        } else {
            bundle.putString("filterLocation", "no");
        }
        intent.putExtras(bundle);
        searching = recent = moody = locationBool = false;
        startActivity(intent);
    }

    public void getTextActivity2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Keyword");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password,
        // and will mask the text
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
                if (recent) {
                    bundle.putString("filterRecent", "yes");
                } else {
                    bundle.putString("filterRecent", "no");
                }
                if(locationBool){
                    bundle.putString("filterLocation", "yes");
                } else {
                    bundle.putString("filterLocation", "no");
                }
                bundle.putString("moodString","fff");
                intent.putExtras(bundle);
                searching = recent = moody = locationBool =false;
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searching = recent = moody = locationBool = false;
                dialog.cancel();
            }
        });

        builder.show();

    }
    public void getMoodDialog2() {
        // Taken from http://stackoverflow.com/questions/30345243/android-dialog-with-multiple-
        // button-how-to-implement-switch-case
        // 2017-03-26 Rajan Bhavsar
        new AlertDialog.Builder(context)
                .setTitle("Choose a mood to filter")
                .setItems(new CharSequence[]
                                {"Filter By Surprised Moods",
                                        "Filter By Disgusted Moods", "Filter By Fearful Moods",
                                        "Filter By Confused Moods", "Filter By Happy Moods",
                                        "Filter By Angry Moods", "Filter By Sad Moods",
                                        "Filter By Shameful Moods", "Filter By Annoyed Moods"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        moodId = "surprised";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Surprised Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        moodId ="disgust";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Disgusted Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        moodId ="fear";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Fearful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        moodId ="confused";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Confused Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4:
                                        moodId ="happy";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Happy Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5:
                                        moodId ="angry";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Angry Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 6:
                                        moodId ="sad";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Sad Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 7:
                                        moodId ="shame";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Shameful Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 8:
                                        moodId ="annoyed";
                                        getTextActivityWithMood2();
                                        Toast.makeText(context, "Filter By Annoyed Moods",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        })

                .setIcon(android.R.drawable.ic_menu_search)
                .show();
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
        //OfflineDataController offlineController = MoodPlusApplication.getOfflineDataController();
        //offlineController.SyncOffline();
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

    public void getTextActivityWithMood2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Keyword");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchText = input.getText().toString();
                Log.i("Error", "searchtext is: " + searchText);

                Intent intent = new Intent(MoodPlusActivity.this, FilterFollowTextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("moodString", moodId);
                bundle.putString("testText", searchText);
                if (recent) {
                    bundle.putString("filterRecent", "yes");
                } else {
                    bundle.putString("filterRecent", "no");
                }
                if(locationBool){

                    bundle.putString("filterLocation", "yes");
                } else {
                    bundle.putString("filterLocation", "no");
                }

                intent.putExtras(bundle);
                searching = recent = moody = locationBool = false;
                moodId = "";
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searching = recent = moody = locationBool = false;
                moodId = "";
                dialog.cancel();
            }
        });

        builder.show();
    }

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

}
