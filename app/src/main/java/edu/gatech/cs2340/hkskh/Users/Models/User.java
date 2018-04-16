package edu.gatech.cs2340.hkskh.Users.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Users.Converters.UserTypeConverter;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;

/**
 * Created by Kirby on 2/16/2018.
 * User class that stores
 * all information needed for the user
 * password, username, and name
 */
@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private final String username;

    @ColumnInfo(name = "password")
    private final String pass;

    @ColumnInfo(name = "name")
    private String name;

    @TypeConverters(UserTypeConverter.class)
    private final UserType type;

    @ColumnInfo(name = "shelterId")
    private int shelterId = -1;

    @ColumnInfo(name = "numFamily")
    private int numFamily;

    @ColumnInfo(name = "numInd")
    private int numInd;


    /**
     * Creates a new user
     *
     * @param username name of the user
     * @param type the user type
     * @param pass the password that is needed to give this user access
     */
    public User(String username, UserType type, String pass){
        this.username = username;
        this.type = type;
        this.pass = pass;
    }

    /**
     *
     * @return user if
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the int to set as user id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return username of user
     */
    public String getUsername() {
        return username;
    }



    /**
     *
     * @return password
     */
    public String getPass() {
        return pass;
    }


    /**
     * name of user
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name the actual name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the type of user
     */
    public UserType getType() {
        return type;
    }



    /**
     *
     * @return the shelter user is staying at
     */
    public int getShelterId() {
        return shelterId;
    }

    /**
     *
     * @param key the id of the shelter the user checked into
     */
    public void setShelterId(int key) {
        shelterId = key;
    }

    /**
     *
     * @return how many family spots user is checked into
     */
    public int getNumFamily() {
        return numFamily;
    }

    /**
     *
     * @param numFamily amount of family spots claimed
     */
    public void setNumFamily(int numFamily) {
        this.numFamily = numFamily;
    }

    /**
     *
     * @return amount of individual spots checked into
     */
    public int getNumInd() {
        return numInd;
    }

    /**
     *
     * @param numInd sets individual spots checked into
     */
    public void setNumInd(int numInd) {
        this.numInd = numInd;
    }

    /**
     * Update the count of beds checked into
     *
     * @param bedType bed type to update
     * @param count count to change by
     */
    public void updateBeds(BedType bedType, int count) {
        switch (bedType) {
            case FAMILY:
                numFamily += count;
                break;
            case INDIVIDUAL:
                numInd += count;
                break;
            default:
                numInd += count;
                break;
        }
    }

    /**
     * Get the number of beds checked into
     *
     * @param bedType type of bed to retrieve
     *
     * @return number of beds checked into
     */
    public int getNumBeds(BedType bedType) {
        switch (bedType) {
            case FAMILY:
                return numFamily;
            case INDIVIDUAL:
                return numInd;
            default:
                return numInd;
        }
    }

    /**
     * compute whether or not this user is checked in
     * @return true if both numFamily and numInd is 0
     */
    public boolean isCheckedIn() {
        return !((numFamily == 0) && (numInd == 0));
    }

    /**
     * Validate a password
     *
     * @param pass the password
     * @return true or false
     */
    public boolean validatePass(String pass) {
        return this.pass.equals(pass);
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "numInd: " + numInd + "\n" +
                "numFam: " + numFamily;
    }
}

