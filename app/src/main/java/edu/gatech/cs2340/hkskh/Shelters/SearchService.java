package edu.gatech.cs2340.hkskh.Shelters;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

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
    private final ArrayList<Shelter> shelters;

    @SuppressWarnings("unchecked")
    public SearchService(AppDatabase adb) {
        ShelterManager sheltersList = new ShelterManager(adb);
        shelters = new ArrayList(sheltersList.getAll());
    }

    /**
     * Conducts a search based on what type of search it is, age name or gender
     *
     * @param searchType the type of search to conduct
     * @param request the characteristic of the shelter user is looking for
     * @return list of shelters that fits the parameters
     */
    public ArrayList<Shelter> searchChoices(String searchType, String request) {
        searchType = searchType.toLowerCase();
        request = request.toLowerCase();
        if (searchType.equals("name")) {
            return this.getByName(request);
        } else if (searchType.equals("age")) {
            return this.getByAge(request);
        } else if (searchType.equals("gender")) {
            if (request.equals("men")) {
                return this.getByMen();
            } else if (request.equals("women")) {
                return this.getByWomen();
            } else {
                throw new IllegalArgumentException("Invalid parameter");
            }
        } else {
            throw new IllegalArgumentException("This is not a valid type of search. Please choose a valid type of search.");
        }

    }

    /**
     * Looks for the shelters that fit the parameters and deletes the others which are not
     *
     * @param name the shelter's name you are looking for
     * @return List of the shelters
     */
    private ArrayList<Shelter> getByName(String name) {
        List<Shelter> shelterList = new ArrayList<>(shelters);
        ArrayList<Shelter> results = new ArrayList<>();

        // If the name is the same, add it
        for (Shelter shelter: shelterList) {
            if (shelter.getName().toLowerCase().equals(name.toLowerCase())) {
                results.add(shelter);
            }
        }
        // If everything was removed, the shelter doesn't exist
        if (shelterList.isEmpty()) {
            throw new NoSuchElementException("There is no shelter with this name. Are you sure it exists?");
        } else {
            return results;
        }
    }

    /**
     * Search for appropriate shelter using age or gender as a parameter and return list of appropriate shelters
     *
     * @param restrictions the age or gender
     * @return the list of appropriate shelters
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Shelter> getByAge(String restrictions) {
        List<Shelter> shelterList = new ArrayList(shelters);
        ArrayList<Shelter> results = new ArrayList<>();

        // Adds the shelter if it fits our requirements
        for (Shelter shelter: shelterList) {
            if (shelter.getRestrictions().toLowerCase().contains(restrictions.toLowerCase())) {
                results.add(shelter);
            }
        }

        // If it doesn't have any shelters left then it doesn't exist
        if (shelterList.isEmpty()) {
            throw new NoSuchElementException("There are no shelters that fits these parameters. Please broaden your search.");
        } else {
            return results;
        }
    }

    /**
     * Get a list of men shelters
     *
     * @return the list
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Shelter> getByMen() {
        List<Shelter> shelterList = new ArrayList(shelters);
        ArrayList<Shelter> results = new ArrayList<>();

        // Adds the shelter if it fits our requirements
        for (Shelter shelter: shelterList) {
            if (shelter.getRestrictions().contains("men") && !(shelter.getRestrictions().toLowerCase().contains("women"))) {
                results.add(shelter);
            }
        }
        // If it doesn't have any shelters left then it doesn't exist
        if (shelterList.isEmpty()) {
            throw new NoSuchElementException("There are no shelters that fit these parameters. Please broaden your search.");
        } else {
            return results;
        }
    }

    /**
     * Get list of women shelters
     *
     * @return the list
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Shelter> getByWomen() {
        List<Shelter> shelterList = new ArrayList(shelters);
        ArrayList<Shelter> results = new ArrayList<>();

        // Adds the shelter if it fits our requirements
        for (Shelter shelter: shelterList) {
            if (shelter.getRestrictions().toLowerCase().contains("women")) {
                results.add(shelter);
            }
        }

        // If it doesn't have any shelters left then it doesn't exist
        if (shelterList.isEmpty()) {
            throw new NoSuchElementException("There are no shelters that fit these parameters. Please broaden your search.");
        } else {
            return results;
        }
    }

    /**
     * Sets the filter for a search given the searchType requested and the View components. Serves to
     * move business logic out of the SearchActivity
     *
     * @param components an array of View objects of length 3. components[0] is an EditText, components[1] is a RadioButton
     *                   and components[2] is a Spinner
     * @param searchType is the type of search requested, a String
     * @return the filter calculated based on the search requested and gathering the info from the correct UI component
     */
    public String setSearchFilter(View[] components, String searchType) {
        switch (searchType) {
            case "name":
                return ((EditText)components[0]).getText().toString();
            case "gender":
                return ((RadioButton)components[1]).isChecked() ? "men" : "women";
            case "age":
                return ((Spinner)components[2]).getSelectedItem().toString();
            default:
                return ((EditText)components[0]).getText().toString();
        }
    }
}
