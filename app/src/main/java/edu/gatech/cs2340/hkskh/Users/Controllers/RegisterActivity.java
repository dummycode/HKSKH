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

import edu.gatech.cs2340.hkskh.Controllers.WelcomeActivity;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Users.UserManager;

public class RegisterActivity extends AppCompatActivity {

    private AppDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mdb = AppDatabase.getInMemoryDatabase(getApplicationContext());
        final UserManager userManager = new UserManager(this.mdb);

        setContentView(R.layout.activity_register);
        final EditText ed1, ed2, ed3, ed4;
        Button b1, b2;
        final Spinner sp1;
        ed1 = findViewById(R.id.userText);
        ed2 = findViewById(R.id.passText1);
        ed3 = findViewById(R.id.passText2);
        ed4 = findViewById(R.id.nameText);
        b1 = findViewById(R.id.regButton);
        b2 = findViewById(R.id.cancelButton);
        sp1 = findViewById(R.id.typeSelect);

        final Intent toWelcome = new Intent(this, WelcomeActivity.class);

        ArrayAdapter<UserType> userAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(userAdapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed1.getText().toString();
                String pass1 = ed2.getText().toString();
                String pass2 = ed3.getText().toString();
                String name = ed4.getText().toString();

                if (!pass1.equals(pass2)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                } else {
                    if (userManager.register(username, name, (UserType) sp1.getSelectedItem(), pass1)) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        startActivity(toWelcome);
                    } else {
                        Toast.makeText(getApplicationContext(), "Try Again. Username is taken or is shorter than 3 characters.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toWelcome);
            }
        });
    }
}