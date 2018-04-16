package edu.gatech.cs2340.hkskh.Users.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import edu.gatech.cs2340.hkskh.Users.Models.User;

/**
 * Created by henry on 3/14/18.
 * Stores users in database
 */
@Dao
public interface UserDao {
    /**
     * Get user by id
     *
     * @param id of user
     * @return the user
     */
    @Query("SELECT * FROM users WHERE id = :id")
    User findUserById(int id);

    /**
     * Get user by username
     *
     * @param username of user
     * @return the user
     */
    @Query("SELECT * FROM users WHERE username = :username")
    User findUserByUsername(String username);

    /**
     * Insert a user into the database
     * @param user to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    /**
     * Insert multiple users into database
     * @param users to be inserted
     */
    @Insert
    void insertAll(User... users);
}
