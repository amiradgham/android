package com.example.pee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Deletee extends AppCompatActivity {
    HomeDatabaseHelper objectDatabaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletee);
        objectDatabaseHandler = new HomeDatabaseHelper((getApplicationContext()));
        String id= getIntent().getStringExtra("id");
        int a = Integer.parseInt(id);
        objectDatabaseHandler.deleteData(a);
        Toast.makeText(this," your house deleted with sucess",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ShowHouse.class));
    }
}
