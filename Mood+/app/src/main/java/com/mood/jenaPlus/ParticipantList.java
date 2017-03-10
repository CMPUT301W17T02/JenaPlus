package com.mood.jenaPlus;

import java.util.ArrayList;

/**
 * Created by Carlo on 2017-03-04.
 */

public class ParticipantList {


    private ArrayList<Participant> participantArrayList = new ArrayList<>();

    public ParticipantList() {

    }

    public void addParticipant(Participant participant) {
        boolean seen = true;
        int pListSize = participantArrayList.size();
        for (int i = 0; i<pListSize; i++) {
            if(participantArrayList.get(i).getUserName().equals(participant.getUserName())){
                throw new IllegalArgumentException("Participant exists.");
            } else {
                seen = false;
            }
        }
        if (seen) {
            participantArrayList.add(participant);
        }
    }

    public void setParticipantArrayList(ArrayList<Participant> participantArrayList) {
        this.participantArrayList = participantArrayList;
    }

    public Participant getParticipant(int i) {
        return participantArrayList.get(i);
    }


}
