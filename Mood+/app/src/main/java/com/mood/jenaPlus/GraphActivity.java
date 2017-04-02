package com.mood.jenaPlus;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.entries;
import static android.media.CamcorderProfile.get;
import static com.github.mikephil.charting.charts.CombinedChart.DrawOrder.BAR;

/**
 * This is the Graph activity class of the statistics.
 *
 * @author Cecilia
 */
public class GraphActivity extends AppCompatActivity implements MPView<MoodPlus> {

    /**
     * The Mood array list.
     */
    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;
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
     * The Shame counter.
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
     * The Bar Chart.
     */
    BarChart chart ;
    /**
     * The Bar entry.
     */
    ArrayList<BarEntry> BARENTRY ;
    /**
     * The Bar entry labels.
     */
    ArrayList<String> BarEntryLabels ;
    /**
     * The Bar dataset.
     */
    BarDataSet Bardataset ;
    /**
     * The Barchart data.
     */
    BarData BARDATA ;
    /**
     * The size of userMoodList.
     */
    int mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        chart = (BarChart) findViewById(R.id.BarChart);

        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        myMoodList = participant.getUserMoodList();
        moodArrayList = myMoodList.getUserMoodList();
        mSize = moodArrayList.size();


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


        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");
        Bardataset.setColors(new int[] {
                Color.parseColor("#FFF176"), //yellow
                Color.parseColor("#FF8A80"), //red
                Color.parseColor("#FFCC80"), //orange
                Color.parseColor("#A5D6A7"), //green
                Color.parseColor("#BDBDBD"), //black
                Color.parseColor("#BBDEFB"), //blue
                Color.parseColor("#F8BBD0"), //pink
                Color.parseColor("#A7FFEB"),
                Color.parseColor("#E1BEE7"), //purple
                });

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        chart.setData(BARDATA);

        chart.animateY(3000);
        chart.invalidate();


    }

    /**
     * Add values to bar entry.
     */
    public void AddValuesToBARENTRY(){
        Log.i("counter",""+happyC);
        Log.i("counterC",""+(float)happyC);
        BARENTRY.add(new BarEntry((float)happyC, 0));
        BARENTRY.add(new BarEntry((float)angryC, 1));
        BARENTRY.add(new BarEntry((float)surprisedC, 2));
        BARENTRY.add(new BarEntry((float)disgustC, 3));
        BARENTRY.add(new BarEntry((float)fearC, 4));
        BARENTRY.add(new BarEntry((float)sadC, 5));
        BARENTRY.add(new BarEntry((float)shameC, 6));
        BARENTRY.add(new BarEntry((float)annoyedC, 7));
        BARENTRY.add(new BarEntry((float)confusedC, 8));


    }


    /**
     * Add values to bar entry labels.
     */
    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add(happy);
        BarEntryLabels.add(angry);
        BarEntryLabels.add(surprised);
        BarEntryLabels.add(disgust);
        BarEntryLabels.add(fear);
        BarEntryLabels.add(sad);
        BarEntryLabels.add(shame);
        BarEntryLabels.add(annoyed);
        BarEntryLabels.add(confused);

    }


    @Override
    public void update(MoodPlus moodPlus) {

    }

}



