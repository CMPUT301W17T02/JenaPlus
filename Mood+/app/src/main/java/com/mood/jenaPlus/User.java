package com.mood.jenaPlus;

import io.searchbox.annotations.JestId;

/**
 * Created by ceciliaxiang on 2017-02-25.
 */

public abstract class User {

    protected String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public abstract boolean hasUserName(String userName);

    @JestId
    private String id;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}
