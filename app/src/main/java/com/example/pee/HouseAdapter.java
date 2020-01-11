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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;



public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseReview> {
    List<Homeclasse> home= null;
    RelativeLayout relativeLayout;
    Activity activity;
    Function function;
    public HouseAdapter(ArrayList<Homeclasse> home, Activity activity, Function<Intent,Object> function){
        this.home=home;
        this.activity=activity;
        this.function=function;
    }
    public HouseAdapter(){

    }
    @NonNull
    @Override
    public HouseReview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HouseReview(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull final HouseReview holder, final int position) {


            final Homeclasse objectmodelclass = home.get(position);
            holder.image.setImageBitmap(objectmodelclass.getBitmap());
            holder.txtName.setText((objectmodelclass.getLocation()));
            holder.txtDescription.setText(objectmodelclass.getType());
            holder.txtroom.setText((objectmodelclass.getNumber()));
            holder.txtphone.setText((objectmodelclass.getPhone()));
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Edit.class);
                    i.putExtra("location", objectmodelclass.getLocation());
                    i.putExtra("type", objectmodelclass.getType());
                    i.putExtra("phone", objectmodelclass.getPhone());
                    i.putExtra("surface", objectmodelclass.getSurface());
                    activity.startActivity(i);

                }
            });
        home=null;
        }

    public void add(int position, Homeclasse person) {
        home.add(position, person);
        notifyItemInserted(position);
    }
    @Override
    public int getItemCount() {
        if (home == null)
        return 0;
        else
        return  home.size();

    }
    class HouseReview extends RecyclerView.ViewHolder{
        TextView txtName;
        TextView txtDescription;
        TextView txtphone;
        TextView txtroom;
        ImageView image ;
        RelativeLayout relative;
        Button btn;
        Context c;
        public HouseReview(@NonNull View view) {

            super(view);

            txtName = view.findViewById(R.id.nameTextView);
            txtDescription = view.findViewById(R.id.descriptionTextView);
            txtphone = view.findViewById(R.id.descriptionTextView4);
            txtroom = view.findViewById(R.id.descriptionTextView2);
            image = view.findViewById(R.id.teacherImageView);
            relative= view.findViewById(R.id.relative);
            btn=view.findViewById(R.id.btn2);

        }
    }
}
