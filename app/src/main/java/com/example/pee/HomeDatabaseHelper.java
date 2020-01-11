package com.example.pee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.SyncStateContract;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class HomeDatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    Context context;
    private byte[] imagebyte;
    private ByteArrayOutputStream objectbyteArrayOutputStream;
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "House.db";
    private static final String TABLE_house = "table_House";
    private static final String id = "id";
    private static final String location = "location";
    private static final String type = "type";
    private static final String number = "number";
    private static final String surface = "surface";
    private static final String phone = "phone";
    private static final String image = "image";
    private static final String email = "email";
    private static final String REQUETE_CREATION_BD = "create table " + TABLE_house + " (" + id + " integer primary key autoincrement, " + location
            + " TEXT not null, " + type + " TEXT not null ," + number + " TEXT not null ," + surface + " TEXT not null ," + phone + " TEXT not null ," + image + " BLOB, " + email + " TEXT not null" + ");";


    public HomeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(REQUETE_CREATION_BD);
        } catch (Exception e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_house + ";");
// Création de la nouvelle structure.
        onCreate(db);
    }

    public void insertHouse(Homeclasse house) {
        try {
            SQLiteDatabase maBD = this.getWritableDatabase();
            Bitmap imagetostore = house.getBitmap();
            objectbyteArrayOutputStream = new ByteArrayOutputStream();
            imagetostore.compress(Bitmap.CompressFormat.JPEG, 100, objectbyteArrayOutputStream);
            imagebyte = objectbyteArrayOutputStream.toByteArray();
            ContentValues valeurs = new ContentValues();
            valeurs.put(location, house.getLocation());
            valeurs.put(type, house.getType());
            valeurs.put(number, house.getNumber());
            valeurs.put(surface, house.getSurface());
            valeurs.put(phone, house.getPhone());
            valeurs.put(image, imagebyte);
            valeurs.put(email, house.getEmail());

            Long checkData = maBD.insert(TABLE_house, null, valeurs);
            if (checkData != 0) {
                Toast.makeText(context, "Your House added Sucessfully ", Toast.LENGTH_SHORT).show();
                maBD.close();
            } else {
                Toast.makeText(context, "error insert database !", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<Homeclasse> getalHouse() {
        try {
            ArrayList<Homeclasse> home = new ArrayList<>();
            SQLiteDatabase maBD = this.getReadableDatabase();
            Cursor c = maBD.rawQuery("select * from " + TABLE_house, null);
            if (c.getCount() != 0) {
                while (c.moveToNext()) {
                    int id = c.getInt(0);
                    String location = c.getString(1);
                    String type = c.getString(2);
                    String number = c.getString(3);
                    String surface = c.getString(4);
                    String phone = c.getString(5);
                    byte[] imagebyte = c.getBlob(6);
                    Bitmap objectbitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                    String email = c.getString(7);
                    home.add(new Homeclasse(id, location, type, number, surface, phone, objectbitmap, email));
                }
                return home;
            } else {
                Toast.makeText(context, "no Values existe into Database ", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }

    }


    public Cursor getItem(String email ){
        SQLiteDatabase db=this.getWritableDatabase();

        String querry="select * from "+TABLE_house+ " where "+email+ "='" +email+ "'";
        Cursor data= db.rawQuery(querry,null);
        return data;
    }








    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_house, id + " = "+id,null
             );
        db.close();
    }
}