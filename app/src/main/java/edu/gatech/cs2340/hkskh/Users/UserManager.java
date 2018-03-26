package edu.gatech.cs2340.hkskh.Users;

import java.util.HashMap;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;

/**
 * Created by Kirby on 2/16/2018.
 */
public class UserManager {
    private static HashMap<String, User> login = new HashMap<>();
    private static String currUserName = "";//Useful to keep track of the logged in user

    public UserManager() {
    }

    /**
     * Use to validate the login credentials
     *
     * Will return false if there is no user key, hashmap is empty, or the passed in password is incorrect
     * Will return true if the user-pass mapping matches what is passed in
     *
     * @param user a username, String
     * @param pass a password, String
     * @return false if any parameter is null, user is not a valid key, or pass does not equal
     *         the stored password of the user. True if pass matches the stored password for the user
     *         with user as the key
     */
    public boolean checkEntry(String user, String pass) {
        if (user == null || pass == null) {
            return false;
        }
        User check;
        check = login.get(user);
        if (check != null && check.getPassword().equals(pass)) {
            this.currUserName = user;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @params string key, or username
     * @return the User with the passed in username
     */
    public User getUser(String key){
        return login.get(key);
    }

    /**
     * Use to register a new user
     *
     * @param user a username
     * @param name the name of the person trying to register
     * @param type a UserType selected by user. Either user or admin
     * @param pass1 the first password entry
     * @param pass2 the second password entry
     * @return false if any parameter is null, the two passwords are not equal, or a user with the
     *         same username already exists. true if a new user is successfully added in the hashmap
     */
    public boolean register(String user, String name, UserType type, String pass1, String pass2) {
        if (user == null || name == null || type == null || pass1 == null || pass2 == null) {
            return false;
        } else if (!(pass1.equals(pass2))) {
            return false;
        } else if (login.containsKey(user)) {
            return false;
        } else if (!(user.equals(""))) {
            User temp = new User(name, type, pass1);
            login.put(user, temp);
            return true;
        }
        return false;
    }

    /**
     * Use for retrieving Name field of user
     *
     * @param username username of the user
     * @return the name of the user with username as its key
     */
    public String getUserName(String username) {
        return login.get(username).getName();
    }

    /**
     * updates the check in
     * @param username the username of the user
     * @param key the key of the shelter
     * @param rooms number of spots they want to reserve
     * @param family checks if it's family or individuals
     */
    public void checkIn(String username, int key, int rooms, boolean family) {
        login.get(username).setShelterID(key);
        login.get(username).setNumBeds(rooms, family, true);
    }

    public void checkOut(String username, int rooms, boolean family) {
        login.get(username).setNumBeds(rooms, family, false);
        if (login.get(username).getNumBeds(family) == 0 && login.get(username).getNumBeds(!family) == 0) {
            login.get(username).setShelterID(-1);
        }
    }

    public int getShelterId(String username) {
        return login.get(username).getShelterID();
    }

    public int getNumBeds(String username, boolean family) {
        return login.get(username).getNumBeds(family);
    }

    /**
     *
     * @return the username of the current user that is logged in
     */
    public String getCurrUserName() {
        return this.currUserName;
    }
}

