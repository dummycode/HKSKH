package edu.gatech.cs2340.hkskh.Users.Converters;

import android.arch.persistence.room.TypeConverter;

import edu.gatech.cs2340.hkskh.Users.Enums.UserType;

import static edu.gatech.cs2340.hkskh.Users.Enums.UserType.ADMIN;
import static edu.gatech.cs2340.hkskh.Users.Enums.UserType.USER;

/**
 * Created by henry on 3/14/18.
 * converts an int into a usertype
 */
public class UserTypeConverter {

    /**
     *
     * @param userType converts the userType
     */
    @TypeConverter
    @SuppressWarnings("unused")
    public static UserType toUserType(int userType) {
        if (userType == USER.getCode()) {
            return USER;
        } else if (userType == ADMIN.getCode()) {
            return ADMIN;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    @SuppressWarnings("unused")
    /**
     * converts userType to integer code
     * @param userType the user type
     * @return the code of the user type
     */
    public static int toInteger(UserType userType) {
        return userType.getCode();
    }
}
