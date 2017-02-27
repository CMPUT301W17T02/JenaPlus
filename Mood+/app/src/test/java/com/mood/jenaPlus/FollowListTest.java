package com.mood.jenaPlus;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

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
        assertFalse(newParticipant1.getPendingFollowing().isEmpty());
        assertTrue(newParticipant1.getFollowing().isEmpty());

        newParticipant2.followingParticipantsRejected(newParticipant1);
        assertTrue(newParticipant2.getPendingFollowers().isEmpty());
        assertTrue(newParticipant2.getFollowers().isEmpty());
        assertTrue(newParticipant1.getFollowing().isEmpty());
    }


    @Test
    public void testFollowingAccepted(){
        String UserName1 = "Jena1";
        String UserName2 = "Jena2";
        Participant par1 = new Participant(UserName1);
        Participant par2 = new Participant(UserName2);

        par1.followingParticipantsRequest(par2);
        assertFalse(par1.getPendingFollowing().isEmpty());
        assertTrue(par1.getFollowing().isEmpty());
        assertTrue(par2.getFollowers().isEmpty());

        par2.followingParticipantsAccepted(par1);
        assertTrue(par2.getPendingFollowers().isEmpty());
        assertFalse(par2.getFollowing().isEmpty());

    }

    @Test
    public void testFollowingRequest(){
        String UserName1 = "Jena1";
        String UserName2 = "Jena2";
        Participant par1 = new Participant(UserName1);
        Participant par2 = new Participant(UserName2);

        par1.followingParticipantsRequest(par2);
        assertFalse(par1.getPendingFollowing().isEmpty());
        assertTrue(par1.getFollowing().isEmpty());
        assertTrue(par2.getFollowers().isEmpty());

    }

    @Test
    public void testFollowersRequest(){
        String UserName1 = "Jena1";
        String UserName2 = "Jena2";
        Participant par1 = new Participant(UserName1);
        Participant par2 = new Participant(UserName2);

        //par2 wants to follow par1
        par1.followerParticipantsRequest(par2);
        assertFalse(par1.getPendingFollowers().isEmpty());
        assertTrue(par1.getFollowers().isEmpty());
        assertTrue(par2.getFollowing().isEmpty());

    }

    @Test
    public void testFollowersAccepted(){
        String UserName1 = "Jena1";
        String UserName2 = "Jena2";
        Participant par1 = new Participant(UserName1);
        Participant par2 = new Participant(UserName2);

        par1.followerParticipantsRequest(par2);
        assertFalse(par1.getPendingFollowers().isEmpty());
        assertTrue(par1.getFollowers().isEmpty());
        assertTrue(par2.getFollowing().isEmpty());

        par1.followerParticipantsAccepted(par2);
        assertTrue(par1.getPendingFollowers().isEmpty());
        assertFalse(par1.getFollowers().isEmpty());

    }

    @Test
    public void testFollowersRejected(){
        String UserName1 = "Jena1";
        String UserName2 = "Jena2";
        Participant par1 = new Participant(UserName1);
        Participant par2 = new Participant(UserName2);

        par1.followerParticipantsRequest(par2);
        assertFalse(par1.getPendingFollowers().isEmpty());
        assertTrue(par1.getFollowers().isEmpty());
        assertTrue(par2.getFollowing().isEmpty());

        par1.followerParticipantsRejected(par2);
        assertTrue(par1.getFollowers().isEmpty());
        assertTrue(par2.getFollowing().isEmpty());
        assertTrue(par1.getPendingFollowers().isEmpty());

    }
}
