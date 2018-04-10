package edu.gatech.cs2340.hkskh;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.UserManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Kirby on 4/6/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserTest {

    @Mock
    private AppDatabase mockedDB;

    private UserManager userManager;

    @Before
    public void initializeUM() {
        userManager = new UserManager(this.mockedDB);
    }

    @Before
    public void initMocks() {
        this.mockedDB = mock(AppDatabase.class, Mockito.RETURNS_DEEP_STUBS);
    }

    @Test
    public void testRegisterNullFails() {
        assertEquals(false, userManager.register(null, "Kirby", UserType.USER, "pass"));
    }

    @Test
    public void testSmallUsername() {
        assertEquals(false, userManager.register("ka", "Kirby", UserType.USER, "pass"));
    }

    @Test
    public void testUserExists() {
        when(this.mockedDB.userDao().findUserByUsername("kaz")).thenReturn(getDefaultUser());
        assertEquals(false, userManager.register("kaz", "Kirby", UserType.USER, "pass"));
    }

    @Test
    public void testRegistrationSuccess() {
        when(this.mockedDB.userDao().findUserByUsername("pete")).thenReturn(null);
        assertEquals(true, userManager.register("pete", "Pete", UserType.USER, "pass"));
    }


    private User getDefaultUser(){
        return new User("kaz", UserType.USER, "pass");
    }
}
