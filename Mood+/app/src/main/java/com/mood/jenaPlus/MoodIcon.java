package com.mood.jenaPlus;

/**
 * Created by Carlo on 2017-02-25.
 */

public class MoodIcon  {

    private String moodString;
    private String hexColor;

    public String getColor(Integer icon) {
        switch (icon) {
            case 0: this.hexColor = "#96F57113";
                break;
            case 1: this.hexColor = "#BF54A62F";
                break;
            case 2: this.hexColor = "#A4131313";
                break;
            case 3: this.hexColor = "#A16A00FF";
                break;
            case 4: this.hexColor = "#A7FFF649";
                break;
            case 5: this.hexColor = "#A0FF0000";
                break;
            case 6: this.hexColor = "#FF33B5E5";
                break;
            case 7: this.hexColor = "#AFDE30C9";
                break;
            default: this.hexColor = "#B5277384";
                break;
        }
        return hexColor;
    }

    public String getMood(Integer iconNum) {
        switch (iconNum) {
            case 0: this.moodString = "surprised";
                break;
            case 1: this.moodString = "disgust";
                break;
            case 2: this.moodString = "fear";
                break;
            case 3: this.moodString = "confused";
                break;
            case 4: this.moodString = "happy";
                break;
            case 5: this.moodString = "angry";
                break;
            case 6: this.moodString = "sad";
                break;
            case 7: this.moodString = "shame";
                break;
            case 8: this.moodString = "annoyed";
                break;
        }

        return moodString;
    }
}
