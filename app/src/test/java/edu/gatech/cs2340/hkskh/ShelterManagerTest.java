package edu.gatech.cs2340.hkskh;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;


import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
/**
 * Created by Steven on 4/16/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ShelterManagerTest {
    private AppDatabase mockedDb;

    private ShelterManager shelterManager;

    @Before
    public void initializeUM() {
        shelterManager = new ShelterManager(this.mockedDb);
    }

    @Before
    public void initMocks() {
        this.mockedDb = mock(AppDatabase.class, Mockito.RETURNS_DEEP_STUBS);
    }

    @Test
    public void testUpdateNullVacancy(){
        Shelter shelter = null;
        shelterManager.updateVacancy(shelter, BedType.INDIVIDUAL, 1);
        assertNull(shelter);
    }
    @Test
    public void testUpdateIndividualVacancy(){
        Shelter shelter = new Shelter("name", 10, 20, "Male", 10.50, 20.50, "555 Fake Street", "Notes", "(555)555-5555");
        shelterManager.updateVacancy(shelter, BedType.INDIVIDUAL, -5);
        assertEquals(5, shelter.getVacancyInd());
    }
    @Test
    public void testUpdateFamilyVacancy() {
        Shelter shelter = new Shelter("name", 10, 20, "Male", 10.50, 20.50, "555 Fake Street", "Notes", "(555)555-5555");
        shelterManager.updateVacancy(shelter, BedType.FAMILY, -5);
        assertEquals(15, shelter.getVacancyFam());
    }
}
