package edu.gatech.cs2340.hkskh.Shelters.Models;

/**
 * Created by baohd on 2/26/2018.
 */

public class Shelter {
    private int key;
    private String name;
    private int capacityInd;
    private int capacityFam;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String notes;
    private String phoneNumber;
    private int vacancies;

    /**
     *  Constructor that initiates all the data.
     *
     *  @param key the unique id for the shelter
     *  @param name the name of the shelter
     *  @param capacityFam the amount of family rooms the shelter has
     *  @param capacityInd the amount of individual space the shelter has
     *  @param restrictions the restrictions of the shelter
     *  @param longitude the longitude of the shelter
     *  @param latitude the latitude of the shelter
     *  @param address street address
     *  @param notes special considerations and such
     *  @param phoneNumber the contact info of the shelter
     */
    public Shelter(int key, String name, int capacityFam, int capacityInd, String restrictions,
                   double longitude, double latitude, String address, String notes, String phoneNumber) {
        this.key = key;
        this.name = name;
        this.capacityFam = capacityFam;
        this.capacityInd = capacityInd;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.notes = notes;
        this.phoneNumber = phoneNumber;
        this.vacancies = capacityFam + capacityInd;
    }

    /**
     *
     * @return the id of the shelter. Should never be returned to the user.
     */
    public int getKey() {
        return this.key;
    }

    /**
     *
     * @return the name of shelter
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return the family room capacity
     */
    public int getCapacityFam() {
        return this.capacityFam;
    }

    /**
     *
     * @return the individual rooms
     */
    public int getCapacityInd() {
        return this.capacityInd;
    }

    public String getRestrictions() {
        return this.restrictions;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getAddress() {
        return this.address;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getVacancies() {
        return this.vacancies;
    }

    @Override
    public String toString() {
        return this.key + " : " + this.name;
    }
}