package com.example.driveit;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends User {
    private int rating;
    private int exp;
    private String region;

    public Teacher(int id, String username, String password, String email, String phone, Bitmap image, int isTeacher, int rating, int exp ,String region) {
        super(username, password, email, phone, image, isTeacher);
        this.rating = rating;
        this.exp = exp;
        this.region = region;
    }

    public Teacher(User user, int rating, int exp ,String region){
        super(user);
        this.rating = rating;
        this.exp = exp;
        this.region = region;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getExp(){return this.exp;}
    public void setExp(int exp){this.exp = exp;}

    @Override
    public String toString(){
        return super.toString()
                + "\nrating= " + this.rating
                + "\nExp= " + this.exp
                + "\nregion= " + this.region;
    }
}
