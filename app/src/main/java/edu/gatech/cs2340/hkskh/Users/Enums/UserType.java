package edu.gatech.cs2340.hkskh.Users.Enums;

/**
 * Created by baohd on 2/24/2018.
 * the different types of users
 */
public enum UserType {
    USER(0), ADMIN(1);

    private int code;
    private String acctType;

    UserType(String type) {
        acctType = type;
    }

    UserType(int code) {
        this.code = code;
    }

    /**
     * gets account type
     * @return account type
     */
    public String getAcctString(){
        return acctType;
    }

    /**
     * the code associated with the enum type
     * @return the id/code
     */
    public int getCode() {
        return code;
    }
}
