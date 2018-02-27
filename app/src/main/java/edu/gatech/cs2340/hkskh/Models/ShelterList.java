package edu.gatech.cs2340.hkskh.Models;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * Created by baohd on 2/26/2018.
 */

public class ShelterList {
    private Map<Integer, Object> shelters = new HashMap<>();

    /**
     * stores the first instance of shelters
     * @param node the node to initialize
     */
    public ShelterList(Shelter node) {
        this.addShelter(node);
    }

    /**
     *
     * @param node shelter to be added to the list
     */
    public void addShelter(Shelter node) { this.shelters.put(node.getKey(), node); }

    /**
     *
     * @param key they key of the shelter to be found
     * @return the shelter they request, or if it doesn't exist return null
     */
    public Object getShelter(int key) {
        if (this.shelters.containsKey(key)) {
            return this.shelters.get(key);
        } else {
            return null;
        }
    }

    /**
     *
     * @return the string equivalent of the hashmap.
     */
    public String toString() {
        return this.shelters.toString();
    }

    /**
     *
     * @return returns a generic collection of all the shelters
     */
    public Collection<Object> getAll() {
        return this.shelters.values();
    }
}
