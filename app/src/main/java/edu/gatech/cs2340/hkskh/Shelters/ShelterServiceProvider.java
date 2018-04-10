package edu.gatech.cs2340.hkskh.Shelters;

import android.content.Context;
import android.content.res.Resources;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R.raw;
import edu.gatech.cs2340.hkskh.Shelters.DAOs.ShelterDao;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;

/**
 * Created by henry on 2/27/18.
 * Loads in the shelters from csv sheet
 * and stores it in shelter list
 */
public class ShelterServiceProvider {

    Context context;
    AppDatabase adb;

    public ShelterServiceProvider(Context context, AppDatabase adb) {
        this.context = context;
        this.adb = adb;
    }

    /**
     * Load data from CSV file
     */
    public void load() {
        ShelterManager shelterManager = new ShelterManager(adb);
        ShelterDao shelterDao = adb.shelterDao();

        if (shelterDao.count() == 0) { // Load from CSV
            try {
                Resources resources = context.getResources();
                InputStream is = resources.openRawResource(raw.shelters);
                CSVReader reader = new CSVReader(
                        new BufferedReader(new InputStreamReader(is, "UTF-8")));
                String[] row;
                reader.readNext(); // Dump header
                while ((row = reader.readNext()) != null) {
                    try {
                        String name = row[1];
                        int capacityInd = Integer.parseInt(row[2]);
                        int capacityFamily = Integer.parseInt(row[3]);
                        String restrictions = row[4];
                        double longitude = Double.parseDouble(row[5]);
                        double latitude = Double.parseDouble(row[6]);
                        String address = row[7];
                        String notes = row[8];
                        String phone = row[9];
                        shelterManager.addShelter(new Shelter(
                                name,
                                capacityInd,
                                capacityFamily,
                                restrictions,
                                longitude,
                                latitude,
                                address,
                                notes,
                                phone
                        ));
                    } catch (NumberFormatException nfe) {
                        //Clears the shelters that have been added so far
                        shelterManager.clear();
                    }
                }
                reader.close();
            } catch (IOException e) {
                //Clears the shelter list if not able to completely scan the file
                shelterManager.clear();
            }
        }
    }

    /**
     * Clear current data and load
     */
    public void reload() {
        ShelterManager shelterManager = new ShelterManager(adb);
        shelterManager.clear();
        load();
    }
}
