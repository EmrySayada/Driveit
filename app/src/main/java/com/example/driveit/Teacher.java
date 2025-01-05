package com.example.driveit;

import android.graphics.Bitmap;

public class Teacher extends User{
    private int rating;
    private String region;

    public Teacher(int rating, String region, int id, String username, String password, String email, String phone, Bitmap image, int isTeacher) {
        super(username, password, email, phone, image, isTeacher);
        this.rating = rating;
        this.region = region;
    }

    public Teacher(User user, int rating, String region){
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString(){
        return super.toString()
                + "\nrating= " + this.rating
                + "\nregion= " + this.region;
    }
}
