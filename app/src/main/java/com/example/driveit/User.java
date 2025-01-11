package com.example.driveit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Bitmap image;
    private int currentTeacherId;
    private int isTeacher;

    public User(String username, String password, String email, String phone, Bitmap image, int isTeacher){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.isTeacher = isTeacher;
    }

    public User(User other){
        this.id = other.id;
        this.username = other.username;
        this.password = other.password;
        this.email = other.email;
        this.phone = other.phone;
        this.image = other.image;
        this.isTeacher = other.isTeacher;
        this.currentTeacherId = other.currentTeacherId;
    }

    public int getId(){
        return this.id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
    public int getCurrentTeacherId(){return this.currentTeacherId;}
    public void setCurrentTeacherId(int currentTeacherId){this.currentTeacherId = currentTeacherId;}
    public int getIsTeacher(){
        return this.isTeacher;
    }
    public void setIsTeacher(int isTeacher){
        this.isTeacher = isTeacher;
    }
    public void setId(int id){
        this.id = id;
    }


    @Override
    public String toString(){
        return "id= " + id+
                "\nusername= " + username +
                "\npassword= " + password+
                "\nphone= " + phone +
                "\nemail= " + email +
                "\nimage= " + image+
                "\nisTeacher= "+ isTeacher;
    }
}
