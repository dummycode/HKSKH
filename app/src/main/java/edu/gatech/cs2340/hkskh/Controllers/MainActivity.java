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

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Controllers.SearchActivity;
import edu.gatech.cs2340.hkskh.Shelters.Controllers.ShelterListActivity;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.UserManager;

public class MainActivity extends AppCompatActivity {

    private AppDatabase mdb;

    // The widgets that form the search function and the button that goes to the full list
    private Button searchButton;
    private Button fullList;
    private Spinner searchSpinner;
    private TextView nameText;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = findViewById(R.id.outButton);

        this.mdb = AppDatabase.getDatabase(getApplicationContext());
        final UserManager userManager = new UserManager(this.mdb);

        final String username = this.getIntent().getStringExtra("Username");

        // Search button, button to shelter, and spinner
        searchButton = findViewById(R.id.button2);
        fullList = findViewById(R.id.button3);
        searchSpinner = findViewById(R.id.spinner1);

        nameText = findViewById(R.id.main_text_name);
        statusText = findViewById(R.id.main_status_text);
        User user = userManager.findByUsername(username);

        nameText.setText("Signed in as: " + username);
        if (user.isCheckedIn()) {
            statusText.setText("Currently checked in to shelter with ID " + user.getShelterId()
                    + "\n\nFamily spaces reserved: " + user.getNumFamily()
                    + "\n\nIndividual spaces reserved: " + user.getNumInd());
        } else {
            statusText.setText("Not checked in");
        }


        // Sets up the search options spinner and loads the options in.
        // Note: reminder to switch out the arrays.aslist for something that is more flexible later like enum reference
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
        // When search button is clicked, it goes to search
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passes on the type of search into the search activity
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class)
                        .putExtra("<Parameters>", (String) searchSpinner.getSelectedItem());
                myIntent.putExtra("Username", username);
                startActivity(myIntent);
            }
        });

        // When they click the button for a full List it transitions to full list
        fullList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        ShelterListActivity.class).putExtra("Username", username));
            }
        });
        }
}
