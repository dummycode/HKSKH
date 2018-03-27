package edu.gatech.cs2340.hkskh.Users.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.cs2340.hkskh.Users.Models.User;

/**
 * Created by henry on 3/14/18.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id = :id")
    User findUserById(int id);

    @Query("SELECT * FROM users WHERE username = :username")
    User findUserByUsername(String username);

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert
    void insertAll(User... users);
}
