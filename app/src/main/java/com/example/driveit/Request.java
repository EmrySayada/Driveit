package com.example.driveit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.Calendar;

public class Request {
    private int request_id;
    private int student_id;
    private int teacher_id;
    private String timestamp;
    private String status;
    private DBHelper mydb;
    private SQLiteDatabase sqdb;

    public String getDate(){
        // date format DD/MM/YYYY
        Calendar cld = Calendar.getInstance();
        timestamp = cld.toString();
        return timestamp;
    }

    public Request(int student_id, int teacher_id){
        // builder for creating a request
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.timestamp = getDate();
        this.status = "Pending";
    }

    public Request(int id, int student_id, int teacher_id, String timestamp, String status){
        // a constructor for DBHelper functions
        this.request_id = id;
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Request(Request other){
        // a self builder
        this.student_id = other.student_id;
        this.teacher_id = other.teacher_id;
        this.timestamp = other.timestamp;
        this.status = other.status;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId(){
        return this.request_id;
    }
    public void setId(int id){
        this.request_id = id;
    }

    public void setMydb(Context context){
        this.mydb = new DBHelper(context);
    }

    public Bitmap getUserImage(){
        User user = null;
        this.sqdb = this.mydb.getWritableDatabase();
        user = this.mydb.getUserById(this.student_id);
        this.sqdb.close();
        return user.getImage();
    }

    public String getUserUsername(){
        User user = null;
        this.sqdb = this.mydb.getWritableDatabase();
        user = this.mydb.getUserById(this.student_id);
        this.sqdb.close();
        return user.getUsername();
    }

    public String getUserPhoneNumber(){
        User user = null;
        this.sqdb = this.mydb.getWritableDatabase();
        user = this.mydb.getUserById(this.student_id);
        this.sqdb.close();
        return user.getPhone();
    }


    @Override
    public String toString(){
        return "request_id: " + this.request_id +
                "\nstudent_id: "+ this.student_id +
                "\nteacher_id: " + this.teacher_id +
                "\ntimestamp: " + this.timestamp +
                "\nstatus: " + this.status;
    }


}
