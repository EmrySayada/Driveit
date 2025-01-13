package com.example.driveit;

import android.widget.Toast;

import java.util.Calendar;

public class Request {
    private int student_id;
    private int teacher_id;
    private String timestamp;
    private String status;

    public String getDate(){
        // date format DD/MM/YYYY
        Calendar cld = Calendar.getInstance();
        timestamp += cld.get(Calendar.DAY_OF_MONTH) + "/";
        timestamp += cld.get(Calendar.MONTH) + "/";
        timestamp += cld.get(Calendar.YEAR);
        return timestamp;
    }

    public Request(int student_id, int teacher_id){
        // builder for creating a request
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.timestamp = getDate();
        this.status = "Pending";
    }

    public Request(Request other){
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


}
