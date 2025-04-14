package com.example.driveit;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Emry Sayada
 * class the oragnizes the information on a teacher
 */
public class Teacher extends User {
    private int rating;
    private int exp;
    private String region;

    /**
     * constructor for the teacher
     * @param id
     * @param username
     * @param password
     * @param email
     * @param phone
     * @param image
     * @param isTeacher
     * @param rating
     * @param exp
     * @param region
     */
    public Teacher(int id, String username, String password, String email, String phone, Bitmap image, int isTeacher, int rating, int exp ,String region) {
        super(username, password, email, phone, image, id,isTeacher);
        this.rating = rating;
        this.exp = exp;
        this.region = region;
    }

    /**
     * constructor for the teacher (adding the teacher information, based on an existing user)
     * @param user
     * @param rating
     * @param exp
     * @param region
     */
    public Teacher(User user, int rating, int exp ,String region){
        super(user);
        this.rating = rating;
        this.exp = exp;
        this.region = region;
    }

    /**
     * function that gets the teacher's rating
     * @return rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * function that sets the rating of a teacher
     * @param rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * function that gets the teacher region
     * @return region
     */
    public String getRegion() {
        return region;
    }

    /**
     * function that sets the teacher region
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * function that gets the experience of a teacher
     * @return experience
     */
    public int getExp(){return this.exp;}

    /**
     * function that sets the experience of a teacher
     * @param exp
     */
    public void setExp(int exp){this.exp = exp;}

    /**
     * function that serializes the object into a string
     * @return object overview
     */
    @Override
    public String toString(){
        return super.toString()
                + "\nrating= " + this.rating
                + "\nExp= " + this.exp
                + "\nregion= " + this.region;
    }
}
