package com.example.pee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    ImageView bgapp, clover,about,imgcal,logout;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;
    private TextView username ;
    ImageView img1,img2,img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        Intent intent = getIntent();
        String username1 = intent.getStringExtra("username");
        username = (TextView) findViewById(R.id.txt);
        username.setText(username1);
        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Login.class);
                startActivity(i);
                finish();

            }
        });
        img1= (ImageView) findViewById(R.id.img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Search.class);
                startActivity(i);
            }
        });

img2= (ImageView) findViewById(R.id.img2);
img2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intt= new Intent(Home.this,Adding.class);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        intt.putExtra("username",username);
        startActivity(intt);
    }
});
img3 =(ImageView ) findViewById(R.id.img3);
img3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(Home.this,ShowHouse.class);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        i.putExtra("username",username);
        startActivity(i);
    }
});
        about = (ImageView)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,About.class);
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                i.putExtra("username",username);
                startActivity(i);

            }
        });
        imgcal = (ImageView) findViewById(R.id.calender);
        imgcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Calender.class);
                startActivity(i);
            }
        });


    }
}
