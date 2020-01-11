package com.example.pee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class Add extends AppCompatActivity {
    private static final int PICK_FROM_GALLERY = 1;
    private int STORAGE_PERMISSION_CODE = 1;
    final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    EditText text1,text2;
    private ImageView teacherImageView;
    private Button showChooserBtn,sendToMySQLBtn;
    private ProgressBar uploadProgressBar;
    TextView txt;

    /******************************************************************************/
    /*
    Our data object. THE POJO CLASS
     */
    class SpiritualTeacher{
        private String location	,type,	roomNum	,surface,	phone , email;

        public SpiritualTeacher(String location, String type, String roomNum, String surface, String phone,String email) {
            this.location = location;
            this.type = type;
            this.roomNum = roomNum;
            this.surface = surface;
            this.phone = phone;
            this.email=email;

        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getType() {
            return type;
        }



        public String getSurface() {
            return surface;
        }

        public String getPhone() {
            return phone;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRoomNum() {
            return roomNum;
        }

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public void setSurface(String surface) {
            this.surface = surface;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


    }
    /******************************************************************************/
    /*
    CLASS TO UPLOAD BOTH IMAGES AND TEXT
     */
    public class MyUploader {
        //YOU CAN USE EITHER YOUR IP ADDRESS OR  10.0.2.2 I depends on the Emulator. Make sure you append
        //the `index.php` when making a POST request
        //Most emulators support this
        //private static final String DATA_UPLOAD_URL="http://10.0.2.2/php/spiritualteachers/index.php";
        //if you use genymotion you can use this
        //private static final String DATA_UPLOAD_URL="http://10.0.3.2/php/spiritualteachers/index.php";
        //You can get your ip adrress by typing ipconfig/all in cmd
        private static final String DATA_UPLOAD_URL="https://ekri.000webhostapp.com/user/index.php";

        //INSTANCE FIELDS
        private final Context c;
        public MyUploader(Context c) {this.c = c;}
        /*
        SAVE/INSERT
         */
        public void upload(SpiritualTeacher s, final View...inputViews)
        {
            if(s == null){
                Toast.makeText(c, "No Data To Save", Toast.LENGTH_SHORT).show();}
            else {
                File imageFile;
                try {
                    imageFile = new File(getImagePath(filePath));

                }catch (Exception e){
                    Toast.makeText(c, "Please pick an Image From Right Place, maybe Gallery or File Explorer so that we can get its path."+e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                uploadProgressBar.setVisibility(View.VISIBLE);

                AndroidNetworking.upload(DATA_UPLOAD_URL)
                        .addMultipartFile("image",imageFile)
                        .addMultipartParameter("location",s.getLocation())
                        .addMultipartParameter("type",s.getType())
                        .addMultipartParameter("RoomNum",s.getRoomNum())
                        .addMultipartParameter("surface",s.getSurface())
                        .addMultipartParameter("phone",s.getPhone())
                        .addMultipartParameter("email",s.getEmail())
                        .addMultipartParameter("name","upload").setTag("MYSQL_UPLOAD").setPriority(Priority.HIGH).build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response != null) {
                            try{
                                //SHOW RESPONSE FROM SERVER
                                String responseString = response.get("message").toString();
                                Toast.makeText(c, "Your House is added with : " + responseString, Toast.LENGTH_LONG).show();

                                if (responseString.equalsIgnoreCase("Success")) {
                                    //RESET VIEWS
                                    teacherImageView=findViewById(R.id.imageView5);
                                    EditText text1 = (EditText) inputViews[0];
                                    EditText text2 = (EditText) inputViews[1];
                                    ImageView teacherImageView = (ImageView) inputViews[2];

                                    text1.setText("");
                                    text2.setText("");
                                    teacherImageView.setImageResource(R.drawable.imag);

                                } else {
                                    Toast.makeText(c, "PHP WASN'T SUCCESSFUL. ", Toast.LENGTH_LONG).show();
                                }
                            }catch(Exception e)
                            {
                                e.printStackTrace();

                                System.out.println( "JSONException "+e.getMessage());
                            }
                        }else{
                            Toast.makeText(c, "NULL RESPONSE. ", Toast.LENGTH_LONG).show();
                        }
                        uploadProgressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(ANError error) {
                        error.printStackTrace();
                        uploadProgressBar.setVisibility(View.GONE);
                        Toast.makeText(c, "UNSUCCESSFUL :  ERROR IS : \n"+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
    /******************************************************************************/

    /*
    Show File Chooser Dialog
     */
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                teacherImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Get Image Path propvided its  android.net.Uri
     */
    public String getImagePath(Uri uri)
    {
        String[] projection={MediaStore.Images.Media.DATA};
        Cursor cursor=getContentResolver().query(uri,projection,null,null,null);
        if(cursor == null){
            return null;
        }
        int columnIndex= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(columnIndex);
        cursor.close();
        return s;
    }
    /*
    Perform basic data validation
     */
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
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
                    if (ActivityCompat.checkSelfPermission(Add.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Add.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
                    SpiritualTeacher s = new SpiritualTeacher(location,type,roomNum,surface,phone,email);

                    //upload data to mysql
                    new MyUploader(Add.this).upload(s, dropdown, dropdown1,dropdown2,text1,text2, teacherImageView,txt);
                } else {
                    Toast.makeText(Add.this, "PLEASE ENTER ALL FIELDS CORRECTLY ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
