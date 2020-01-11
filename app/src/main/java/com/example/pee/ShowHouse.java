package com.example.pee;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowHouse extends AppCompatActivity {
HomeDatabaseHelper objectDatabaseHandler;
RecyclerView objectRecyclView;
HouseAdapter objectAdapter;

Function<Intent,Object> function = (Intent intent)->{startActivity(intent);return null;};
    HouseAdapter2 objectAdapter2;
    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;
    Button btn1,btn2;
    private TextView username,txtt ;
    ImageView img1,img2,img3;
    ArrayList<Homeclasse> home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_house);

        objectDatabaseHandler = new HomeDatabaseHelper((getApplicationContext()));
        try {
            objectRecyclView = findViewById(R.id.imgerv);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        objectRecyclView = findViewById(R.id.imgerv);



        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);
        bgapp.animate().translationY(-900).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);
        btn2 = (Button) findViewById(R.id.btn2);
        Intent intent = getIntent();
        String username1 = intent.getStringExtra("username");
        username = (TextView) findViewById(R.id.txt);
        username.setText(username1);
        txtt=(TextView) findViewById(R.id.txtt);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectDatabaseHandler = new HomeDatabaseHelper((getApplicationContext()));


                    try {

                        objectAdapter2 = new HouseAdapter2(objectDatabaseHandler.getalHouse(), ShowHouse.this, function);

                        objectRecyclView.setHasFixedSize(true);

                        objectRecyclView.setLayoutManager(new LinearLayoutManager(ShowHouse.this));
                        objectRecyclView.setAdapter(objectAdapter2);
                        Button btn = (Button) findViewById(R.id.btnl);
                        btn.setVisibility(View.INVISIBLE);
                        btn2.setVisibility(View.INVISIBLE);
                        txtt.setVisibility(View.INVISIBLE);
                        textsplash = (LinearLayout) findViewById(R.id.textsplash);
                        texthome = (LinearLayout) findViewById(R.id.texthome);
                        menus = (LinearLayout) findViewById(R.id.menus);
                        bgapp = (ImageView) findViewById(R.id.bgapp);
                        bgapp.setVisibility(v.INVISIBLE);
                        textsplash.setVisibility(v.INVISIBLE);
                        texthome.setVisibility(v.INVISIBLE);
                        menus.setVisibility(v.INVISIBLE);


                    } catch (Exception e) {
                        Toast.makeText(ShowHouse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

        });

    }

    public void getData(View view){
        home= new ArrayList<Homeclasse>();


            try {
                objectDatabaseHandler = new HomeDatabaseHelper((getApplicationContext()));
                objectAdapter = new HouseAdapter(objectDatabaseHandler.getalHouse(), this, function);
                objectRecyclView.setHasFixedSize(true);
                objectRecyclView.setLayoutManager(new LinearLayoutManager(this));
                objectRecyclView.setAdapter(objectAdapter);
                textsplash = (LinearLayout) findViewById(R.id.textsplash);
                texthome = (LinearLayout) findViewById(R.id.texthome);
                menus = (LinearLayout) findViewById(R.id.menus);
                bgapp = (ImageView) findViewById(R.id.bgapp);
                bgapp.setVisibility(view.INVISIBLE);
                textsplash.setVisibility(view.INVISIBLE);
                texthome.setVisibility(view.INVISIBLE);
                menus.setVisibility(view.INVISIBLE);
                Button btn = (Button) findViewById(R.id.btnl);
                btn.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                txtt.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
