package edu.gatech.cs2340.hkskh.Shelters.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;

/**
 * Created by henry on 3/15/18.
 * stores the database for shelters?
 */
@Dao
public interface ShelterDao {
    /**
     * Get all shelters
     * @return list of all shelters
     */
    @Query("SELECT * FROM shelters")
    List<Shelter> getAll();

    /**
     * Get a shelter by id
     * @param id of shelter
     * @return the shelter
     */
    @Query("SELECT * FROM shelters WHERE id = :id")
    Shelter findShelterById(int id);

    /**
     * Insert a shelter into the database
     * @param shelter to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Shelter shelter);

    /**
     * Delete all shelters
     */
    @Query("DELETE FROM shelters")
    void clear();

    /**
     * Get a count of the current shelters in database
     * @return count
     */
    @Query("SELECT count(*) FROM shelters")
    int count();
}
