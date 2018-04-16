package edu.gatech.cs2340.hkskh.Users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;
import edu.gatech.cs2340.hkskh.Users.Models.User;


import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

/**
 * Created by henry on 4/9/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    @Mock
    private AppDatabase mockedDb;

    private UserManager userManager;

    @Before
    public void initializeUM() {
        userManager = new UserManager(this.mockedDb);
    }

    @Before
    public void initMocks() {
        this.mockedDb = mock(AppDatabase.class, Mockito.RETURNS_DEEP_STUBS);
    }

    @Test
    public void checkOutNullUser() {
        // Nothing should happen when passed a null user
        User user = null;
        User otherUser = new User("henry", UserType.USER, "pass");
        otherUser.setShelterId(1);
        otherUser.setNumFamily(5);
        otherUser.setNumInd(10);

        userManager.checkOut(null, 0, BedType.FAMILY);

        assertEquals(7, otherUser.getNumInd());
        assertEquals(3, otherUser.getNumFamily());
    }

    @Test
    public void checkOut() {
        User user = new User("john", UserType.USER, "pass");
        user.setShelterId(1);
        user.setNumFamily(5);
        user.setNumInd(10);
        
        userManager.checkOut(user, 2, BedType.FAMILY);
        assertEquals(3, user.getNumFamily());
        userManager.checkOut(user, 3, BedType.INDIVIDUAL);
        assertEquals(7, user.getNumInd());
        assertEquals(3, user.getNumFamily());
    }

    @Test
    public void checkOutZeroNow() {
        User user = new User("john", UserType.USER, "pass");
        user.setShelterId(1);
        user.setNumFamily(5);
        user.setNumInd(10);

        // Fully checkout of both
        userManager.checkOut(user, 5, BedType.FAMILY);
        userManager.checkOut(user, 10, BedType.INDIVIDUAL);

        assertEquals(0, user.getNumInd());
        assertEquals(0, user.getNumFamily());
        assertEquals(-1, user.getShelterId());
    }

}