package edu.gatech.cs2340.hkskh.Users.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;
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

    @ColumnInfo(name = "numBadAttempts")
    private int numBadAttempts;


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
        this.numBadAttempts = 0;
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
     *  @return the number of incorrect login attempts the user currently has
     */
    public int getNumBadAttempts() {
        return this.numBadAttempts;
    }

    /**
     *
     * @param badAttempts the number of incorrect login attempts
     */
    public void setNumBadAttempts(int badAttempts) {
        this.numBadAttempts = badAttempts;
    }

    /**
     * Resets the number of invalid login attempts to zero
     */
    public void resetNumBadAttempts() {
        this.numBadAttempts = 0;
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
    private boolean isCheckedIn() {
        return !((numFamily == 0) && (numInd == 0));
    }

    /**
     * Validate a password, and increment number of incorrect attempts by one if failed
     *
     * @param pass the password
     * @return true or false
     */
    public boolean validatePass(String pass) {
        if(!this.pass.equals(pass)) {
            numBadAttempts += 1;
            return false;
        } else {
            return true;
        }
    }

    /**
     * Use to determine whether or not user is locked out
     *
     * @return true if the user has 3 or more bad login attempts
     */
    public boolean isLockedOut() {
        return this.numBadAttempts >= 3;
    }

    /**
     * Get status of a user
     *
     * @param shelterManager the shelter manager
     * @return the status string
     */
    public CharSequence getStatus(ShelterManager shelterManager) {
        if (isCheckedIn()) {
            Shelter shelter = shelterManager.findById(getShelterId());
            return "Currently checked in to shelter "
                        + shelter.getName() + " (" + shelter.getId() + ") "
                        + "\n\nFamily spaces reserved: " + getNumFamily()
                        + "\n\nIndividual spaces reserved: " + getNumInd();
        } else {
            return "Not checked in";
        }
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "numInd: " + numInd + "\n" +
                "numFam: " + numFamily;
    }
}

