package edu.gatech.cs2340.hkskh.Shelters;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;

/**
 * Created by baohd on 2/26/2018.
 */
public class ShelterManager {

    /**
     * HashMap of shelters
     */
    static private Map<Integer, Shelter> shelters = new HashMap<>();
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
     * Looks for the shelters that fit the parameters and deletes the others which are not
     * @param name the shelter's name you are looking for
     * @return List of the shelters
     */
    public List<Shelter> getByName(String name) {
        List<Shelter> shelterList = new ArrayList(shelters.values());
        int i = 0;
        while (i < shelterList.size()) {
            if (!shelterList.get(i).getName().equals(name)) {
                shelterList.remove(i);
            }
        }
        if (shelterList.size() == 0) {
            throw new NoSuchElementException("THere is no shelter with this name. Are you sure it exists?");
        } else {
            return shelterList;
        }
    }

    /**
     * Search for appropriate shelter using age or gender as a parameter and return list of appropriate shelters
     * @param restrictions the age or gender. We are searching for exact matches for the criteria of age or gender.
     * @return the list of appropriate shelters
     */
    public List<Shelter> getByGenderOrAge(String restrictions) {
        List<Shelter> shelterList = new ArrayList(shelters.values());
        int i = 0;
        while (i < shelterList.size()) {
            if (!shelterList.get(i).getRestrictions().toLowerCase().contains(restrictions.toLowerCase())) {
                shelterList.remove(i);
            }
        }
        if (shelterList.size() == 0) {
            throw new NoSuchElementException("THere is no shelter that fits these parameters. Please broaden your search.");
        } else {
            return shelterList;
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
    public Collection<Shelter> getAll() {
        return shelters.values();
    }

    /**
     * Wipe out the current shelters
     */
    static void clear() {
        shelters = new HashMap<>();
    }
}
