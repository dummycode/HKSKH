package edu.gatech.cs2340.hkskh.Shelters;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;

/**
 * Created by baohd on 3/5/2018.
 */
public class SearchService {

    /**
     * List of shelters
     */
    private List<Shelter> shelters;

    public SearchService(AppDatabase adb) {
        ShelterManager sheltersList = new ShelterManager(adb);
        shelters = new ArrayList(sheltersList.getAll());
    }

    /**
     * conducts a search based on what type of search it is, age name or gender
     * @param param the type of search to conduct
     * @param find the characteristic of the shelter user is looking for
     * @return list of shelters that fits the parameters
     */
    public List<Shelter> searchChoices(String param, String find) {
        if (param.toLowerCase().equals("name")) {
            return this.getByName(find);
        } else if (param.toLowerCase().equals("age")) {
            return this.getByAge(find);
        } else if (param.toLowerCase().equals("gender")) {
            if (find.toLowerCase().equals("men")) {
                return this.getMen();
            } else if (find.toLowerCase().equals("women")) {
                return this.getWomen();
            } else {
                throw new IllegalArgumentException("Invalid parameter");
            }
        } else {
            throw new IllegalArgumentException("This is not a valid type of search. Please choose a valid type of search.");
        }

    }

    /**
     * Looks for the shelters that fit the parameters and deletes the others which are not
     * @param name the shelter's name you are looking for
     * @return List of the shelters
     */
    public List<Shelter> getByName(String name) {
        List<Shelter> shelterList = new ArrayList(shelters);
        List<Shelter> toReturn = new ArrayList<>();
        int i = 0;
        //if the name is the same, add it
        while (i < shelterList.size()) {
            if (shelterList.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                toReturn.add(shelterList.get(i));
            }
            i++;
        }
        // if everything was removed, the shelter doesn't exist
        if (shelterList.size() == 0) {
            throw new NoSuchElementException("There is no shelter with this name. Are you sure it exists?");
        } else {
            return toReturn;
        }
    }

    /**
     * Search for appropriate shelter using age or gender as a parameter and return list of appropriate shelters
     * @param restrictions the age or gender. We are searching for exact matches for the criteria of age or gender.
     * @return the list of appropriate shelters
     */
    public List<Shelter> getByAge(String restrictions) {
        List<Shelter> shelterList = new ArrayList(shelters);
        List<Shelter> toReturn = new ArrayList<>();
        int i = 0;
        //adds the shelter if it fits our requirements
        while (i < shelterList.size()) {
            if (shelterList.get(i).getRestrictions().toLowerCase().contains(restrictions.toLowerCase())) {
                toReturn.add(shelterList.get(i));
            }
            i++;
        }
        //if it doesn't have any shelters left then it doesn't exist
        if (shelterList.size() == 0) {
            throw new NoSuchElementException("There are no shelters that fits these parameters. Please broaden your search.");
        } else {
            return toReturn;
        }
    }

    /**
     *
     * @return a list of shelters that cater to men
     */
    public List<Shelter> getMen() {
        List<Shelter> shelterList = new ArrayList(shelters);
        List<Shelter> toReturn = new ArrayList<>();
        int i = 0;
        //adds the shelter if it fits our requirements
        while (i < shelterList.size()) {
            if (shelterList.get(i).getRestrictions().toLowerCase().contains("men")
                    && !(shelterList.get(i).getRestrictions().toLowerCase().contains("women"))) {
                toReturn.add(shelterList.get(i));
            }
            i++;
        }
        //if it doesn't have any shelters left then it doesn't exist
        if (shelterList.size() == 0) {
            throw new NoSuchElementException("There are no shelters that fits these parameters. Please broaden your search.");
        } else {
            return toReturn;
        }
    }

    /**
     *
     * @return a list of shelters that cater to women
     */
    public List<Shelter> getWomen() {
        List<Shelter> shelterList = new ArrayList(shelters);
        List<Shelter> toReturn = new ArrayList<>();
        int i = 0;
        //adds the shelter if it fits our requirements
        while (i < shelterList.size()) {
            if (shelterList.get(i).getRestrictions().toLowerCase().contains("women")) {
                toReturn.add(shelterList.get(i));
            }
            i++;
        }
        //if it doesn't have any shelters left then it doesn't exist
        if (shelterList.size() == 0) {
            throw new NoSuchElementException("There are no shelters that fits these parameters. Please broaden your search.");
        } else {
            return toReturn;
        }
    }



}
