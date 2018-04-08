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
    private String username;

    @ColumnInfo(name = "password")
    private String pass;

    @ColumnInfo(name = "name")
    private String name;

    @TypeConverters(UserTypeConverter.class)
    private UserType type;

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
        this.setUsername(username);
        this.setType(type);
        this.setPass(pass);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public int getShelterId() {
        return shelterId;
    }

    public void setShelterId(int key) {
        shelterId = key;
    }

    public int getNumFamily() {
        return numFamily;
    }

    public void setNumFamily(int numFamily) {
        this.numFamily = numFamily;
    }

    public int getNumInd() {
        return numInd;
    }

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
    public boolean isCheckedIn(){
        return !(numFamily == 0 && (numInd == 0));
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "numInd: " + numInd + "\n" +
                "numFam: " + numFamily;
    }
}

