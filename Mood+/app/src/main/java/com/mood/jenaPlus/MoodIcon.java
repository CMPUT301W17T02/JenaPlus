package com.mood.jenaPlus;

/**
 * Created by Carlo on 2017-02-25.
 */
public class MoodIcon  {

    private String moodString;
    private String hexColor;

    /**
     * Gets color.
     *
     * @param icon the icon
     * @return the color
     */
    public String getColor(Integer icon) {
        switch (icon) {
            case 0: this.hexColor = "#5CF57113";
                break;
            case 1: this.hexColor = "#6254A62F";
                break;
            case 2: this.hexColor = "#61131313";
                break;
            case 3: this.hexColor = "#606A00FF";
                break;
            case 4: this.hexColor = "#5EFFF649";
                break;
            case 5: this.hexColor = "#5CFF0000";
                break;
            case 6: this.hexColor = "#6033B5E5";
                break;
            case 7: this.hexColor = "#5CDE30C9";
                break;
            default: this.hexColor = "#62277384";
                break;
        }
        return hexColor;
    }

    /**
     * Gets mood.
     *
     * @param iconNum the icon num
     * @return the mood
     */
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
