package com.mood.jenaPlus;

import android.icu.text.MessagePattern;

import io.searchbox.annotations.JestId;

/**
 * Created by ceciliaxiang on 2017-02-25.
 */

public abstract class User {

    protected String userName;

    /*public User(String userName) {
        this.userName = userName;
    }
*/
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {this.userName = userName;}

    public abstract boolean hasUserName(String userName);





}
