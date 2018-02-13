package edu.gatech.cs2340.hkskh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b1, b2;
        final EditText ed1, ed2;
        ed1 = (EditText) findViewById(R.id.userText);
        ed2 = findViewById(R.id.passText);
        b1 = findViewById(R.id.logButton);
        b2 = findViewById(R.id.button);
        /**
         * setup login button
         */
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().equals("user") && ed2.getText().toString().equals("pass")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else { //failed login
                    Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * setup cancel button
         */
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            }
        });
    }
}
