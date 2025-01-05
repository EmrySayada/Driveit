package com.example.driveit;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.util.ArrayList;

public class Teacher extends User {
    private int rating;
    private ArrayList<String> region;

    public Teacher(int id, String username, String password, String email, String phone, Bitmap image, int isTeacher, int rating, ArrayList<String> region) {
        super(username, password, email, phone, image, isTeacher);
        this.rating = rating;
        this.region = region;
    }

    public Teacher(User user, int rating, ArrayList<String> region){
        super(user);
        this.rating = rating;
        this.region = region;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<String> getRegion() {
        return region;
    }

    public void setRegion(ArrayList<String> region) {
        this.region = region;
    }

    @Override
    public String toString(){
        return super.toString()
                + "\nrating= " + this.rating
                + "\nregion= " + this.region;
    }
}
