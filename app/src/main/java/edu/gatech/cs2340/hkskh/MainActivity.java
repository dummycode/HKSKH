package edu.gatech.cs2340.hkskh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button b1 = findViewById(R.id.outButton);
        TextView text = findViewById(R.id.main_name);

        String name = this.getIntent().getStringExtra("Name");
        text.setText("Signed in as: " + name);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        });
    }
}
