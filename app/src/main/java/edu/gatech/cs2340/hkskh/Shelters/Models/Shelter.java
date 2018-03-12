package edu.gatech.cs2340.hkskh.Shelters.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baohd on 2/26/2018.
 */
public class Shelter {
    private int key;
    private String name;
    private String capacityString;
    private int capacityInd;
    private int capacityFam;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String notes;
    private String phoneNumber;
    private int vacanInd;
    private int vacanFam;

    /**
     *  Constructor that initiates all the data.
     *
     *  @param key the unique id for the shelter
     *  @param name the name of the shelter
     *  @param capacityString the string of capacity to break down
     *  @param restrictions the restrictions of the shelter
     *  @param longitude the longitude of the shelter
     *  @param latitude the latitude of the shelter
     *  @param address street address
     *  @param notes special considerations and such
     *  @param phoneNumber the contact info of the shelter
     */
    public Shelter(int key, String name, String capacityString, String restrictions,
                   double longitude, double latitude, String address, String notes, String phoneNumber) {
        this.key = key;
        this.name = name;
        this.capacityString = capacityString;
        this.translateCapacity();
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.notes = notes;
        this.phoneNumber = phoneNumber;
        this.vacanFam = capacityFam;
        this.vacanInd = capacityInd;
    }

    /**
     * @return the id of the shelter. Should never be returned to the user.
     */
    public int getKey() {
        return this.key;
    }

    /**
     * @return the name of shelter
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the capacity of the shelter as a string
     */
    public String getCapacityString() {
        return this.capacityString;
    }

    /**
     * translate the capacity string into family capacity and individual capacity
     */
    private void translateCapacity() {
        String cap1 = "";
        String cap2 = "";
        String capString = this.capacityString.toLowerCase();
        int fam = capString.lastIndexOf("famil");
        int a = 0;
        int first = -1;
        int second = -1;
        for (int i = 0; i < capString.length(); i++) {
            //the first variable will be concatenated if the number exists
            if (Character.isDigit(capString.charAt(i)) && a == 0) {
                if (first == -1) {
                    first = i;
                }
                cap1 = cap1 + capString.charAt(i);
                //if the thing is no longer a digit stop adding and sign that there is a break
            } else if (!Character.isDigit(capString.charAt(i)) && a == 0) {
                a++;
                //if this isn't the first number then this must be a separate number
            } else if (Character.isDigit(capString.charAt(i)) && a != 0) {
                if (second == -1) {
                    second = i;
                }
                cap2 = cap2 + capString.charAt(i);
            }
        }
        if (first == -1) {
            //no number at all, does not specify the capacity at all
            capacityInd = 0;
            capacityFam = 0;
        } else if (second != -1) {
            //if there are two variables and the substring famil is between them, then the first number must be family
            //else the first is individual
            if (fam < second) {
                capacityFam = Integer.parseInt(cap1);
                capacityInd = Integer.parseInt(cap2);
            } else {
                capacityFam = Integer.parseInt(cap2);
                capacityInd = Integer.parseInt(cap1);
            }

        } else {
            //there's only one number
            //if family exists then it's family else it's individual
            if (fam != -1) {
                capacityFam = Integer.parseInt(cap1);
                capacityInd = 0;
            } else {
                capacityFam = 0;
                capacityInd = Integer.parseInt(cap1);
            }
        }
        vacanFam = capacityFam;
        vacanInd = capacityInd;
    }
    /**
     * @return the family room capacity
     */
    public int getCapacityFam() {
        return this.capacityFam;
    }

    /**
     * @return the individual room capacity
     */
    public int getCapacityInd() {
        return this.capacityInd;
    }

    /**
     * @return the restrictions
     */
    public String getRestrictions() {
        return this.restrictions;
    }

    /**
     * @return the longitude of the shelter
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * @return the latitude of the shelter
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * @return the address of the shelter
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * @return notes associated with shelter
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * @return phone number of shelter
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * returns family vacancies
     * @return number of vacancies
     */
    public int getVacancyFam() {
        return this.vacanFam;
    }

    /**
     * returns individual vacancies
     * @return individual vacancies
     */
    public int getVacancyInd() { return this.vacanInd; }

    /**
     * updates the vacancies by removing number of beds that are checked out
     * @param taken the amount of beds that are checked in.
     */
    public void updateVacancy(int taken) {
        if (taken > this.vacanInd) {
            taken = taken - this.vacanInd;
            this.vacanInd = 0;
            this.vacanFam = this.vacanFam - taken;
        } else {
            //takes individual spots first then family spots
            this.vacanInd = this.vacanInd - taken;
        }
    }

    /**
     * return the vacancies a digestible string
     * @return the vacancy string
     */
    public String getVacancy() { return "Spots remaining: " +this.getVacancyFam() + " family beds, "
            +this.getVacancyInd() + " individual beds.";}

    /**
     * String representation of a shelter
     *
     * @return {key} : {name}
     */
    @Override
    public String toString() {
        return this.key + " : " + this.name;
    }
}
