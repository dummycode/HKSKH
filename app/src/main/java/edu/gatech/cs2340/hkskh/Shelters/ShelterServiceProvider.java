package edu.gatech.cs2340.hkskh.Shelters;

import android.content.Context;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;

/**
 * Created by henry on 2/27/18.
 */
public class ShelterServiceProvider {

    /**
     * Load data from CSV file
     */
    public static void load(Context context) {
        // Load from CSV
        try {
            InputStream is = context.getResources().openRawResource(R.raw.shelters);
            CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(is, "UTF-8")));
            String[] row;
            reader.readNext(); // Dump header
            while ((row = reader.readNext()) != null) {
                try {
                    int key = Integer.parseInt(row[0]);
                    String name = row[1];
                    String capacityInd = row[2]; // TODO parse out individual and family
                    String restrictions = row[3];
                    double longitude = Double.parseDouble(row[4]);
                    double latitude = Double.parseDouble(row[5]);
                    String address = row[6];
                    String notes = row[7];
                    String phone = row[8];
                    ShelterManager.addShelter(new Shelter(
                            key,
                            name,
                            0,
                            0,
                            restrictions,
                            longitude,
                            latitude,
                            address,
                            notes,
                            phone
                    ));
                } catch (NumberFormatException nfe) {
                    // TODO handle errors better
                    System.out.println("Invalid CSV");
                    System.out.println(nfe.getMessage());
                }
            }
            reader.close();
        } catch (IOException e) {
            // TODO handle errors better
            System.out.println("Dang");
        }
    }

    /**
     * Clear current data and load
     */
    public static void reload(Context context) {
        ShelterManager.clear();
        load(context);
    }
}
