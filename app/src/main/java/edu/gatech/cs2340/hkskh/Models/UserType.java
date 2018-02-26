package edu.gatech.cs2340.hkskh.Models;

/**
 * Created by baohd on 2/24/2018.
 */
public enum UserType {
    USER("User"), ADMIN("Admin");

    private String acctType;

    UserType(String type) {
        acctType = type;
    }

    public String getAcctString(){
        return acctType;
    }

}
