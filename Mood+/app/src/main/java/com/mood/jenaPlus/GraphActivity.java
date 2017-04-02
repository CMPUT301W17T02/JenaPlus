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

public class GraphActivity extends AppCompatActivity implements MPView<MoodPlus> {

    ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private UserMoodList myMoodList = new UserMoodList();
    private ArrayAdapter<Mood> adapter;
    String happy = "happy";
    String angry = "angry";
    String surprised = "surprised";
    String disgust = "disgust";
    String fear = "fear";
    String sad = "sad";
    String shame = "shame";
    String annoyed = "annoyed";
    String confused = "confused";

    int happyC = 0;
    int angryC = 0;
    int surprisedC = 0;
    int disgustC = 0;
    int fearC = 0;
    int sadC = 0;
    int shameC = 0;
    int annoyedC = 0;
    int confusedC = 0;

    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    int mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        chart = (BarChart) findViewById(R.id.BarChart);


        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();
        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");
        Bardataset.setColors(new int[] {Color.parseColor("#FFCC80"),Color.parseColor("#A5D6A7"),
                Color.parseColor("#BDBDBD"),
                Color.parseColor("#E1BEE7"),Color.parseColor("#FFF176"),Color.parseColor("#FF8A80"),
                Color.parseColor("#BBDEFB"),Color.parseColor("#F8BBD0"),Color.parseColor("#A7FFEB"),
                });

        BARDATA = new BarData(BarEntryLabels, Bardataset);


        chart.setData(BARDATA);

        chart.animateY(3000);
        chart.invalidate();


    }
    public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(2f, 0));
        BARENTRY.add(new BarEntry(4f, 1));
        BARENTRY.add(new BarEntry(5f, 2));
        BARENTRY.add(new BarEntry(2f, 3));
        BARENTRY.add(new BarEntry(7f, 4));
        BARENTRY.add(new BarEntry(7f, 5));
        BARENTRY.add(new BarEntry(9f, 6));
        BARENTRY.add(new BarEntry(10f, 7));
        BARENTRY.add(new BarEntry(1f, 8));


    }


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



