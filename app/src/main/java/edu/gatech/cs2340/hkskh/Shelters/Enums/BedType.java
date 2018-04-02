package edu.gatech.cs2340.hkskh.Shelters.Enums;

/**
 * Created by henry on 2/24/2018.
 */
public enum BedType {
    INDIVIDUAL(0), FAMILY(1);

    private int code;
    private String bedType;

    BedType(int code) {
        this.code = code;
    }

    public String getBedType(){
        return bedType;
    }
}
