package com.example.myuser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myuser.model.User;

import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user " +
                "(id integer primary key, name text,email text,location text,image text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public boolean insertUser (User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("email", user.getEmail());
        contentValues.put("location", user.getLocation());
        contentValues.put("image",user.getImage());
        db.insert("user", null, contentValues);

        return true;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> array_list = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM user", null );
        res.moveToFirst();

        if(res!=null)
        {
            if (res.moveToFirst())
            {
                do {
                    User user = new User();
                    user.setName(res.getString(1));
                    user.setEmail(res.getString(2));
                    user.setLocation(res.getString( 3));
                    user.setImage(res.getBlob(4));
                    array_list.add(user);
                } while (res.moveToNext());
            }
        }
        return array_list;
    }

    public Integer deleteUser (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("user",
                "name = ? ",
                new String[] { name });
    }
}