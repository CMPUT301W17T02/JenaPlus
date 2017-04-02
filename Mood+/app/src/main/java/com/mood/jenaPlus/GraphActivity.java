package com.mood.jenaPlus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

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

    private ImageButton infoButton;

    Context context = this;

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
                Color.parseColor("#B3E5FC"), //blue
                Color.parseColor("#F48FB1"), //pink
                Color.parseColor("#A7FFEB"),
                Color.parseColor("#E1BEE7"), //purple
                });

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        chart.setData(BARDATA);

        chart.animateY(3000);
        chart.invalidate();


    }
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



