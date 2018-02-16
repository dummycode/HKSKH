package edu.gatech.cs2340.hkskh;

/**
 * Created by Kirby on 2/16/2018.
 */

public class User {
    private String name;
    private UserType type;
    private String pass;

    /**
     * Creates a new user
     *
     * @param name name of the user
     * @param type the user type
     * @param pass the password that is needed to give this user access
     */
    public User(String name, UserType type, String pass){
        this.name = name;
        this.type = type;
        this.pass = pass;
    }

    /**
     * No-arg constructor. Do not call this directly
     */
    public User() {
        this("user", UserType.USER, "pass");
    }

    //Getters ------------------------------------------------
    public String getName() {
        return name;
    }

    public String getPassword() {
        return pass;
    }

    public UserType getAccountType() {
        return type;
    }
    //---------------------------------------------------------
}

enum UserType {
    USER("User"), ADMIN("Admin");

    private String acctType;

    UserType(String type) {
        acctType = type;
    }

    public String getAcctString(){
        return acctType;
    }

}
