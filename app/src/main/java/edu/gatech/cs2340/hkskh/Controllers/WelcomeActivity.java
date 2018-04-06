package edu.gatech.cs2340.hkskh.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.R.id;
import edu.gatech.cs2340.hkskh.R.layout;
import edu.gatech.cs2340.hkskh.Shelters.ShelterServiceProvider;
import edu.gatech.cs2340.hkskh.Users.Controllers.LoginActivity;
import edu.gatech.cs2340.hkskh.Users.Controllers.RegisterActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_welcome);

        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());

        Button b1;
        Button b2;
        b1 = findViewById(id.logButton);
        b2 = findViewById(id.regButton);
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });

        ShelterServiceProvider.load(getBaseContext(), adb);
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}
