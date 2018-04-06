package edu.gatech.cs2340.hkskh.Users.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.gatech.cs2340.hkskh.Application;
import edu.gatech.cs2340.hkskh.Controllers.MainActivity;
import edu.gatech.cs2340.hkskh.Controllers.WelcomeActivity;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Users.Models.User;
import edu.gatech.cs2340.hkskh.Users.UserManager;

/**
 *  Handles the UI for the login screen
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());

        final UserManager userManager = new UserManager(adb);

        Button b1;
        Button b2;
        final EditText ed1;
        final EditText ed2;
        ed1 = findViewById(R.id.userText);
        ed2 = findViewById(R.id.passText);
        b1 = findViewById(R.id.logButton);
        b2 = findViewById(R.id.button);

        // moves to the main activity
        final Intent toMain = new Intent(this, MainActivity.class);

        // Setup login button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed1.getText().toString();
                String password = ed2.getText().toString();
                if (userManager.validateCredentials(username, password)) {
                    Application state = (Application) getApplicationContext();
                    User user = userManager.findByUsername(username);
                    state.setCurrentUserId(user.getId());
                    startActivity(toMain);
                } else {
                    // Failed login
                    Toast credentialError = new Toast(getApplicationContext());
                    credentialError.makeText(getApplicationContext(),
                            "Wrong credentials", Toast.LENGTH_SHORT);
                    credentialError.show();

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
