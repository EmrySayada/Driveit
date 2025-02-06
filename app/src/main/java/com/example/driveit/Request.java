package com.example.driveit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Emry Sayada
 * class that holds all the information that in a request
 */
public class Request {
    private int request_id;
    private int student_id;
    private int teacher_id;
    private String timestamp;
    private String status;
    private DBHelper mydb;
    private SQLiteDatabase sqdb;

    /**
     * function that gets the current date and formats it.
     * @return date
     */
    public String getDate(){
        // date format DD/MM/YYYY
        // when ever it checks for the date it should convert it to a date object.
        timestamp="";
        Calendar cld = Calendar.getInstance();
        timestamp += cld.get(Calendar.DAY_OF_MONTH)+"/";
        timestamp += cld.get(Calendar.MONTH)+"/";
        timestamp += cld.get(Calendar.YEAR);
        return timestamp;
    }

    /**
     * constructor for the request based on the teacher id and student id
     * @param student_id
     * @param teacher_id
     */
    public Request(int student_id, int teacher_id){
        // builder for creating a request
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.timestamp = getDate();
        this.status = "Pending";
    }

    /**
     * constructor for the request based on all the information (from scratch, used in DBHelper)
     * @param id
     * @param student_id
     * @param teacher_id
     * @param timestamp
     * @param status
     */
    public Request(int id, int student_id, int teacher_id, String timestamp, String status){
        // a constructor for DBHelper functions
        this.request_id = id;
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.timestamp = timestamp;
        this.status = status;
    }

    /**
     * constructor that copies information from another request
     * @param other
     */
    public Request(Request other){
        // a self builder
        this.student_id = other.student_id;
        this.teacher_id = other.teacher_id;
        this.timestamp = other.timestamp;
        this.status = other.status;
    }

    /**
     * function that gets the student Id
     * @return student id
     */
    public int getStudent_id() {
        return student_id;
    }

    /**
     * function that sets the student id
     * @param student_id
     */
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    /**
     * function that gets the teacher Id
     * @return teacher Id
     */
    public int getTeacher_id() {
        return teacher_id;
    }

    /**
     * function that sets the teacher id
     * @param teacher_id
     */
    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    /**
     * function that gets the request's timestamp (creation date)
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * function that sets the timestamp (creation date)
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * function that gets the request status
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * function that set the status
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * function that gets the request id
     * @return request id
     */
    public int getId(){
        return this.request_id;
    }

    /**
     * function that sets the request id
     * @param id
     */
    public void setId(int id){
        this.request_id = id;
    }

    /**
     * function that sets the dbhelper instance
     * @param context
     */
    public void setMydb(Context context){
        this.mydb = new DBHelper(context);
    }

    /**
     * function that get the request's user image
     * @return image
     */
    public Bitmap getUserImage(){
        User user = null;
        user = this.mydb.getUserById(this.student_id);
        return user.getImage();
    }

    /**
     * function that gets the request's user username
     * @return username
     */
    public String getUserUsername(){
        User user = null;
        user = this.mydb.getUserById(this.student_id);
        return user.getUsername();
    }

    /**
     * function that gets the request's user phone number
     * @return phone number
     */
    public String getUserPhoneNumber(){
        User user = null;
        user = this.mydb.getUserById(this.student_id);
        return user.getPhone();
    }

    /**
     * function that check if the request is valid (under 30 days)
     * @return valid/invalid
     */
    public boolean isValid(){
        // find the date of 30 days ago
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.DAY_OF_YEAR, -30);
        Date deadline = cld.getTime();
        String [] date = timestamp.split("/");
        Calendar requestDate = Calendar.getInstance();
        requestDate.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        return requestDate.before(deadline);

    }

    /**
     * function that accepts the request in the db
     */
    public void acceptRequest(){
        this.mydb.approveRequestDB(request_id,student_id, teacher_id);
    }

    /**
     * function that rejects the request in the db
     */
    public void rejectRequest(){
        this.mydb.deleteRequest(this);
    }

    /**
     * function that serializes the object into string
     * @return object overview
     */
    @Override
    public String toString(){
        return "request_id: " + this.request_id +
                "\nstudent_id: "+ this.student_id +
                "\nteacher_id: " + this.teacher_id +
                "\ntimestamp: " + this.timestamp +
                "\nstatus: " + this.status;
    }


}
