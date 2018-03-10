package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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

        //Instantiate a ShelterManager to gain access to the correct schelter
        ShelterManager shelters = new ShelterManager();
        Shelter selected;
        //Use passed hashcode from intent to gain access to correct shelter
        int shelterKey = getIntent().getIntExtra("shelter hash key", 0 );
        selected = (Shelter) (shelters.getShelter(shelterKey));

        //Instantiate textviews to display the info
        TextView name = findViewById(R.id.shelter_detail_name);
        TextView capacity = findViewById(R.id.shelter_detail_capacity);
        TextView gender = findViewById(R.id.shelter_detail_gender);
        TextView longitude = findViewById(R.id.shelter_detail_longitude);
        TextView latitude = findViewById(R.id.shelter_detail_latitude);
        TextView address = findViewById(R.id.shelter_detail_address);
        TextView phone = findViewById(R.id.shelter_detail_phone);

        //Set the textviews to show the specific info for the selected shelter
        name.setText(selected.getName());
        capacity.setText("Capacity: " + selected.getCapacityFam() + " Family rooms, " + selected.getCapacityInd()
            + " Individual rooms");
        gender.setText("Genders/Restrictions: " + selected.getRestrictions());
        longitude.setText("Longitude: " + selected.getLongitude());
        latitude.setText("Latitude: " + selected.getLatitude());
        address.setText("Address: " + selected.getAddress());
        phone.setText("Phone Number: " + selected.getPhoneNumber());

        //get info about what screen came before this
        final String previous = getIntent().getStringExtra("Previous Screen");
        final Intent filteredList = new Intent(this, FilteredSheltersActivity.class);
        //If the details page is to return to the filtered list, it needs the filter and type to generate the recyclerview
        if (getIntent().getStringExtra("Previous Screen").equals("filtered list")) {
            filteredList.putExtra("Search Type", getIntent().getStringExtra("Search Type"));
            filteredList.putExtra("Filter", getIntent().getStringExtra("Filter"));
        }

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
