package com.example.pee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Adding extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    EditText text1,text2;
    Bitmap bitmap;
    private static final int PICK_FROM_GALLERY = 1;
    private ImageView teacherImageView;
    private Button showChooserBtn,sendToMySQLBtn;
    private ProgressBar uploadProgressBar;
    TextView txt;

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image To Upload"), PICK_IMAGE_REQUEST);
    }
    /*
    Receive Image data from FileChooser and set it to ImageView as Bitmap
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                teacherImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        final Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Sousse", "Tunis", "Monastir","Mahdia","Sfax","Bizerte","Beja","Kairouan","Gafsa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        final Spinner dropdown1 = findViewById(R.id.spinner2);
        String[] items1 = new String[]{"S+1", "S+2", "S+3","S+4","S+4","S+5"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
//set the spinners adapter to the previously created one.
        dropdown1.setAdapter(adapter1);

        final Spinner dropdown2 = findViewById(R.id.spinner3);
        String[] items2 = new String[]{"Apartment", "House"};
        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
//set the spinners adapter to the previously created one.
        dropdown2.setAdapter(adapter2);
        text1 = (EditText) findViewById(R.id.surface);
        text2= (EditText) findViewById(R.id.phone);
        showChooserBtn =(Button) findViewById(R.id.image1);
        teacherImageView=findViewById(R.id.imageView5);
        uploadProgressBar=findViewById(R.id.myProgressBar);

        showChooserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(Adding.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Adding.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        });


        sendToMySQLBtn= (Button) findViewById(R.id.btn1) ;
        sendToMySQLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateData()) {
                    //GET VALUES
                    String location = String.valueOf(dropdown.getSelectedItem());
                    String type =String.valueOf(dropdown1.getSelectedItem());
                    String roomNum=String.valueOf(dropdown2.getSelectedItem());
                    String surface= text1.getText().toString().trim();
                    String phone= text2.getText().toString().trim();
                    Intent intent = getIntent();
                    String username1 = intent.getStringExtra("username");
                    txt = (TextView) findViewById(R.id.txt);
                    txt.setText(username1);
                    String email = txt.getText().toString().trim();
                    HomeDatabaseHelper db = new HomeDatabaseHelper(getApplicationContext());

                    db.insertHouse(new Homeclasse(location,type,roomNum,surface,phone,bitmap,email));

                } else {
                    Toast.makeText(Adding.this, "PLEASE ENTER ALL FIELDS CORRECTLY ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateData()
    {
        text1 = (EditText) findViewById(R.id.surface);
        text2= (EditText) findViewById(R.id.phone);
        String surface= text1.getText().toString().trim();
        String phone= text2.getText().toString().trim();

        if( surface == null || phone == null){  return false;  }

        if(surface== "" || phone == ""){  return false;  }

        if(filePath == null){return false;}

        return true;
    }

}
