package edu.gatech.cs2340.hkskh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button b1 = findViewById(R.id.outButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"logging out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        });
    }
}
