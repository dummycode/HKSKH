package edu.gatech.cs2340.hkskh.Users.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.gatech.cs2340.hkskh.Controllers.MainActivity;
import edu.gatech.cs2340.hkskh.Controllers.WelcomeActivity;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Controllers.ShelterListActivity;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;
import edu.gatech.cs2340.hkskh.Users.UserManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b1, b2;
        final EditText ed1, ed2;
        ed1 = findViewById(R.id.userText);
        ed2 = findViewById(R.id.passText);
        b1 = findViewById(R.id.logButton);
        b2 = findViewById(R.id.button);

        final UserManager accounts = new UserManager();

        // moves to the main activity
        final Intent toMain = new Intent(this, MainActivity.class);

        // Setup login button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accounts.checkEntry(ed1.getText().toString(), ed2.getText().toString())) {
                    toMain.putExtra("Name", accounts.getUserName(ed1.getText().toString()));
                    startActivity(toMain);
                } else {
                    // Failed login
                    Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Setup cancel button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            }
        });
    }
}
