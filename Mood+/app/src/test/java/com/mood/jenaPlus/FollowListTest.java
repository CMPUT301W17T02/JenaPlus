package com.mood.jenaPlus;

import org.junit.Test;

/**
 * Created by carrotji on 2017-02-26.
 */

public class FollowListTest {

    @Test
    public void testFollowingRejected(){
        String UserName1 = "Jena1";
        String UserName2 = "Jena2";
        Participant newParticipant1 = new Participant(UserName1);
        Participant newParticipant2 = new Participant(UserName2);

        newParticipant1.followingParticipantsRequest(newParticipant2);
        newParticipant2.followingParticipantsRejected(newParticipant1);
        //assertTrue(participant2.getPending().isEmpty());
    }

    @Test
    public void testFollowingAccepted(){

    }

    @Test
    public void testFollowingRequest(){

    }
}
