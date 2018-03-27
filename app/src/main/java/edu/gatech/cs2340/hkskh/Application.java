package edu.gatech.cs2340.hkskh;


/**
 * Created by henry on 3/26/18.
 */
public class Application extends android.app.Application {

    private int currentUserId;

    public int getCurrentUserId()
    {
        return currentUserId;
    }

    public void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

}
