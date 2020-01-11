package com.example.pee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    Animation frombottom;

    class Teacher {
        private String location, type, image, RoomNum, phone;

        public Teacher(String location, String type, String image, String RoomNum, String phone) {
            this.location = location;
            this.type = type;
            this.image = image;
            this.RoomNum = RoomNum;
            this.phone = phone;
        }

        public String getLocation() {
            return location;
        }

        public String getType() {
            return type;
        }

        public String getImage() {
            return image;
        }

        public String getRoomNum() {
            return RoomNum;
        }

        public String getPhone() {
            return phone;
        }
    }

    /*
   Our custom adapter class
    */
    public class ListViewAdapter extends BaseAdapter {
        Context c;
        ArrayList<Teacher> teachers;

        public ListViewAdapter(Context c, ArrayList<Teacher> teachers) {
            this.c = c;
            this.teachers = teachers;
        }

        @Override
        public int getCount() {
            return teachers.size();
        }

        @Override
        public Object getItem(int i) {
            return teachers.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(c).inflate(R.layout.row_model, viewGroup, false);
            }

            TextView txtName = view.findViewById(R.id.nameTextView);
            TextView txtDescription = view.findViewById(R.id.descriptionTextView);
            final TextView txtphone = view.findViewById(R.id.descriptionTextView4);
            TextView txtroom = view.findViewById(R.id.descriptionTextView2);
            ImageView teacherImageView = view.findViewById(R.id.teacherImageView);

            final Teacher teacher = (Teacher) this.getItem(i);

            txtName.setText(teacher.getLocation());
            txtDescription.setText(teacher.getType());
            txtroom.setText((teacher.getRoomNum()));
            txtphone.setText((teacher.getPhone()));
            txtphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intt = new Intent(Intent.ACTION_DIAL);
                    intt.setData(Uri.parse("tel:" + txtphone.getText().toString()));
                    startActivity(intt);
                }
            });
            if (teacher.getImage() != null && teacher.getImage().length() > 0) {
                Picasso.get().load(teacher.getImage()).placeholder(R.drawable.placeholder).into(teacherImageView);
            } else {
                Toast.makeText(c, "Empty Image URL", Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.placeholder).into(teacherImageView);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c, teacher.getLocation(), Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }

    /*
    Our HTTP Client
     */
    public class DataRetriever {

        //YOU CAN USE EITHER YOUR IP ADDRESS OR  10.0.2.2 I depends on the Emulator
        //private static final String PHP_MYSQL_SITE_URL="http://10.0.2.2/php/spiritualteachers";
        //For genymotion you can use this
        //private static final String PHP_MYSQL_SITE_URL="http://10.0.3.2/php/spiritualteachers";
        //You can get your ip adrress by typing ipconfig/all in cmd
        private static final String PHP_MYSQL_SITE_URL = "https://ekri.000webhostapp.com/user";
        //INSTANCE FIELDS
        private final Context c;
        private ListViewAdapter adapter;

        public DataRetriever(Context c) {
            this.c = c;
        }

        /*
        RETRIEVE/SELECT/REFRESH
         */
        public void retrieve(final ListView gv, final ProgressBar myProgressBar) {
            final ArrayList<Teacher> teachers = new ArrayList<>();

            myProgressBar.setIndeterminate(true);
            myProgressBar.setVisibility(View.VISIBLE);

            AndroidNetworking.get(PHP_MYSQL_SITE_URL)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jo;
                            Teacher teacher;
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    jo = response.getJSONObject(i);

                                    int id = jo.getInt("id");
                                    String name = jo.getString("location");
                                    String description = jo.getString("type");
                                    String imageURL = jo.getString("image");
                                    String RoomNum = jo.getString("roomnumber");
                                    String phone = jo.getString("phone");

                                    teacher = new Teacher(name, description, PHP_MYSQL_SITE_URL + "/images/" + imageURL, RoomNum, phone);
                                    teachers.add(teacher);
                                }

                                //SET TO SPINNER
                                adapter = new ListViewAdapter(c, teachers);
                                gv.setAdapter(adapter);
                                myProgressBar.setVisibility(View.GONE);

                            } catch (JSONException e) {
                                myProgressBar.setVisibility(View.GONE);
                                Toast.makeText(c, "GOOD RESPONSE BUT JAVA CAN'T PARSE JSON IT RECEIEVED. " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                            myProgressBar.setVisibility(View.GONE);
                            Toast.makeText(c, "UNSUCCESSFUL :  ERROR IS : " + anError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ListView myGridView = findViewById(R.id.myListView);
            ProgressBar myDataLoaderProgressBar = findViewById(R.id.myDataLoaderProgressBar);

            new DataRetriever(Search.this).retrieve(myGridView, myDataLoaderProgressBar);



    }
}
