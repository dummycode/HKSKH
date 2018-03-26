package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import edu.gatech.cs2340.hkskh.Controllers.MainActivity;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;


public class ShelterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText amount = findViewById(R.id.editText2);
        //set up the checkout button
        Button checkin = findViewById(R.id.button4);
        Button checkOut = findViewById(R.id.button5);
        //Instantiate a ShelterManager to gain access to the correct schelter
        ShelterManager shelters = new ShelterManager();
        final Shelter selected;
        //Use passed hashcode from intent to gain access to correct shelter
        int shelterKey = getIntent().getIntExtra("shelter hash key", 0 );
        selected = (Shelter) (shelters.getShelter(shelterKey));

        //final String userName = this.getIntent().getStringExtra("Username");
        //Instantiate a UserManager and manage the check in and check out
        final edu.gatech.cs2340.hkskh.Users.UserManager users = new edu.gatech.cs2340.hkskh.Users.UserManager();
        final String userName = users.getCurrUserName();

        //initialize spinner
        final Spinner vacanSpinner = findViewById(R.id.spinner);

        //Sets up the search options spinner and loads the options in.
        //Note: reminder to switch out the arrays.aslist for something that is more flexible later like enum reference
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList("family", "individuals"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacanSpinner.setAdapter(adapter);

        //Instantiate textviews to display the info
        TextView name = findViewById(R.id.shelter_detail_name);
        TextView capacity = findViewById(R.id.shelter_detail_capacity);
        TextView gender = findViewById(R.id.shelter_detail_gender);
        TextView longitude = findViewById(R.id.shelter_detail_longitude);
        TextView latitude = findViewById(R.id.shelter_detail_latitude);
        TextView address = findViewById(R.id.shelter_detail_address);
        TextView phone = findViewById(R.id.shelter_detail_phone);
        final TextView vacancies = findViewById(R.id.Shelter_Detail_Vacancies);

        //Set the textviews to show the specific info for the selected shelter
        name.setText(selected.getName());
        capacity.setText("Capacity: " + selected.getCapacityFam() + " Family rooms, " + selected.getCapacityInd()
            + " Individual rooms");
        gender.setText("Genders/Restrictions: " + selected.getRestrictions());
        longitude.setText("Longitude: " + selected.getLongitude());
        latitude.setText("Latitude: " + selected.getLatitude());
        address.setText("Address: " + selected.getAddress());
        phone.setText("Phone Number: " + selected.getPhoneNumber());
        vacancies.setText(selected.getVacancy());

        //get info about what screen came before this
        final String previous = getIntent().getStringExtra("Previous Screen");
        final Intent filteredList = new Intent(this, FilteredSheltersActivity.class);
        //If the details page is to return to the filtered list, it needs the filter and type to generate the recyclerview
        if (getIntent().getStringExtra("Previous Screen").equals("filtered list")) {
            filteredList.putExtra("Search Type", getIntent().getStringExtra("Search Type"));
            filteredList.putExtra("Filter", getIntent().getStringExtra("Filter"));
        }

        //checkin button updates
        checkin.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        String roomChoice = vacanSpinner.getSelectedItem().toString();
                        if (amount.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter a " +
                                    "number for how many beds you wish to check in", Toast.LENGTH_LONG).show();
                        } else if (Integer.parseInt(amount.getText().toString()) <= 0) {
                            Toast.makeText(getApplicationContext(), "Warning: Cannot enter negative or zero beds. " +
                                    "Please select a minimum of one bed.", Toast.LENGTH_LONG).show();
                        } else if (Integer.parseInt(amount.getText().toString()) > (selected.getVacancyInd()) &&
                                roomChoice.equals("individuals")) {
                            Toast.makeText(getApplicationContext(), "You cannot select more beds than there exist. " +
                                    "Please choose number less than the vacancies", Toast.LENGTH_LONG).show();
                        } else if (Integer.parseInt(amount.getText().toString()) > (selected.getVacancyFam()) &&
                                roomChoice.equals("family")){
                            Toast.makeText(getApplicationContext(), "You cannot select more beds than there exist. " +
                                    "Please choose number less than the vacancies", Toast.LENGTH_LONG).show();
                        } else if (users.getShelterId(userName) != selected.getKey() && users.getShelterId(userName) != -1){
                            Toast.makeText(getApplicationContext(), "You cannot select this shelter " +
                                    "because you are already checked in at another shelter. Please choose the shelter you are checked into.", Toast.LENGTH_LONG).show();
                        } else {
                            selected.updateVacancy(Integer.parseInt(amount.getText().toString()),
                                    true, roomChoice.equals("family"));
                            vacancies.setText(selected.getVacancy());
                            users.checkIn(userName, selected.getKey(), Integer.parseInt(amount.getText().toString()),
                                    roomChoice.equals("family"));
                        }
                    }
                }
        );
        //checkout button updates vacancies
        checkOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String roomChoice = vacanSpinner.getSelectedItem().toString();
                if (amount.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a " +
                            "number for how many beds you wish to check out", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(amount.getText().toString()) <= 0) {
                    Toast.makeText(getApplicationContext(), "Warning: Cannot enter negative or zero beds. " +
                            "Please select a minimum of one bed.", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(amount.getText().toString()) > users.getNumBeds(userName, false) &&
                        roomChoice.equals("individuals")) {
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than you checked out. " +
                            "Please choose number less than the vacancies", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(amount.getText().toString()) > users.getNumBeds(userName, true) &&
                        roomChoice.equals("family")){
                    Toast.makeText(getApplicationContext(), "You cannot select more beds than you've checked out. " +
                            "Please choose number less than the vacancies", Toast.LENGTH_LONG).show();
                } else if (users.getShelterId(userName) != selected.getKey()){
                    Toast.makeText(getApplicationContext(), "You cannot select this shelter " +
                            "because you are already checked in at another shelter. Please choose the shelter you are checked into.", Toast.LENGTH_LONG).show();
                } else {
                    selected.updateVacancy(Integer.parseInt(amount.getText().toString()),
                                false, roomChoice.equals("family"));
                    vacancies.setText(selected.getVacancy());
                    users.checkOut(userName, Integer.parseInt(amount.getText().toString()),
                            roomChoice.equals("family"));
                }
            }
        }
        );
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previous.equals("full list")) {
                    startActivity(new Intent(view.getContext(), ShelterListActivity.class).putExtra("Username", userName));
                } else {
                    startActivity(filteredList);
                }
            }
        });


    }

}
