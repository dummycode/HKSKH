package edu.gatech.cs2340.hkskh.Controllers;

import android.R.layout;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

import edu.gatech.cs2340.hkskh.Application;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.R.id;
import edu.gatech.cs2340.hkskh.Shelters.Controllers.MapsActivity;
import edu.gatech.cs2340.hkskh.Shelters.Controllers.SearchActivity;
import edu.gatech.cs2340.hkskh.Shelters.Controllers.ShelterListActivity;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.UserManager;

/**
 * This is the main page to begin search from.
 * Shows activity allowing for search select
 */
public class MainActivity extends AppCompatActivity {

    private Application state;



    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = findViewById(id.outButton);

        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());
        state = (Application) getApplication();

        UserManager userManager = new UserManager(adb);
        ShelterManager shelterManager = new ShelterManager(adb);

        int userId = state.getCurrentUserId();
        User user = userManager.findById(userId);


        // Search button, button to shelter, and spinner
        Button searchButton = findViewById(id.button2);
        Button fullList = findViewById(id.button3);
        final Spinner searchSpinner = findViewById(id.spinner1);
        Button mapButton = findViewById(id.main_map_button);

        TextView nameText = findViewById(id.main_text_name);
        TextView statusText = findViewById(id.main_status_text);

        String username = user.getUsername();
        nameText.setText("Signed in as: " + username);

        statusText.setText(user.getStatus(shelterManager));


        // Sets up the search options spinner and loads the options in.
        // Note: reminder to switch out the arrays.asList for
        // something that is more flexible later like enum reference
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                layout.simple_spinner_item,
                Arrays.asList("name", "age", "gender"));
        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast logOut = Toast.makeText(getApplicationContext(),
                        "Logging out", Toast.LENGTH_SHORT);
                logOut.show();

                state.setCurrentUser(null);
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        });

        // When search button is clicked, it goes to search
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // passes on the type of search into the search activity
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                myIntent.putExtra("<Parameters>", (String) searchSpinner.getSelectedItem());
                startActivity(myIntent);
            }
        });

        // When they click the button for a full List it transitions to full list
        fullList.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        ShelterListActivity.class));
            }
        });

        // Will send you to the maps page when ready
        mapButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        MapsActivity.class));
            }
        });
    }
}
