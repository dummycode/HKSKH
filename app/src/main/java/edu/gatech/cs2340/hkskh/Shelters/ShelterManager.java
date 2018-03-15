package edu.gatech.cs2340.hkskh.Shelters;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;

/**
 * Created by baohd on 2/26/2018.
 */
public class ShelterManager {
    
    private AppDatabase adb;

    public ShelterManager(AppDatabase adb) {
        this.adb = adb;
    }

    /**
     * Add a shelter
     *
     * @param shelter shelter to be added
     */
    public void addShelter(Shelter shelter) {
        adb.shelterDao().insert(shelter);
    }

    /**
     * Get a shelter
     *
     * @param key they key of the shelter to be found
     * @return the shelter they request, or if it doesn't exist return null
     */
    public Shelter getShelter(int key) {
        Shelter shelter = adb.shelterDao().findShelterById(key);
        return shelter;
    }

    /**
     * @return returns a generic collection of all the shelters
     */
    public Collection<Shelter> getAll() {
        return adb.shelterDao().getAll();
    }

    /**
     * Wipe out the current shelters
     */
    public void clear() {
        adb.shelterDao().clear();
    }
}
