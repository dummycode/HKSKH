package edu.gatech.cs2340.hkskh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText ed1, ed2, ed3;
        Button b1, b2;
        ed1 = findViewById(R.id.userText);
        ed2 = findViewById(R.id.passText1);
        ed3 = findViewById(R.id.passText2);
        b1 = findViewById(R.id.regButton);
        b2 = findViewById(R.id.cancelButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed2.getText().toString().equals(ed3.getText().toString())) {
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
            }
        });
    }
}
