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

import java.util.ArrayList;
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


    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());
        Application state = (Application) getApplication();


        // Instantiate a UserManager and manage the check in and check out
        final UserManager userManager = new UserManager(adb);
        // Instantiate a ShelterManager to gain access to the correct shelter
        final ShelterManager shelterManager = new ShelterManager(adb);

        setContentView(R.layout.activity_shelter_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        final Spinner vacancySpinner = findViewById(R.id.spinner);

        // Sets up the search options spinner and loads the options in.
        // Note: reminder to switch out the arrays.asList for something that is more flexible later like enum reference
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList("family", "individuals"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacancySpinner.setAdapter(adapter);

        // Instantiate TextViews to display the info
        TextView name = findViewById(R.id.shelter_detail_name);
        TextView capacity = findViewById(R.id.shelter_detail_capacity);
        TextView gender = findViewById(R.id.shelter_detail_gender);
        TextView longitude = findViewById(R.id.shelter_detail_longitude);
        TextView latitude = findViewById(R.id.shelter_detail_latitude);
        TextView address = findViewById(R.id.shelter_detail_address);
        TextView phone = findViewById(R.id.shelter_detail_phone);
        final TextView vacancies = findViewById(R.id.Shelter_Detail_Vacancies);

        // Set the TextViews to show the specific info for the selected shelter
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

        // checkIn button updates
        checkIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int selectedShelterId = selected.getId();
                String roomChoice = vacancySpinner.getSelectedItem().toString();

                BedType bedType;
                if ("family".equals(roomChoice)) {
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
                } else if ((count > (selected.getVacancyInd())) && (bedType == BedType.INDIVIDUAL)) {
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than there exist.", Toast.LENGTH_LONG).show();
                } else if ((count > (selected.getVacancyFam())) && (bedType == BedType.FAMILY)){
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than there exist.", Toast.LENGTH_LONG).show();
                } else if ((shelterId != selectedShelterId) && (shelterId != -1)){
                    String currentName = shelterManager.findById(shelterId).getName();
                    Toast.makeText(getApplicationContext(), "You are already checked into " + currentName, Toast.LENGTH_LONG).show();
                } else {
                    shelterManager.updateVacancy(selected, bedType, -count);
                    userManager.checkIn(user, selectedShelterId, count, bedType);

                    vacancies.setText(selected.getVacancy());

                    Toast.makeText(getApplicationContext(), "Checked into " + count + " " + bedType.getBedType() + " bed(s)", Toast.LENGTH_LONG).show();
                }
            }
        });

        // checkOut button updates vacancies
        checkOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int selectedShelterId = selected.getId();
                String roomChoice = vacancySpinner.getSelectedItem().toString();

                BedType bedType;
                if ("family".equals(roomChoice)) {
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
                } else if (count > user.getNumBeds(BedType.INDIVIDUAL) && (bedType == BedType.INDIVIDUAL)) {
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than you checked out.", Toast.LENGTH_LONG).show();
                } else if (count > user.getNumBeds(BedType.FAMILY) && (bedType == BedType.FAMILY)) {
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than you've checked out.", Toast.LENGTH_LONG).show();
                } else if (user.getShelterId() != selectedShelterId){
                    String currentName = shelterManager.findById(user.getShelterId()).getName();
                    Toast.makeText(getApplicationContext(), "You are already checked into " + currentName, Toast.LENGTH_LONG).show();
                } else {
                    shelterManager.updateVacancy(selected, bedType, count);
                    userManager.checkOut(user, count, bedType);

                    vacancies.setText(selected.getVacancy());

                    Toast.makeText(getApplicationContext(), "Checked out of " + count + " " + bedType.getBedType() + " bed(s)", Toast.LENGTH_LONG).show();
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(view.getContext(), ShelterListActivity.class);

                // Put the full list back into the intent
                ArrayList<Shelter> shelters = getIntent().getParcelableArrayListExtra("shelters");
                back.putParcelableArrayListExtra("shelters", shelters);

                startActivity(back);
            }
        });


    }
}
