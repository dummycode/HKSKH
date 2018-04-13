package edu.gatech.cs2340.hkskh.Shelters.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by baohd on 2/26/2018.
 * Shelter is a model storing all the information
 * needed for shelter
 */
@Entity(tableName = "shelters")
public class Shelter implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "capacityInd")
    private final int capacityInd;

    @ColumnInfo(name = "capacityFam")
    private final int capacityFam;

    @ColumnInfo(name = "restrictions")
    private final String restrictions;

    @ColumnInfo(name = "longitude")
    private final double longitude;

    @ColumnInfo(name = "latitude")
    private final double latitude;

    @ColumnInfo(name = "address")
    private final String address;

    @ColumnInfo(name = "notes")
    private final String notes;

    @ColumnInfo(name = "phoneNumber")
    private final String phoneNumber;

    @ColumnInfo(name = "vacancyInd")
    private int vacancyInd;

    @ColumnInfo(name = "vacancyFam")
    private int vacancyFam;

    /**
     *  Constructor that initiates all the data.
     *
     *  @param name the name of the shelter
     *  @param capacityInd the individual capacity of the shelter
     *  @param capacityFam the family capacity of the shelter
     *  @param restrictions the restrictions of the shelter
     *  @param longitude the longitude of the shelter
     *  @param latitude the latitude of the shelter
     *  @param address street address
     *  @param notes special considerations and such
     *  @param phoneNumber the contact info of the shelter
     */
    public Shelter(
            String name,
            int capacityInd,
            int capacityFam,
            String restrictions,
            double longitude,
            double latitude,
            String address,
            String notes,
            String phoneNumber
    ) {
        this.name = name;
        this.capacityInd = capacityInd;
        this.capacityFam = capacityFam;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.notes = notes;
        this.phoneNumber = phoneNumber;

        vacancyInd = capacityInd;
        vacancyFam = capacityFam;
    }

    /**
     * allows for shelter initialization
     * @param in the parcel that contains all the info
     */
    public Shelter(Parcel in) {
        id = in.readInt();
        name = in.readString();
        capacityInd = in.readInt();
        capacityFam = in.readInt();
        restrictions = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        address = in.readString();
        notes = in.readString();
        phoneNumber = in.readString();
        vacancyInd = in.readInt();
        vacancyFam = in.readInt();
    }

    /**
     * returns shelter id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * sets shelter id
     * @param id the id of the shelter
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns name of shelter
     * @return shelter name
     */
    public String getName() {
        return name;
    }


    /**
     * gets individual capacity
     * @return individual capacity
     */
    public int getCapacityInd() {
        return capacityInd;
    }

    /**
     * gets family capacity
     * @return family capacity
     */
    public int getCapacityFam() {
        return capacityFam;
    }

    /**
     * gets restrictions of shelter
     * @return restrictions
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     *
     * @return longitude of shelter
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * @return latitude of shelter
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return address of shelter
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @return special notes about shelter
     */
    public String getNotes() {
        return notes;
    }



    /**
     *
     * @return phone number of shelter
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * returns vacancies (individual)
     * @return individual spots
     */
    public int getVacancyInd() {
        return vacancyInd;
    }

    /**
     * sets individual vacancies
     * @param vacancyInd individual vacancies
     */
    public void setVacancyInd(int vacancyInd) {
        this.vacancyInd = vacancyInd;
    }

    /**
     * gets vacancy of family spots
     * @return family spots
     */
    public int getVacancyFam() {
        return vacancyFam;
    }

    /**
     * sets vacancy of family spots
     * @param vacancyFam how many family spots remain
     */
    public void setVacancyFam(int vacancyFam) {
        this.vacancyFam = vacancyFam;
    }


    /**
     * Return the vacancies a digestible string
     *
     * @return the vacancy string
     */
    public CharSequence getVacancy() {
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
        return id + " : " + name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Shelter> CREATOR = new Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(capacityInd);
        dest.writeInt(capacityFam);
        dest.writeString(restrictions);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(address);
        dest.writeString(notes);
        dest.writeString(phoneNumber);
        dest.writeInt(vacancyInd);
        dest.writeInt(vacancyFam);
    }

    /**
     * returns name + phone number for map info
     * @return shelter info for map
     */
    public String getMapTitle() {
        return name + " - " + phoneNumber;
    }
}
