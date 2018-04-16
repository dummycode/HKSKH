package edu.gatech.cs2340.hkskh.Users.Controllers;

import android.R.layout;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.gatech.cs2340.hkskh.Controllers.WelcomeActivity;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R.id;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Users.UserManager;

/**
 * register activity
 * registers a user by taking in their name,
 * username, and password
 */
public class RegisterActivity extends AppCompatActivity {


    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());
        final UserManager userManager = new UserManager(adb);

        setContentView(R.layout.activity_register);
        final EditText ed1;
        final EditText ed2;
        final EditText ed3;
        final EditText ed4;
        Button b1;
        Button b2;
        final Spinner sp1;
        ed1 = findViewById(id.userText);
        ed2 = findViewById(id.passText1);
        ed3 = findViewById(id.passText2);
        ed4 = findViewById(id.nameText);
        b1 = findViewById(id.regButton);
        b2 = findViewById(id.cancelButton);
        sp1 = findViewById(id.typeSelect);

        final Intent toWelcome = new Intent(this, WelcomeActivity.class);

        ArrayAdapter<UserType> userAdapter = new ArrayAdapter(this,
                layout.simple_spinner_item, UserType.values());
        userAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        sp1.setAdapter(userAdapter);

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable usernameEdit = ed1.getText();
                String username = usernameEdit.toString();
                Editable pass1Edit = ed2.getText();
                String pass1 = pass1Edit.toString();

                Editable pass2Edit = ed3.getText();
                String pass2 = pass2Edit.toString();

                Editable nameEdit = ed4.getText();
                String name = nameEdit.toString();

                if (!pass1.equals(pass2)) {
                    Toast.makeText(getApplicationContext(),
                            "Passwords do not match", Toast.LENGTH_LONG).show();
                } else {
                    if (userManager.register(username,
                            name, (UserType) sp1.getSelectedItem(), pass1)) {
                        Toast userRegistered = Toast.makeText(getApplicationContext(),
                                "Success", Toast.LENGTH_SHORT);
                        userRegistered.show();
                        startActivity(toWelcome);
                    } else {
                        Toast RegisterFailed = Toast.makeText(getApplicationContext(),
                                "Try Again. Username is taken or is shorter than 3 characters.",
                                Toast.LENGTH_LONG);
                        RegisterFailed.show();
                    }
                }
            }
        });

        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toWelcome);
            }
        });
    }
}