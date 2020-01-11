package com.example.pee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Show extends BaseAdapter {
    ArrayList<Homeclasse> home;
    Context c;

    public Show(ArrayList<Homeclasse> home) {
        this.home = home;
    }

    @Override
    public int getCount() {
        return home.size();
    }

    @Override
    public Object getItem(int position) {
        return home.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(c).inflate(R.layout.row_model, viewGroup, false);

        }
        TextView txtName = view.findViewById(R.id.nameTextView);
        TextView txtDescription = view.findViewById(R.id.descriptionTextView);
        final TextView txtphone = view.findViewById(R.id.descriptionTextView4);
        TextView txtroom = view.findViewById(R.id.descriptionTextView2);
        ImageView teacherImageView = view.findViewById(R.id.teacherImageView);
        final Homeclasse home = (Homeclasse) this.getItem(position);
        teacherImageView.setImageBitmap(home.getBitmap());
        txtName.setText(home.getLocation());
        txtDescription.setText(home.getType());
        txtroom.setText((home.getNumber()));
        txtphone.setText((home.getPhone()));
        return view;
    }

}

