package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

import edu.gatech.cs2340.hkskh.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the search type that the user chose on the last screen
        final String searchEntered = this.getIntent().getStringExtra("<Parameters>");

        //Set the text at top to display the previously selected search type
        TextView searchType = (TextView) findViewById(R.id.search_type_text);
        searchType.setText("Search Selected: " + searchEntered);

        final EditText name = (EditText) findViewById(R.id.search_name_edit);

        //Set up the male/female buttons
        final RadioButton female = (RadioButton) findViewById(R.id.search_female_radio);
        final RadioButton male = (RadioButton) findViewById(R.id.search_male_radio);

        final Spinner age = (Spinner) findViewById(R.id.search_age_spinner);
        //Make adapter for the spinner
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("Families", "Children", "Young Adults", "Anyone"));
        age.setAdapter(ageAdapter);

        Button search = (Button) findViewById(R.id.search_button_search);

        final Intent toFilteredList = new Intent(this, FilteredSheltersActivity.class);
        toFilteredList.putExtra("Search Type", searchEntered);


        search.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (searchEntered.equals("name")) {
                        toFilteredList.putExtra("Filter", name.getText().toString());
                        startActivity(toFilteredList);
                    } else if (searchEntered.equals("gender")) {
                        if (female.isChecked()) {
                            toFilteredList.putExtra("Filter", "women");
                            startActivity(toFilteredList);
                        } else if (male.isChecked()) {
                            toFilteredList.putExtra("Filter", "men");
                            startActivity(toFilteredList);
                        }
                    } else {
                        toFilteredList.putExtra("Filter", age.getSelectedItem().toString());
                        startActivity(toFilteredList);
                    }
                }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}