package com.example.pee;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Homeclasse  implements Serializable {
    int id;
    private String location,type,number,surface,phone,email;
   private  Bitmap bitmap ;
   public static final long serialVersionUID = -1021512020L;
public Homeclasse(){
}
    public Homeclasse(String location, String type, String number, String surface, String phone, Bitmap bitmap,String email) {
        this.location = location;
        this.type = type;
        this.number = number;
        this.surface = surface;
        this.phone = phone;
        this.bitmap = bitmap;
        this.email=email;
    }

    public Homeclasse(int id, String location, String type, String number, String surface, String phone, Bitmap bitmap, String email) {
       this.id=id;
        this.location = location;
        this.type = type;
        this.number = number;
        this.surface = surface;
        this.phone = phone;
        this.bitmap = bitmap;
        this.email=email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "Homeclasse{" +
                "location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", number='" + number + '\'' +
                ", surface='" + surface + '\'' +
                ", phone='" + phone + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }
}
