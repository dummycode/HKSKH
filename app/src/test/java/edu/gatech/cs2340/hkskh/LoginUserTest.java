package edu.gatech.cs2340.hkskh;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Shelters.DAOs.ShelterDao;
import edu.gatech.cs2340.hkskh.Users.DAOs.UserDao;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.UserManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
/**
 * created by Kevin on 4/8/2018
 */
public class LoginUserTest {

    @Mock
    private AppDatabase mockDB;
    private UserManager userManager;

    @Mock
    private User mockUser;

    @Before
    public void initUserManager() {
        userManager = new UserManager(this.mockDB);
    }
    @Before
    public void initMocks() {
        this.mockDB = mock(AppDatabase.class, Mockito.RETURNS_DEEP_STUBS);
    }

    @Test
    public void loginCorrectUser() {
        when(this.mockDB.userDao().findUserByUsername("kjin42")).thenReturn(mockUser);
        when(this.mockUser.getPass()).thenReturn("qwerty");

        //this.userManager.register("kjin42", "Kevin", UserType.USER, "qwerty");
        boolean result = this.userManager.login("kjin42", "qwerty");
        assertEquals(true, result);
    }

    @Test
    public void loginWrongPass() {
        when(this.mockDB.userDao().findUserByUsername("kjin42")).thenReturn(mockUser);
        when(this.mockUser.getPass()).thenReturn("qwerty");

        //this.userManager.register("kjin42", "Kevin", UserType.USER, "qwerty");
        boolean result = this.userManager.login("kjin42", "wrongPass");
        assertEquals(false, result);
    }

    @Test
    public void loginNullUser() {
        when(this.mockDB.userDao().findUserByUsername("kjin42")).thenReturn(mockUser);
        //when(this.mockUser.getPass()).thenReturn("qwerty");
        boolean result = this.userManager.login(null, "qwerty");
        assertEquals(false, result);
    }

    @Test
    public void loginNullPass() {
        when(this.mockDB.userDao().findUserByUsername("kjin42")).thenReturn(mockUser);
        //when(this.mockUser.getPass()).thenReturn("qwerty");
        boolean result = this.userManager.login("kjin42", null);
        assertEquals(false, result);
    }

    @Test
    public void loginNoUser() {
        when(this.mockDB.userDao().findUserByUsername("kjin42")).thenReturn(mockUser);
        when(this.mockDB.userDao().findUserByUsername("kjin")).thenReturn(null);
        //when(this.mockUser.getPass()).thenReturn("qwerty");
        boolean result = this.userManager.login("kjin", "qwerty");
        assertEquals(false, result);
    }






    private User getDefaultUser() {
        return new User("kjin42", UserType.USER, "qwerty");

    }
}