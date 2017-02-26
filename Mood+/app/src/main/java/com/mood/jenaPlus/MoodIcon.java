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
        String mood;
        switch (iconNum) {
            case 0: mood = "surprised";
                break;
            case 1: mood = "disgust";
                break;
            case 2: mood = "fear";
                break;
            case 3: mood = "confused";
                break;
            case 4: mood = "happy";
                break;
            case 5: mood = "angry";
                break;
            case 6: mood = "sad";
                break;
            case 7: mood = "shame";
                break;
            default: mood = "annoyed";
                break;
        }

        this.moodString = mood;
        return moodString;
    }
}
