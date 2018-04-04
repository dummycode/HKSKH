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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import edu.gatech.cs2340.hkskh.Controllers.MainActivity;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.SearchService;

public class SearchActivity extends AppCompatActivity {

    private AppDatabase adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.adb = AppDatabase.getDatabase(getApplicationContext());

        // Get the search type that the user chose on the last screen
        final String searchEntered = this.getIntent().getStringExtra("<Parameters>");

        // Set the text at top to display the previously selected search type
        TextView searchType = findViewById(R.id.search_type_text);
        searchType.setText("Search Selected: " + searchEntered);

        final EditText name = findViewById(R.id.search_name_edit);

        // Set up the male/female buttons
        final RadioButton femaleButton = findViewById(R.id.search_female_radio);
        femaleButton.setChecked(true);
        final RadioButton maleButton = findViewById(R.id.search_male_radio);

        final Spinner ageSpinner = findViewById(R.id.search_age_spinner);

        // Make adapter for the spinner
        SpinnerAdapter ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("Families", "Children", "Young Adults", "Anyone"));
        ageSpinner.setAdapter(ageAdapter);

        Button searchButton = findViewById(R.id.search_button_search);

        final Intent toFilteredList = new Intent(SearchActivity.this, ShelterListActivity.class);
        // Pass the search type as an extra to the next screen so we can make the list to display
        toFilteredList.putExtra("Search Type", searchEntered);

        // Make sure to pass the filter to the next screen as an extra because we need it to generate the list
        searchButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ArrayList<Shelter> shelters;
                    View[] components = {name, maleButton, ageSpinner};
                    String filter;
                    SearchService searchService = new SearchService(adb);

                    filter = searchService.setSearchFilter(components, searchEntered);

                    // Put in try-catch because the search methods throw exceptions
                    try {
                        shelters = searchService.searchChoices(searchEntered, filter);
                    } catch (IllegalArgumentException exception){
                        shelters = new ArrayList<>();
                    } catch (NoSuchElementException exception) {
                        shelters = new ArrayList<>();
                    }

                    // Pass the search type as an extra to the next screen so we can make the list to display
                    toFilteredList.putParcelableArrayListExtra("shelters", shelters);

                    startActivity(toFilteredList);
                }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });
    }

}
