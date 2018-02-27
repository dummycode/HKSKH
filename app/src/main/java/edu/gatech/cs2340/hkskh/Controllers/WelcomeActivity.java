package edu.gatech.cs2340.hkskh.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.ShelterServiceProvider;
import edu.gatech.cs2340.hkskh.Users.Controllers.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button b1, b2;
        b1 = findViewById(R.id.logButton);
        b2 = findViewById(R.id.regButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.RegisterActivity.class));
            }
        });
        ShelterServiceProvider.load(getBaseContext());
    }
}
