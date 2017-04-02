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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Piechart extends AppCompatActivity implements MPView<MoodPlus> {


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

    int mSize;

    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    private ImageButton infoButton;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        pieChart = (PieChart) findViewById(R.id.chart1);

        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<String>();


        MainMPController mpController = MoodPlusApplication.getMainMPController();
        Participant participant = mpController.getParticipant();

        myMoodList = participant.getUserMoodList();
        moodArrayList = myMoodList.getUserMoodList();
        mSize = moodArrayList.size();


        for (int i = 0; i<mSize; i++) {
            Log.i("Debug",moodArrayList.get(i).getId());
            if (moodArrayList.get(i).getId().equals(happy)){
                Log.i("Debug",""+happyC);
                happyC++;

            } else if(moodArrayList.get(i).getId().equals(angry)) {
                Log.i("Debug",""+angryC);
                angryC++;

            }else if(moodArrayList.get(i).getId().equals(surprised)) {
                Log.i("Debug",""+surprisedC);
                surprisedC++;

            }else if(moodArrayList.get(i).getId().equals(disgust)) {
                Log.i("Debug",""+disgustC);
                disgustC++;

            }else if(moodArrayList.get(i).getId().equals(fear)) {
                Log.i("Debug",""+fearC);
                fearC++;

            }else if(moodArrayList.get(i).getId().equals(sad)) {
                Log.i("Debug",""+sadC);
                sadC++;

            }else if(moodArrayList.get(i).getId().equals(shame)) {
                Log.i("Debug",""+shameC);
                shameC++;

            }else if(moodArrayList.get(i).getId().equals(annoyed)) {
                Log.i("Debug",""+annoyedC);
                annoyedC++;

            }else if(moodArrayList.get(i).getId().equals(confused)) {
                Log.i("Debug",""+confusedC);
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
}
