package com.example.driveit;

import android.graphics.Bitmap;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private String phone;
    private Bitmap image;
    private int isTeacher;

    public User(String username, String password, String email, String phone, Bitmap image, int isTeacher){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.isTeacher = isTeacher;
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

    public int getIsTeacher(){
        return this.isTeacher;
    }

    public void setIsTeacher(int isTeacher){
        this.isTeacher = isTeacher;
    }

    @Override
    public String toString(){
        return "username= " + username +
                "\npassword= " + password+
                "\nphone= " + phone +
                "\nemail= " + email +
                "\nimage= " + image+
                "\nisTeacher= "+ isTeacher;
    }
}
