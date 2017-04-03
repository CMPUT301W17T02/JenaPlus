package com.mood.jenaPlus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ImageButton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * This is the pie chart activity class of the statistics.
 * It shows the pie chart statistic for the participant,
 * and gives two options to filter most recent and all moods.
 *
 * @author Cecilia
 * @version 1.0
 */
public class StatsActivity extends AppCompatActivity implements MPView<MoodPlus> {


    /**
     * The Mood array list.
     */
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();

    /**
     * The Happy.
     */
    String happy = "happy";
    /**
     * The Angry.
     */
    String angry = "angry";
    /**
     * The Surprised.
     */
    String surprised = "surprised";
    /**
     * The Disgust.
     */
    String disgust = "disgust";
    /**
     * The Fear.
     */
    String fear = "fear";
    /**
     * The Sad.
     */
    String sad = "sad";
    /**
     * The Shame.
     */
    String shame = "shame";
    /**
     * The Annoyed.
     */
    String annoyed = "annoyed";
    /**
     * The Confused.
     */
    String confused = "confused";

    /**
     * The Happy counter.
     */
    int happyC = 0;
    /**
     * The Angry counter.
     */
    int angryC = 0;
    /**
     * The Surprised counter.
     */
    int surprisedC = 0;
    /**
     * The Disgust counter.
     */
    int disgustC = 0;
    /**
     * The Fear counter.
     */
    int fearC = 0;
    /**
     * The Sad counter.
     */
    int sadC = 0;
    /**
     * The Shame c.
     */
    int shameC = 0;
    /**
     * The Annoyed counter.
     */
    int annoyedC = 0;
    /**
     * The Confused counter.
     */
    int confusedC = 0;

    /**
     * The size of userMoodList.
     */
    int mSize;

    /**
     * The Pie chart.
     */
    PieChart pieChart ;
    /**
     * The Entries.
     */
    ArrayList<Entry> entries ;
    /**
     * The Pie entry labels.
     */
    ArrayList<String> PieEntryLabels ;
    /**
     * The Pie data set.
     */
    PieDataSet pieDataSet ;
    /**
     * The Pie data.
     */
    PieData pieData ;
    private ImageButton infoButton;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        pieChart = (PieChart) findViewById(R.id.chart1);

        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<String>();


        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        Bundle bundle = getIntent().getExtras();
        String dateCheck = bundle.getString("sevenDays");

        myMoodList = participant.getUserMoodList();
        ArrayList<Mood> first = myMoodList.getUserMoodList();

        List<Mood> temp = first;
        if (dateCheck.equals("yes")) {
            for(Iterator<Mood> iterator = temp.iterator(); iterator.hasNext();) {
                Mood mood = iterator.next();
                Date tempDate = mood.getDate();
                if(!isWithinRange(tempDate)){
                    iterator.remove();
                }
            }
        }

        moodArrayList.addAll(temp);
        mSize = moodArrayList.size();
        pieChart.setDescription("Mood events statistics");


        for (int i = 0; i<mSize; i++) {
            Log.i("Debug",moodArrayList.get(i).getId());
            if (moodArrayList.get(i).getId().equals(happy)){
                happyC++;

            } else if(moodArrayList.get(i).getId().equals(angry)) {
                angryC++;

            }else if(moodArrayList.get(i).getId().equals(surprised)) {
                surprisedC++;

            }else if(moodArrayList.get(i).getId().equals(disgust)) {
                disgustC++;

            }else if(moodArrayList.get(i).getId().equals(fear)) {
                fearC++;

            }else if(moodArrayList.get(i).getId().equals(sad)) {
                sadC++;

            }else if(moodArrayList.get(i).getId().equals(shame)) {
                shameC++;

            }else if(moodArrayList.get(i).getId().equals(annoyed)) {
                annoyedC++;

            }else if(moodArrayList.get(i).getId().equals(confused)) {
                confusedC++;
            }

        }

        AddValuesToPIEENTRY();
        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");
        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieDataSet.setColors(new int[] {Color.parseColor("#FFF176"), //yellow
                Color.parseColor("#FF8A80"), //red
                Color.parseColor("#FFCC80"), //orange
                Color.parseColor("#A5D6A7"), //green
                Color.parseColor("#BDBDBD"), //black
                Color.parseColor("#BBDEFB"), //blue
                Color.parseColor("#F8BBD0"), //pink
                Color.parseColor("#A7FFEB"),
                Color.parseColor("#E1BEE7"), //purple
        });

        pieChart.setData(pieData);
        pieChart.animateY(3000);
        pieChart.invalidate();

        infoButton = (ImageButton) findViewById(R.id.info);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.color_legend);

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

    }

    /**
     * Add values to pie entry.
     */
    public void AddValuesToPIEENTRY(){

        entries.add(new BarEntry((float)happyC, 0));
        entries.add(new BarEntry((float)angryC, 1));
        entries.add(new BarEntry((float)surprisedC, 2));
        entries.add(new BarEntry((float)disgustC, 3));
        entries.add(new BarEntry((float)fearC, 4));
        entries.add(new BarEntry((float)sadC, 5));
        entries.add(new BarEntry((float)shameC, 6));
        entries.add(new BarEntry((float)annoyedC, 7));
        entries.add(new BarEntry((float)confusedC, 8));


    }

    /**
     * Add values to pie entry labels.
     */
    public void AddValuesToPieEntryLabels(){

        PieEntryLabels.add(happy);
        PieEntryLabels.add(angry);
        PieEntryLabels.add(surprised);
        PieEntryLabels.add(disgust);
        PieEntryLabels.add(fear);
        PieEntryLabels.add(sad);
        PieEntryLabels.add(shame);
        PieEntryLabels.add(annoyed);
        PieEntryLabels.add(confused);

    }


    @Override
    public void update(MoodPlus moodPlus) {

    }
    static boolean isWithinRange(Date testDate) {
        Date endDate = new Date();
        Date startDate = new Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000);
        return !(testDate.before(startDate) || testDate.after(endDate));
    }
}
