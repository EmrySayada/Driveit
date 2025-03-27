package com.example.driveit;

import java.util.Calendar;

/**
 * @author Emry Sayada
 * This class orgenizes all the information available for a certian lesson
 */
public class Lesson {
    private int lessonId;
    private int studentId;
    private int teacherId;
    private String type;
    private String timestamp;
    private String gps;
    private String feedback;
    private String status;

    /**
     * function that gets the current date
     * @return timestamp (current time and date)
     */
    public String getDate(){
        timestamp="";
        Calendar cld = Calendar.getInstance();
        timestamp += cld.get(Calendar.DAY_OF_MONTH)+"/";
        timestamp += cld.get(Calendar.MONTH)+"/";
        timestamp += cld.get(Calendar.YEAR);
        timestamp += " "+cld.get(Calendar.HOUR)+":";
        timestamp += cld.get(Calendar.MINUTE);
        return timestamp;
    }

    /**
     * function that convert the timestamp to an array by the format DD/MM/YYYY HH:mm
     * @return date array
     */
    public String[] timestampToArray(){
        // date comes in as DD/MM/YYYY HH:mm
        String[] dateAndTime = this.timestamp.split(" ");
        String[] date = dateAndTime[0].split("/");
        String[] time = dateAndTime[1].split(":");
        String[] res = new String[date.length + time.length];
        System.arraycopy(date, 0, res, 0, date.length);
        System.arraycopy(time, 0, res, date.length, time.length);
        return res;
    }

    /**
     * constructor for the lesson object
     * @param lessonId
     * @param studentId
     * @param teacherId
     * @param timestamp
     * @param gps
     * @param feedback
     * @param type
     * @param status
     */
    public Lesson(int lessonId, int studentId, int teacherId, String type,String timestamp, String gps, String feedback, String status){
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.type = type;
        this.timestamp = timestamp;
        this.gps = gps;
        this.feedback = feedback;
        this.status = status;
    }

    /**
     * Copying constructor to be used in the DBHelper class
     * @param other
     */
    public Lesson(Lesson other){
        this.lessonId = other.lessonId;
        this.studentId = other.studentId;
        this.teacherId = other.teacherId;
        this.timestamp = other.timestamp;
        this.gps = other.gps;
        this.feedback = other.feedback;
        this.status = other.status;
    }

    /**
     * constructor for default lesson
     * @param studentId
     * @param teacherId
     * @param type
     */
    public Lesson(int studentId, int teacherId, String type, String timestamp){
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.type = type;
        this.timestamp = timestamp;
        this.feedback = "No feedback yet!";
        this.status = "pending";
    }

    /**
     * getter for lesson Id
     * @return lessonId
     */
    public int getLessonId() {
        return lessonId;
    }

    /**
     * setter for lesson Id
     * @param lessonId
     */
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * getter for student Id
     * @return student Id
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * setter for student Id
     * @param studentId
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * getter for teacher Id
     * @return teacherId
     */
    public int getTeacherId() {
        return teacherId;
    }

    /**
     * setter for teacher Id
     * @param teacherId
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * getter for type
     * @return
     */
    public String getType(){
        return this.type;
    }

    /**
     * setter for type
     * @param type
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * getter for the date of the lesson
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * setter for the date of the lesson
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * getter for the gps
     * @return gps
     */
    public String getGps() {
        return gps;
    }

    /**
     * setter for the gps
     * @param gps
     */
    public void setGps(String gps) {
        this.gps = gps;
    }

    /**
     * getter for feedback
     * @return feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * setter for feedback
     * @param feedback
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * getter for status
     * @return status
     */
    public String getStatus(){return status;}

    /**
     * setter for status
     * @param status
     */
    public void setStatus(String status) {this.status = status;}

    /**
     * serializing the object to string
     * @return object overview
     */
    @Override
    public String toString(){
        return "lessonId: "+this.lessonId+
                "\nstudentId: "+this.studentId+
                "\nteacherId: "+this.teacherId+
                "\ndate: "+this.timestamp+
                "\ngps: "+this.gps+
                "\nfeedback: "+this.feedback+
                "\nstatus: "+this.status;
    }
}
