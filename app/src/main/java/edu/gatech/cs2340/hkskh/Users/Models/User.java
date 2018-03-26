package edu.gatech.cs2340.hkskh.Users.Models;

import edu.gatech.cs2340.hkskh.Users.Enums.UserType;

/**
 * Created by Kirby on 2/16/2018.
 */

public class User {
    private String name;
    private UserType type;
    private String pass;
    private int shelterID = -1;
    private int numFamily = 0;
    private int numInd = 0;

    /**
     * Creates a new user
     *
     * @param name name of the user
     * @param type the user type
     * @param pass the password that is needed to give this user access
     */
    public User(String name, UserType type, String pass){
        this.name = name;
        this.type = type;
        this.pass = pass;
    }

    /**
     * No-arg constructor. Do not call this directly
     */
    public User() {
        this("user", UserType.USER, "pass");
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return pass;
    }

    public UserType getAccountType() {
        return type;
    }

    public int getNumBeds(boolean family) {
        if (family)
            return this.numFamily;
        else {
            return this.numInd;
        }
    }

    public int getShelterID() {
        return this.shelterID;
    }

    public void setShelterID(int key) {
        this.shelterID = key;
    }

    /**
     * updates the number of beds the user has checked into
     * @param beds the number of beds the user checked into
     * @param family if it's family rooms or not
     */
    public void setNumBeds(int beds, boolean family, boolean release) {
        if (release) {
            if (family) {
                this.numFamily += beds;
            } else {
                this.numInd += beds;
            }
        } else {
            if (family && this.numFamily - beds >= 0) {
                this.numFamily -= beds;
            } else if (!family && this.numInd - beds >= 0) {
                this.numInd -= beds;
            }
        }
    }

    /**
     * want to display amount of beds claimed
     * @return number of family spots claimed
     */
    public int getNumFamily() {
        return this.numFamily;
    }

    /**
     * want to display the number of individual spots claimed
     * @return number of individual spots claimed
     */
    public int getNumInd() {
        return this.numInd;
    }

    /**
     * compute whether or not this user is checked in
     * @return true if both numFamily and numInd is 0
     */
    public boolean isCheckedIn(){
        return !(numFamily == 0 && numInd == 0);
    }
}

