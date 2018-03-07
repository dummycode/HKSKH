package edu.gatech.cs2340.hkskh.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Controllers.SearchActivity;
import edu.gatech.cs2340.hkskh.Shelters.Controllers.ShelterListActivity;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private Button fullList;
    private Spinner searchSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button b1 = findViewById(R.id.outButton);
        TextView text = findViewById(R.id.main_name);
        //search button, button to shelter, and spinner
        searchButton = findViewById(R.id.button2);
        fullList = findViewById(R.id.button3);
        searchSpinner = findViewById(R.id.spinner1);

        String name = this.getIntent().getStringExtra("Name");
        text.setText("Signed in as: " + name);

        //Sets up the search options spinner and loads the options in.
        //Note: reminder to switch out the arrays.aslist for something that is more flexible later like enum reference
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList("name", "age", "gender"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        });

        //WHen search button is clicked, it goes to search
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class)
                        .putExtra("<Parameters>", (String) searchSpinner.getSelectedItem());
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
                                        }
        );

        //When they click the button for a full List it transitions to full list
        fullList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShelterListActivity.class));
            }
        });
        }
}
