package edu.gatech.cs2340.hkskh.Users.Models;

import org.junit.Test;

import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;

import static org.junit.Assert.*;

/**
 * Created by baohd on 4/2/2018.
 * tests user
 */
public class UserTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() throws Exception {
        BedType bedFam = BedType.FAMILY;
        BedType bedInd = BedType.INDIVIDUAL;
        User homeless1 = new User("hbao31", UserType.USER, "123");
        //This should test that it updates the individual beds
        homeless1.updateBeds(bedInd, -12);
    }
    @Test
    public void testIndividualUpdate() throws Exception {
        BedType bedFam = BedType.FAMILY;
        BedType bedInd = BedType.INDIVIDUAL;
        User homeless1 = new User("hbao31", UserType.USER, "123");
        //This should test that it updates the individual beds
        homeless1.updateBeds(bedInd, 11);
        assertEquals(11, homeless1.getNumBeds(bedInd));
    }

    @Test
    public void testFamilyUpdate() throws Exception {
        BedType bedFam = BedType.FAMILY;
        BedType bedInd = BedType.INDIVIDUAL;
        User homeless1 = new User("hbao31", UserType.USER, "123");
        //checks family beds are updated
        homeless1.updateBeds(bedFam, 6);
        assertEquals(6, homeless1.getNumBeds(bedFam));
    }

}