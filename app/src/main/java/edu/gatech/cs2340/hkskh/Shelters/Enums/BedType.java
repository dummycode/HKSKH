package edu.gatech.cs2340.hkskh.Shelters.Enums;

/**
 * Created by henry on 2/24/2018.
 */
public enum BedType {
    INDIVIDUAL(0), FAMILY(1);

    private String bedType;

    BedType(int code) {
        switch (code) {
            case 0:
                bedType = "individual";
                break;
            case 1:
                bedType = "family";
                break;
        }
    }

    /**
     * Get the string of a bed type
     *
     * @return the bed type
     */
    public String getBedType(){
        return bedType;
    }
}
