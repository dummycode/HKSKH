package edu.gatech.cs2340.hkskh.Shelters.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by baohd on 2/26/2018.
 */
@Entity(tableName = "shelters")
public class Shelter implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

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

        this.vacancyInd = capacityInd;
        this.vacancyFam = capacityFam;
    }

    public Shelter(Parcel in) {
        id = in.readInt();
        name = in.readString();
        capacityString = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Shelter> CREATOR = new Parcelable.Creator<Shelter>() {
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(capacityString);
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
}
