package edu.gatech.cs2340.hkskh.Users.Enums;

/**
 * Created by baohd on 2/24/2018.
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

    public String getAcctString(){
        return acctType;
    }

    public int getCode() {
        return code;
    }
}
