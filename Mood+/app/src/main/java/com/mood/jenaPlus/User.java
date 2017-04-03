package com.mood.jenaPlus;

/**
 * This is a general abstract user class.
 * A user of the app must have a username.
 *
 * @author Cecelia
 */

public abstract class User {

    /**
     * The User name.
     */
    protected String userName;

    /**
     * Gets user name.
     *
     * @return the user name
     */
/*public User(String userName) {
        this.userName = userName;
    }
*/
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {this.userName = userName;}

    /**
     * Has user name boolean.
     *
     * @param userName the user name
     * @return the boolean
     */
    public abstract boolean hasUserName(String userName);





}
