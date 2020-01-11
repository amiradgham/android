package com.example.pee;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class Edit extends AppCompatActivity {
ImageView imageView5;
EditText text1,text2;
String house ;
Bitmap image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        String housesurface= getIntent().getStringExtra("location");

        final Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{housesurface, "Tunis", "Monastir","Mahdia","Sfax","Bizerte","Beja","Kairouan","Gafsa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        final Spinner dropdown1 = findViewById(R.id.spinner2);
        String[] items1 = new String[]{"S+1", "S+2", "S+3","S+4","S+4","S+5"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);

        final Spinner dropdown2 = findViewById(R.id.spinner3);
        String[] items2 = new String[]{"Apartment", "House"};
        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
//set the spinners adapter to the previously created one.
        dropdown2.setAdapter(adapter2);
        text1 = (EditText) findViewById(R.id.surface);
        text2= (EditText) findViewById(R.id.phone);
        String surface= getIntent().getStringExtra("surface");
        text1.setText(surface);

       String housephone= getIntent().getStringExtra("phone");
      text2.setText(housephone);
        Bundle bundle = getIntent().getExtras();
        int surface2= bundle.getInt("id",-1);
        System.out.println(surface2);



    }
}
