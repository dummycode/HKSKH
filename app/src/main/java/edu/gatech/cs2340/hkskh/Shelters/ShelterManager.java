package edu.gatech.cs2340.hkskh.Shelters;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;

/**
 * Created by baohd on 2/26/2018.
 */
public class ShelterManager {

    /**
     * HashMap of shelters
     */
    static private Map<Integer, Object> shelters = new HashMap<>();
    static boolean isLoaded = false;

    /**
     * Add a shelter to the HashMap
     *
     * @param node shelter to be added
     */
    static void addShelter(Shelter node) {
        shelters.put(node.hashCode(), node);
    }

    /**
     * Get a shelter
     *
     * @param key they key of the shelter to be found
     * @return the shelter they request, or if it doesn't exist return null
     */
    public Object getShelter(int key) {
        if (shelters.containsKey(key)) {
            return shelters.get(key);
        } else {
            return null;
        }
    }

    /**
     * @return the string equivalent of the hashmap.
     */
    public String toString() {
        return shelters.toString();
    }

    /**
     * @return returns a generic collection of all the shelters
     */
    public Collection<Object> getAll() {
        return shelters.values();
    }

    /**
     * Wipe out the current shelters
     */
    static void clear() {
        shelters = new HashMap<>();
    }
}
