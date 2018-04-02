package edu.gatech.cs2340.hkskh.Users;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;

/**
 * Created by Kirby on 2/16/2018.
 */
public class UserManager {
    private AppDatabase adb;

    /**
     * Constructor for the user manager
     *
     * @param adb dependency injected database
     */
    public UserManager(AppDatabase adb) {
        this.adb = adb;
    }

    /**
     * Use to validate the login credentials
     *
     * Will return false if there is no user key, hashmap is empty, or the passed in password is incorrect
     * Will return true if the user-pass mapping matches what is passed in
     *
     * @param username a username, String
     * @param pass a password, String
     * @return false if any parameter is null, user is not a valid key, or pass does not equal
     *         the stored password of the user. True if pass matches the stored password for the user
     *         with user as the key
     */
    public boolean login(String username, String pass) {
        if (username == null || pass == null) {
            return false;
        }
        User user = adb.userDao().findUserByUsername(username);
        return user != null && user.getPass().equals(pass);
    }

    /**
     * Use to register a new user
     *
     * @param username a username
     * @param name the name of the person trying to register
     * @param type a UserType selected by user. Either user or admin
     * @param pass a password
     * @return false if any parameter is null, the two passwords are not equal, or a user with the
     *         same username already exists. true if a new user is successfully added in the hashmap
     */
    public boolean register(String username, String name, UserType type, String pass) {
        // No arguments can be null
        if (username == null || name == null || type == null || pass == null) {
            return false;
        }
        if (username.length() < 3) {
            return false;
        }
        User user = adb.userDao().findUserByUsername(username);
        // Username is taken
        if (user != null) {
            return false;
        } else {
            user = new User(username, type, pass);
            adb.userDao().insertAll(user);
            return true;
        }
    }

    /**
     * Retrieve user given an id
     *
     * @param userId id of the user
     * @return the user from the id
     */
    public User findById(int userId) {
        User user = adb.userDao().findUserById(userId);
        return user;
    }

    /**
     * Retrieve user given a username
     *
     * @param username username of the user
     * @return the user from the username
     */
    public User findByUsername(String username) {
        User user = adb.userDao().findUserByUsername(username);
        return user;
    }

    /**
     * Check into rooms
     *
     * @param user the user to be updated
     * @param key the key of the shelter
     * @param count number of spots they want to reserve
     * @param bedType checks if it's family or individuals
     */
    public void checkIn(User user, int key, int count, BedType bedType) {
        if (user != null) {
            user.setShelterId(key);
            user.updateBeds(bedType, count);
            adb.userDao().insert(user);
        }
    }

    /**
     * Check out of rooms
     *
     * @param user the user to be updated
     * @param count number of spots they want to reserve
     * @param bedType checks if it's family or individuals
     */
    public void checkOut(User user, int count, BedType bedType) {
        if (user != null) {
            user.updateBeds(bedType, -count);
            if (user.getNumFamily() == 0 && user.getNumInd() == 0) {
                user.setShelterId(-1);
            }
            adb.userDao().insert(user);
        }
    }
}

