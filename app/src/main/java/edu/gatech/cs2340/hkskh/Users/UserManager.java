package edu.gatech.cs2340.hkskh.Users;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Users.DAOs.UserDao;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;

/**
 * Created by Kirby on 2/16/2018.
 * Manages the user class, what data is entered
 * and what is pulled out
 */
public class UserManager {
    private final UserDao userDao;

    /**
     * Constructor for the user manager
     *
     * @param adb dependency injected database
     */
    public UserManager(AppDatabase adb) {
        this.userDao = adb.userDao();
    }

    /**
     * Use to validate the login credentials
     *
     * @param username a username, String
     * @param pass a password, String
     * @return true or false based on validation of credentials
     */
    public boolean validateCredentials(String username, String pass) {
        if ((username == null) || (pass == null)) {
            return false;
        }
        User user = userDao.findUserByUsername(username);
        String userPass = "";
        if (user != null) {
            userPass = user.getPass();
        }
        return (user != null) && (userPass.equals(pass));
    }

    /**
     * Use to register a new user
     *
     * @param username a username
     * @param name the name of the person trying to register
     * @param type a UserType selected by user. Either user or admin
     * @param pass a password
     *
     * @return a success status
     */
    public boolean register(String username, String name, UserType type, String pass) {
        // No arguments can be null
        if ((username == null) || (name == null) || (type == null) || (pass == null)) {
            return false;
        }
        if (username.length() < 3) {
            return false;
        }
        User user = userDao.findUserByUsername(username);
        // Username is taken
        if (user != null) {
            return false;
        } else {
            user = new User(username, type, pass);
            userDao.insertAll(user);
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
        return userDao.findUserById(userId);
    }

    /**
     * Retrieve user given a username
     *
     * @param username username of the user
     * @return the user from the username
     */
    public User findByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    /**
     * Check into rooms
     *
     * @param user the user to be updated
     * @param shelterId the key of the shelter
     * @param count number of spots they want to reserve
     * @param bedType checks if it's family or individuals
     */
    public void checkIn(User user, int shelterId, int count, BedType bedType) {
        if (user != null) {
            user.setShelterId(shelterId);
            user.updateBeds(bedType, count);
            userDao.insert(user);
        }
    }

    /**
     * Check out of rooms
     *
     * @param user the user to be updated
     * @param bedNumber number of spots they want to reserve
     * @param bedType checks if it's family or individuals
     */
    public void checkOut(User user, int bedNumber, BedType bedType) {
        if (user != null) {
            user.updateBeds(bedType, -bedNumber);
            if ((user.getNumFamily() == 0) && (user.getNumInd() == 0)) {
                user.setShelterId(-1);
            }
            userDao.insert(user);
        }
    }
}

