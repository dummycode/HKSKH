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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button b1 = findViewById(R.id.outButton);
        TextView text = findViewById(R.id.main_name);
        searchButton = findViewById(R.id.button2);
        fullList = findViewById(R.id.button3);

        String name = this.getIntent().getStringExtra("Name");
        text.setText("Signed in as: " + name);

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
