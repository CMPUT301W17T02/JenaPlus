package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by Carlo on 2017-03-04.
 */

public class ParticipantList {


    private ArrayList<Participant> participantArrayList;

    public ParticipantList(ArrayList<Participant> newList) {
        participantArrayList = newList;
    }

    public ParticipantList() {

    }

    //public ArrayList<Participant> getParticipantArrayList() {
     //   return participantArrayList;
    //}

    public void setParticipantArrayList(ArrayList<Participant> participantArrayList) {
        this.participantArrayList = participantArrayList;
    }

    public Participant getParticipant(int i) {
        return participantArrayList.get(i);
    }


}
