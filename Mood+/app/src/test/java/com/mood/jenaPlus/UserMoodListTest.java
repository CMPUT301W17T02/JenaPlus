package com.mood.jenaPlus;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by Carlo on 2017-02-25.
 */
public class UserMoodListTest  {
    /**
     * Test add mood.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAddMood() throws Exception{
        List<String> moodIdList = Arrays.asList("surprised","disgust","fear","confused","happy","angry","sad","shame","annoyed");
        List<String> moodColorList = Arrays.asList("#96F57113","#BF54A62F","#A4131313","#A16A00FF","#A7FFF649","#A0FF0000","#FF33B5E5","#AFDE30C9","#B5277384");

        int length = 9;

        for (int i = 0; i<length; i++) {
            UserMoodList userList = new UserMoodList();
            Mood userMood = new Mood("",false,moodIdList.get(i),"","",moodColorList.get(i),"testUserName");
            userMood.setColor(moodColorList.get(i));

            userList.addUserMood(userMood);
            //Testing if adding works
            assertTrue(userList.hasUserMood(userMood));
        }

    }


    /**
     * Test delete mood.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteMood() throws Exception {
        List<String> moodIdList = Arrays.asList("surprised","disgust","fear","confused","happy","angry","sad","shame","annoyed");
        List<String> moodColorList = Arrays.asList("#96F57113","#BF54A62F","#A4131313","#A16A00FF","#A7FFF649","#A0FF0000","#FF33B5E5","#AFDE30C9","#B5277384");

        int length = 9;

        for (int i = 0; i<length; i++) {
            UserMoodList userList = new UserMoodList();
            Mood userMood = new Mood("",false,moodIdList.get(i),"","",moodColorList.get(i),"testUserName");
            userMood.setColor(moodColorList.get(i));

            userList.addUserMood(userMood);

            userList.deleteUserMood(userMood);
            //Testing if deleting works
            assertFalse(userList.hasUserMood(userMood));

        }
        String moodId = "happy";
        String color = "#A7FFF649";
        UserMoodList userList = new UserMoodList();
        Mood userMood = new Mood("",false,moodId,"","",color,"testUserName");

        //Adding a text
        userMood.setText("I'm happy today!");

        //Adding a color
        userMood.setColor(color);

        //Adding a social situation
        userMood.setSocial("I'm alone");

        userList.addUserMood(userMood);

        userList.deleteUserMood(userMood);

        assertFalse(userList.hasUserMood(userMood));

    }

    /**
     * Test has mood.
     *
     * @throws Exception the exception
     */
    @Test
    public void testHasMood() throws Exception {
        List<String> moodIdList = Arrays.asList("surprised","disgust","fear","confused","happy","angry","sad","shame","annoyed");
        List<String> moodColorList = Arrays.asList("#96F57113","#BF54A62F","#A4131313","#A16A00FF","#A7FFF649","#A0FF0000","#FF33B5E5","#AFDE30C9","#B5277384");

        int length = 9;

        for (int i = 0; i<length; i++) {
            UserMoodList userList = new UserMoodList();
            Mood userMood = new Mood("",false,moodIdList.get(i),"","",moodColorList.get(i),"testUserName");
            userMood.setColor(moodColorList.get(i));

            assertFalse(userList.hasUserMood(userMood));

            userList.addUserMood(userMood);

            assertTrue(userList.hasUserMood(userMood));
        }

    }

    /**
     * Test get mood.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetMood() throws Exception {
        String moodId = "happy";
        String color = "#A7FFF649";
        UserMoodList userList = new UserMoodList();
        Mood userMood = new Mood("I'm happy today!",false,moodId,"","",color,"testUserName");


        //Adding a social situation
        userMood.setSocial("I'm alone");

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(userList.hasUserMood(userMood),userList.hasUserMood(returnedMood));

    }

    /**
     * Test get color.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetColor() throws Exception {
        String moodId = "happy";
        String color = "#A7FFF649";
        UserMoodList userList = new UserMoodList();
        Mood userMood = new Mood("I'm happy today!",false,moodId,"","",color,"testUserName");

        //Adding a color
        userMood.setColor(color);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getColor(), userMood.getColor());

    }

    /**
     * Test get photo.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetPhoto() throws Exception {
        String moodId = "happy";
        String color = "#A7FFF649";
        String photo = "photjdkl234j3kjklesfds";
        UserMoodList userList = new UserMoodList();
        Mood userMood = new Mood("I'm happy today!",false,moodId,"",photo,color,"testUserName");

        // adding a color
        userMood.setColor(color);

        // adding a photo
        userMood.setPhoto(photo);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getPhoto(), userMood.getPhoto());
    }

    /**
     * Test get social.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetSocial() throws Exception {
        String moodID = "happy";
        String color = "#A7FFF649";
        String social = "I'm alone";
        UserMoodList userList = new UserMoodList();
        Mood userMood = new Mood("I'm happy today!",false,moodID,"","",color,"testUserName");

        // adding a color
        userMood.setColor(color);

        // adding a social situation
        userMood.setSocial(social);

        userList.addUserMood(userMood);

        Mood returnedMood = userList.getUserMood(0);

        assertEquals(returnedMood.getSocial(), userMood.getSocial());

    }

}
