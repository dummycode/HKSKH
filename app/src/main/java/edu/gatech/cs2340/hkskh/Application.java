package edu.gatech.cs2340.hkskh;


/**
 * Created by henry on 3/26/18.
 */
public class Application extends android.app.Application {

    private int currentUserId;
    
    /**
     * Get the current logged in user id
     *
     * @return user id
     */
    public int getCurrentUserId()
    {
        return currentUserId;
    }

    /**
     * Set the current user id
     *
     * @param userId the id
     */
    public void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

}
