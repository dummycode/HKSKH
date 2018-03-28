package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import edu.gatech.cs2340.hkskh.Application;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Enums.BedType;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.UserManager;

/**
 * Created by henry on 3/26/18.
 */
public class ShelterDetailActivity extends AppCompatActivity {

    private AppDatabase adb;
    private Application state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adb = AppDatabase.getDatabase(getApplicationContext());
        this.state = (Application) getApplication();


        // Instantiate a UserManager and manage the check in and check out
        final UserManager userManager = new UserManager(this.adb);
        // Instantiate a ShelterManager to gain access to the correct schelter
        final ShelterManager shelterManager = new ShelterManager(this.adb);

        setContentView(R.layout.activity_shelter_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText amount = findViewById(R.id.editText2);

        // Set up the check in/out buttons
        Button checkIn = findViewById(R.id.button4);
        Button checkOut = findViewById(R.id.button5);

        // Use passed id from intent to select correct shelter
        final int shelterKey = getIntent().getIntExtra("shelterId", 0 );
        final Shelter selected = shelterManager.findById(shelterKey);

        // Get user from state
        final int userId = state.getCurrentUserId();
        final User user = userManager.findById(userId);

        // Initialize spinner
        final Spinner vacanSpinner = findViewById(R.id.spinner);

        // Sets up the search options spinner and loads the options in.
        // Note: reminder to switch out the arrays.aslist for something that is more flexible later like enum reference
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList("family", "individuals"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacanSpinner.setAdapter(adapter);

        // Instantiate textviews to display the info
        TextView name = findViewById(R.id.shelter_detail_name);
        TextView capacity = findViewById(R.id.shelter_detail_capacity);
        TextView gender = findViewById(R.id.shelter_detail_gender);
        TextView longitude = findViewById(R.id.shelter_detail_longitude);
        TextView latitude = findViewById(R.id.shelter_detail_latitude);
        TextView address = findViewById(R.id.shelter_detail_address);
        TextView phone = findViewById(R.id.shelter_detail_phone);
        final TextView vacancies = findViewById(R.id.Shelter_Detail_Vacancies);

        // Set the textviews to show the specific info for the selected shelter
        name.setText(selected.getName());
        capacity.setText("Capacity: " + selected.getCapacityFam() + " family rooms, " + selected.getCapacityInd()
            + " individual rooms");
        gender.setText("Genders/Restrictions: " + selected.getRestrictions());
        longitude.setText("Longitude: " + selected.getLongitude());
        latitude.setText("Latitude: " + selected.getLatitude());
        address.setText("Address: " + selected.getAddress());
        phone.setText("Phone Number: " + selected.getPhoneNumber());
        vacancies.setText(selected.getVacancy());

        // Get info about what screen came before this
        final String previous = getIntent().getStringExtra("Previous Screen");
        final Intent filteredList = new Intent(this, FilteredSheltersActivity.class);

        // If the details page is to return to the filtered list, it needs the filter and type to generate the recyclerview
        if (getIntent().getStringExtra("Previous Screen").equals("filtered list")) {
            filteredList.putExtra("Search Type", getIntent().getStringExtra("Search Type"));
            filteredList.putExtra("Filter", getIntent().getStringExtra("Filter"));
        }

        // checkIn button updates
        checkIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int selectedShelterId = selected.getId();
                String roomChoice = vacanSpinner.getSelectedItem().toString();

                BedType bedType;
                if (roomChoice.equals("family")) {
                    bedType = BedType.FAMILY;
                } else {
                    bedType = BedType.INDIVIDUAL;
                }

                int count;

                try {
                    count = Integer.parseInt(amount.getText().toString());
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_LONG).show();
                    return;
                }

                int shelterId = user.getShelterId();

                if (count <= 0) {
                    Toast.makeText(getApplicationContext(), "Please select a minimum of one bed.", Toast.LENGTH_LONG).show();
                } else if (count > (selected.getVacancyInd()) && bedType == BedType.INDIVIDUAL) {
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than there exist.", Toast.LENGTH_LONG).show();
                } else if (count > (selected.getVacancyFam()) && bedType == BedType.FAMILY){
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than there exist.", Toast.LENGTH_LONG).show();
                } else if (shelterId != selectedShelterId && shelterId != -1){
                    String currentName = shelterManager.findById(shelterId).getName();
                    Toast.makeText(getApplicationContext(), "You are already checked into " + currentName, Toast.LENGTH_LONG).show();
                } else {
                    shelterManager.updateVacancy(selected, bedType, -count);
                    userManager.checkIn(user, selectedShelterId, count, bedType);

                    vacancies.setText(selected.getVacancy());

                    Toast.makeText(getApplicationContext(), "Checked into " + count + " " + bedType + " beds", Toast.LENGTH_LONG).show();
                }
            }
        });

        // checkOut button updates vacancies
        checkOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int selectedShelterId = selected.getId();
                String roomChoice = vacanSpinner.getSelectedItem().toString();

                BedType bedType;
                if (roomChoice.equals("family")) {
                    bedType = BedType.FAMILY;
                } else {
                    bedType = BedType.INDIVIDUAL;
                }

                int count;

                try {
                    count = Integer.parseInt(amount.getText().toString());
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (count <= 0) {
                    Toast.makeText(getApplicationContext(), "Please select a minimum of one bed.", Toast.LENGTH_LONG).show();
                } else if (count > user.getNumBeds(BedType.INDIVIDUAL) && bedType == BedType.INDIVIDUAL) {
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than you checked out.", Toast.LENGTH_LONG).show();
                } else if (count > user.getNumBeds(BedType.FAMILY) && bedType == BedType.FAMILY) {
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than you've checked out.", Toast.LENGTH_LONG).show();
                } else if (user.getShelterId() != selectedShelterId){
                    String currentName = shelterManager.findById(user.getShelterId()).getName();
                    Toast.makeText(getApplicationContext(), "You are already checked into " + currentName, Toast.LENGTH_LONG).show();
                } else {
                    shelterManager.updateVacancy(selected, bedType, count);
                    userManager.checkOut(user, count, bedType);

                    vacancies.setText(selected.getVacancy());

                    Toast.makeText(getApplicationContext(), "Checked out of " + count + " " + bedType + " beds", Toast.LENGTH_LONG).show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previous.equals("full list")) {
                    startActivity(new Intent(view.getContext(), ShelterListActivity.class));
                } else {
                    startActivity(filteredList);
                }
            }
        });


    }
}
