package com.mood.jenaPlus;

/**
 * Created by Carlo on 2017-02-25.
 */

public class MoodIcon  {

    private String moodString;
    private Integer color;

    public Integer getColor(Integer icon) {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
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
        return mood;
    }
}
