package com.example.driveit;

/**
 * @author Emry Sayada
 * This class orgenizes all the information available for a certian lesson
 */
public class Lesson {
    private int lessonId;
    private int studentId;
    private int teacherId;
    private String date;
    private String gps;
    private String feedback;

    /**
     * constructor for the lesson object
     * @param lessonId
     * @param studentId
     * @param teacherId
     * @param date
     * @param gps
     * @param feedback
     */
    public Lesson(int lessonId, int studentId, int teacherId, String date, String gps, String feedback){
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.date = date;
        this.gps = gps;
        this.feedback = feedback;
    }

    /**
     * Copying constructor to be used in the DBHelper class
     * @param other
     */
    public Lesson(Lesson other){
        this.lessonId = other.lessonId;
        this.studentId = other.studentId;
        this.teacherId = other.teacherId;
        this.date = other.date;
        this.gps = other.gps;
        this.feedback = other.feedback;
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
     * getter for the date of the lesson
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * setter for the date of the lesson
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
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
     * serializing the object to string
     * @return object overview
     */
    @Override
    public String toString(){
        return "lessonId: "+this.lessonId+
                "\nstudentId: "+this.studentId+
                "\nteacherId: "+this.teacherId+
                "\ndate: "+this.date+
                "\ngps: "+this.gps+
                "\nfeedback: "+this.feedback;
    }
}
