package com.example.pee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent intent = getIntent();
        String username1 = intent.getStringExtra("username");
        btn= (Button) findViewById(R.id.btnguide);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(About.this,Home.class);
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                i.putExtra("username",username);
                startActivity(i);

            }
        });
    }
}
