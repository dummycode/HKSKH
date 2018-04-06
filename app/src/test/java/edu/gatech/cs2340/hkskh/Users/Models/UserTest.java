package edu.gatech.cs2340.hkskh.Users.Models;

import org.junit.Test;

import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;

import static org.junit.Assert.*;

/**
 * Created by baohd on 4/2/2018.
 */
public class UserTest {
    @Test
    public void updateBeds() throws Exception {
        //written by hyatt bao
        BedType bedFam = BedType.FAMILY;
        BedType bedInd = BedType.INDIVIDUAL;
        User homeless1 = new User("hbao31", UserType.USER, "123");
        //This should test that it updates the individual beds
        homeless1.updateBeds(bedInd, 11);
        assertEquals(11, homeless1.getNumBeds(bedInd));
        //checks family beds are updated
        homeless1.updateBeds(bedFam, 6);
        assertEquals(6, homeless1.getNumBeds(bedFam));
    }

}