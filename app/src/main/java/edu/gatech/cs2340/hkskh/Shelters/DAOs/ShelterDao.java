package edu.gatech.cs2340.hkskh.Shelters.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;

/**
 * Created by henry on 3/15/18.
 */
@Dao
public interface ShelterDao {
    @Query("SELECT * FROM shelters")
    List<Shelter> getAll();

    @Query("SELECT * FROM shelters WHERE id = :id")
    Shelter findShelterById(int id);

    @Query("SELECT * FROM shelters WHERE id IN (:shelterIds)")
    List<Shelter> loadAllByIds(int[] shelterIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Shelter shelter);

    @Insert
    void insertAll(Shelter... shelters);

    @Query("DELETE FROM shelters")
    void clear();

    @Query("SELECT count(*) FROM shelters")
    int count();
}
