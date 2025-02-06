package com.example.driveit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * @author Emry Sayada
 * class that holds all the information on all users (including teachers)
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Bitmap image;
    private int currentTeacherId;
    private int isTeacher;

    /**
     * constructor for the User object
     * @param username
     * @param password
     * @param email
     * @param phone
     * @param image
     * @param isTeacher
     */
    public User(String username, String password, String email, String phone, Bitmap image, int isTeacher){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.isTeacher = isTeacher;
    }

    /**
     * constructor fot the User object based on another user
     * @param other
     */
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

    /**
     * function that gets the id
     * @return id
     */
    public int getId(){
        return this.id;
    }

    /**
     * function that gets the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * function that sets the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * function that gets the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * function that sets the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * function that gets the email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * function that sets the email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * function that gets the phone number
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * function that sets the phone number
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * function that gets the image
     * @return image
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * function that gets the user Image
     * @param image
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * function that gets the current teacher id
     * @return id
     */
    public int getCurrentTeacherId(){return this.currentTeacherId;}

    /**
     * function that sets the current teacher id
     * @param currentTeacherId
     */
    public void setCurrentTeacherId(int currentTeacherId){this.currentTeacherId = currentTeacherId;}

    /**
     * function that check is the user is a teacher
     * @return teacher/not teacher
     */
    public int getIsTeacher(){
        return this.isTeacher;
    }

    /**
     * function that sets whether the user is a teacher
     * @param isTeacher
     */
    public void setIsTeacher(int isTeacher){
        this.isTeacher = isTeacher;
    }

    /**
     * function that sets the id
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * function that converts the object to string
     * @return object overview
     */
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
