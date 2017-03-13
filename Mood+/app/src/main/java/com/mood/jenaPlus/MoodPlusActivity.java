package com.mood.jenaPlus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MoodPlusActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MPView<MoodPlus>{

    private static final String FILENAME = "moodPlus.sav";
    protected ListView moodListView;
	private AlertDialog.Builder deleteAlertBuilder;

    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;
    private String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_plus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        moodListView = (ListView) findViewById(R.id.listView);

        TextView test = (TextView) findViewById(R.id.test_string);
        setSupportActionBar(toolbar);

        /* LOADING THE LOGGED IN PARTICIPANT */

        final MainMPController mpController = MoodPlusApplication.getMainMPController();

        Participant participant = mpController.getParticipant();

        String name = participant.getUserName();
        String id = participant.getId();
        String who = "Name: "+ name + ", id: "+id;
        test.setText(who);

        moodListView.setAdapter(adapter);
        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MoodPlusActivity.this, ViewMoodActivity.class);
                intent.putExtra("aMood", (Serializable) moodListView.getItemAtPosition(position));
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

		deleteAlertBuilder = new AlertDialog.Builder(MoodPlusActivity.this);

		moodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
				setResult(RESULT_OK);

				deleteAlertBuilder.setMessage("Are you sure you want to delete this Mood Event?");

				// user selects "Yes" and the Mood Event long clicked will be deleted.
				deleteAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						mpController.deleteMoodParticipant(moodArrayList.get(position));
						adapter.notifyDataSetChanged();
					}
				});

				// user selects "No" and the Mood Even long clicked will NOT be deleted.
				deleteAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

				AlertDialog alertDialog = deleteAlertBuilder.create();
				alertDialog.show();

				return true;
			}
		});

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
            // Handle the camera action
        } else if (id == R.id.request) {

        } else if (id == R.id.menuSortText){
            getTextActivity();
        } else if (id == R.id.menuSortRecent) {
            getDateFiltered();
        } else if (id == R.id.menuMoodSurprise){
            getMoodFiltered("surprised");
        } else if (id == R.id.menuMoodDisgust) {
            getMoodFiltered("disgust");
        } else if (id == R.id.menuMoodFear) {
            getMoodFiltered("fear");
        } else if (id == R.id.menuMoodConfused){
            getMoodFiltered("confused");
        } else if (id == R.id.menuMoodHappy) {
            getMoodFiltered("happy");
        } else if (id == R.id.menuMoodAngry) {
            getMoodFiltered("angry");
        } else if (id == R.id.menuMoodSad) {
            getMoodFiltered("sad");
        } else if (id == R.id.menuMoodShame) {
            getMoodFiltered("shame");
        } else if (id == R.id.menuMoodAnnoyed){
            getMoodFiltered("annoyed");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();
        myMoodList = participant.getUserMoodList();
        moodArrayList = myMoodList.getUserMoodOrderedList();

        adapter = new ArrayAdapter<Mood>(this, R.layout.mood_plus_listview, moodArrayList);
        moodListView.setAdapter(adapter);
    }

    @Override
    public void update(MoodPlus moodPlus){

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

}
