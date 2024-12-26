package com.example.driveit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBName = "users.db";

    public static final int DBVERSION=1;

    public static final String TABLE_NAME = "users";
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String PHONE="phone";
    public static final String EMAIL="email";
    public static final String PICTURE="picture";

    public static final String ISTEACHER="isteacher";

    //TODO add variables to another sql table

    private String SQL_Create = "";
    private String SQL_Delete = "";
    private SQLiteDatabase sqdb;

    public DBHelper(@Nullable Context context){super(context, DBName, null, DBVERSION);}


    // function that creates the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        SQL_Create = "CREATE TABLE " + TABLE_NAME + " (";
        SQL_Create+=USERNAME+" TEXT, ";
        SQL_Create+=PASSWORD+" TEXT, ";
        SQL_Create+=EMAIL+" TEXT, ";
        SQL_Create+=PHONE+" TEXT, ";
        SQL_Create+=PICTURE+" BLOB, ";
        SQL_Create+=ISTEACHER+" INTEGER);";
        db.execSQL(SQL_Create);

        // TODO create another table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SQL_Delete="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(SQL_Delete);
        //TODO onUpgrade another table

        onCreate(db);
    }

    public byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap getPicture(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void insert(User user){
        sqdb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, user.getUsername());
        cv.put(PASSWORD, user.getPassword());
        cv.put(EMAIL, user.getEmail());
        cv.put(PHONE, user.getPhone());
        cv.put(PICTURE, getBytes(user.getImage()));
        cv.put(ISTEACHER, user.getIsTeacher());
        sqdb.insert(TABLE_NAME, null, cv);
        sqdb.close();
    }

    public boolean userExists(String email, String password){
        Cursor c;
        sqdb = getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, null, null, null, null, null);
        int col1 = c.getColumnIndex(EMAIL);
        int col2 = c.getColumnIndex(PASSWORD);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            if(s1.equals(email)&&s2.equals(password)){
                return true;
            }
            c.moveToNext();
        }
        sqdb.close();
        return false;
    }

    public User searchUser(String username, String phone){
        Cursor c;
        User user = null;
        sqdb=getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, USERNAME+"=? AND "+PHONE+"=?", new String[]{username,phone}, null, null, null);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6=c.getColumnIndex(ISTEACHER);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image = getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            user = new User(s1, s2, s3, s4, image, s6);
            c.moveToNext();
        }
        sqdb.close();
        return user;
    }


    public ArrayList<User> getAllUsers(){
        Cursor c;
        ArrayList<User> arrUsers = new ArrayList<>();
        sqdb=getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, null, null, null, null, null);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6 = c.getColumnIndex(ISTEACHER);
        c.moveToNext();
        while(!c.isAfterLast()){
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image=getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            User user = new User(s1,s2,s3,s4,image, s6);
            arrUsers.add(user);
            c.moveToNext();
        }
        sqdb.close();
        return arrUsers;
    }

    public void deleteUser(User userToDelete){
        sqdb=getWritableDatabase();
        sqdb.delete(TABLE_NAME,USERNAME+"=? AND "+PASSWORD+"=?", new String[]{userToDelete.getUsername(), userToDelete.getPassword()});
        sqdb.close();
    }

    public void updateUser(User oldUser, User newUser){
        sqdb=getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, newUser.getUsername());
        cv.put(PASSWORD, newUser.getPassword());
        cv.put(EMAIL, newUser.getEmail());
        cv.put(PHONE, newUser.getPhone());
        cv.put(PICTURE, getBytes(newUser.getImage()));
        sqdb.update(TABLE_NAME, cv, USERNAME+"=? AND "+PASSWORD+"=?", new String[]{oldUser.getUsername(), oldUser.getPassword()});
        sqdb.close();
    }

    public boolean isTeacherInDB(String email, String password){
        Cursor c;
        User user = null;
        sqdb=getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, EMAIL+"=? AND "+PASSWORD+"=?", new String[]{email,password}, null, null, null);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6=c.getColumnIndex(ISTEACHER);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image = getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            user = new User(s1, s2, s3, s4, image, s6);
            c.moveToNext();
        }
        sqdb.close();
        if(user!=null){
            if(user.getIsTeacher()==1){
                return true;
            }
        }
        return false;
    }
}
