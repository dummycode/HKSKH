package edu.gatech.cs2340.hkskh.Shelters.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by baohd on 2/26/2018.
 */
@Entity(tableName = "shelters")
public class Shelter {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "capacityString")
    private String capacityString;

    @ColumnInfo(name = "capacityInd")
    private int capacityInd;

    @ColumnInfo(name = "capacityFam")
    private int capacityFam;

    @ColumnInfo(name = "restrictions")
    private String restrictions;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @ColumnInfo(name = "vacancyInd")
    private int vacancyInd;

    @ColumnInfo(name = "vacancyFam")
    private int vacancyFam;

    @Ignore
    private final int DEFAULT_CAPACITY = 0;

    /**
     *  Constructor that initiates all the data.
     *
     *  @param name the name of the shelter
     *  @param capacityString the string of capacity to break down
     *  @param restrictions the restrictions of the shelter
     *  @param longitude the longitude of the shelter
     *  @param latitude the latitude of the shelter
     *  @param address street address
     *  @param notes special considerations and such
     *  @param phoneNumber the contact info of the shelter
     */
    public Shelter(String name, String capacityString, String restrictions,
                   double longitude, double latitude, String address, String notes, String phoneNumber) {
        this.name = name;
        this.capacityString = capacityString;
        this.translateCapacity(capacityString);
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.notes = notes;
        this.phoneNumber = phoneNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacityString() {
        return this.capacityString;
    }

    public void setCapacityString(String capacityString) {
        this.capacityString = capacityString;
    }

    public int getCapacityInd() {
        return this.capacityInd;
    }

    public void setCapacityInd(int capacityInd) {
        this.capacityInd = capacityInd;
    }

    public int getCapacityFam() {
        return this.capacityFam;
    }

    public void setCapacityFam(int capacityFam) {
        this.capacityFam = capacityFam;
    }

    public String getRestrictions() {
        return this.restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getVacancyInd() {
        return this.vacancyInd;
    }

    public void setVacancyInd(int vacancyInd) {
        this.vacancyInd = vacancyInd;
    }

    public int getVacancyFam() {
        return this.vacancyFam;
    }

    public void setVacancyFam(int vacancyFam) {
        this.vacancyFam = vacancyFam;
    }


    /**
     * Translate the capacity string into family capacity and individual capacity
     */
    private void translateCapacity(String capacityString) {
        String cap1 = "";
        String cap2 = "";

        int fam = capacityString.lastIndexOf("famil");
        int a = 0;
        int first = -1;
        int second = -1;

        for (int i = 0; i < capacityString.length(); i++) {
            // the first variable will be concatenated if the number exists
            if (Character.isDigit(capacityString.charAt(i)) && a == 0) {
                if (first == -1) {
                    first = i;
                }
                cap1 = cap1 + capacityString.charAt(i);
                // if the thing is no longer a digit stop adding and sign that there is a break
            } else if (!Character.isDigit(capacityString.charAt(i)) && a == 0) {
                a++;
                // if this isn't the first number then this must be a separate number
            } else if (Character.isDigit(capacityString.charAt(i)) && a != 0) {
                if (second == -1) {
                    second = i;
                }
                cap2 = cap2 + capacityString.charAt(i);
            }
        }

        if (first == -1) {
            // No number at all, does not specify the capacity at all
            capacityInd = DEFAULT_CAPACITY;
            capacityFam = DEFAULT_CAPACITY;
        } else if (second != -1) {
            // if there are two variables and the substring famil is between them, then the first number must be family
            capacityFam = Integer.parseInt(cap1);
            capacityInd = Integer.parseInt(cap2);
        } else {
            // There's only one number
            // if family exists then it's family else it's individual
            if (fam != -1) {
                capacityFam = Integer.parseInt(cap1);
                capacityInd = DEFAULT_CAPACITY;
            } else {
                capacityFam = DEFAULT_CAPACITY;
                capacityInd = Integer.parseInt(cap1);
            }
        }

        vacancyFam = capacityFam;
        vacancyInd = capacityInd;
    }

    /**
     * Return the vacancies a digestible string
     *
     * @return the vacancy string
     */
    public String getVacancy() {
        return "Spots remaining: " + vacancyFam + " family beds, "
            + vacancyInd + " individual beds.";
    }

    /**
     * String representation of a shelter
     *
     * @return {key} : {name}
     */
    @Override
    public String toString() {
        return this.id + " : " + this.name;
    }
}
