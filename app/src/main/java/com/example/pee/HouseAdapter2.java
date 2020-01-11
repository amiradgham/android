package com.example.pee;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;



public class HouseAdapter2 extends RecyclerView.Adapter<HouseAdapter2.HouseReview2> {
    private Activity activity;
    private Function function;

    private HomeDatabaseHelper objectDatabaseHandler;

    private List<Homeclasse> home;

    public HouseAdapter2(ArrayList<Homeclasse> home, Activity activity, Function<Intent,Object> function){
        this.home=home;
        this.activity=activity;
        this.function=function;
    }
    public HouseAdapter2(){

    }
    @NonNull
    @Override
    public HouseReview2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HouseReview2(LayoutInflater.from(parent.getContext()).inflate(R.layout.double_row,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull final HouseReview2 holder, final int position) {
            final Homeclasse objectmodelclass = home.get(position);
            holder.image.setImageBitmap(objectmodelclass.getBitmap());
            holder.txtName.setText((objectmodelclass.getLocation()));
            holder.txtDescription.setText(objectmodelclass.getType());
            holder.txtroom.setText((objectmodelclass.getNumber()));
            holder.txtphone.setText((objectmodelclass.getPhone()));
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = objectmodelclass.getId();
                    objectDatabaseHandler = new HomeDatabaseHelper((activity.getApplicationContext()));
                    objectDatabaseHandler.deleteData(a);
                    home.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                    notifyDataSetChanged();
                    Toast.makeText(activity, " Your House deleted with  success", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
            });

        }


    @Override
    public int getItemCount() {
        return home.size();
    }


    class HouseReview2 extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtDescription;
        TextView txtphone;
        TextView txtroom;
        ImageView image ;
        RelativeLayout relative;
        Button btn;
        Context c;
        TextView txt2;
        public HouseReview2(@NonNull View view) {
            super(view);
           txtName = view.findViewById(R.id.nameTextView);
           txtDescription = view.findViewById(R.id.descriptionTextView);
           txtphone = view.findViewById(R.id.descriptionTextView4);
           txtroom = view.findViewById(R.id.descriptionTextView2);
           image = view.findViewById(R.id.teacherImageView);
           relative = view.findViewById(R.id.relative);
           btn = view.findViewById(R.id.btn2);
           txt2 = view.findViewById(R.id.txt2);
       }
        }
    }

