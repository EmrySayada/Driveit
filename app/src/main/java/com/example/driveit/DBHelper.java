package com.example.driveit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.IDN;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Emry Sayada
 * A class that communicates the db
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DBName = "users.db";

    public static final int DBVERSION=1;

    public static final String TABLE_NAME = "users";

    public static final String KEY_ID="_id";
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String PHONE="phone";
    public static final String EMAIL="email";
    public static final String PICTURE="picture";
    public static final String CURRENTTEACHERID = "current_teacher_id";
    public static final String ISTEACHER="teacher";


    //Teacher Table
    public static final String TEACHER_TABLE_NAME = "teachers";
    public static final String TEACHER_KEY_ID = "_id_teacher";
    public static final String TEACHER_RATING = "rating";
    public static final String TEACHER_EXP = "exp";
    public static final String TEACHER_REGION="region";


    //requests table
    public static final String REQUEST_TABLE_NAME = "requests";
    public static final String REQUEST_KEY_ID = "_id_request";
    public static final String REQUEST_STUDENT_ID = "student_id";
    public static final String REQUEST_TEACHER_ID = "teacher_id";
    public static final String REQUEST_TIMESTAMP = "timestamp";
    public static final String REQUEST_STATUS = "status";

    // lesson tbale
    public static final String LESSON_TABLE_NAME = "lessons";
    public static final String LESSON_KEY_ID = "_id_lesson";
    public static final String LESSON_STUDENT_ID = "student_id";
    public static final String LESSON_TEACHER_ID = "teacher_id";
    public static final String LESSON_TYPE = "type";
    public static final String LESSON_DATE = "date";
    public static final String LESSON_GPS = "gps";
    public static final String LESSON_FEEDBACK = "feedback";
    public static final String LESSON_STATUS = "status";


    //TODO add variables to another sql table

    private String SQL_Create = "";
    private String SQL_Delete = "";
    private SQLiteDatabase sqdb;

    // teacher table create/delete
    private String SQL_Teacher_Create = "";
    private String SQL_Teacher_Delete = "";

    //Request table create/delete
    private String SQL_Request_Create = "";
    private String SQL_Request_Delete = "";

    // lesson table create/delete
    private String SQL_Lesson_Create = "";
    private String SQL_Lesson_Delete = "";

    /**
     * constructor for the class
     * @param context
     */
    public DBHelper(@Nullable Context context){super(context, DBName, null, DBVERSION);}


    /**
     * function that creates all the required tables in the database
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        SQL_Create = "CREATE TABLE " + TABLE_NAME + " (";
        SQL_Create+=KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, ";
        SQL_Create+=USERNAME+" TEXT, ";
        SQL_Create+=PASSWORD+" TEXT, ";
        SQL_Create+=EMAIL+" TEXT, ";
        SQL_Create+=PHONE+" TEXT, ";
        SQL_Create+=PICTURE+" BLOB, ";
        SQL_Create+=CURRENTTEACHERID + " INTEGER, ";
        SQL_Create+=ISTEACHER+" INTEGER);";
        db.execSQL(SQL_Create);

        // TODO create another table
        //Teacher table creation
        SQL_Teacher_Create = "CREATE TABLE " + TEACHER_TABLE_NAME + " (";
        SQL_Teacher_Create += TEACHER_KEY_ID +" INTEGER, ";
        SQL_Teacher_Create += TEACHER_RATING + " INTEGER, ";
        SQL_Teacher_Create += TEACHER_EXP + " INTEGER, ";
        SQL_Teacher_Create += TEACHER_REGION + " TEXT ";
        SQL_Teacher_Create+=");";
        db.execSQL(SQL_Teacher_Create);

        // Request table create
        SQL_Request_Create = "CREATE TABLE " + REQUEST_TABLE_NAME + " (";
        SQL_Request_Create += REQUEST_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        SQL_Request_Create += REQUEST_STUDENT_ID + " INTEGER, ";
        SQL_Request_Create += REQUEST_TEACHER_ID + " INTEGER, ";
        SQL_Request_Create += REQUEST_TIMESTAMP + " TEXT, ";
        SQL_Request_Create += REQUEST_STATUS + " TEXT);";
        db.execSQL(SQL_Request_Create);

        SQL_Lesson_Create = "CREATE TABLE " + LESSON_TABLE_NAME + " (";
        SQL_Lesson_Create += LESSON_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        SQL_Lesson_Create += LESSON_STUDENT_ID + " INTEGER, ";
        SQL_Lesson_Create += LESSON_TEACHER_ID + " INTEGER, ";
        SQL_Lesson_Create += LESSON_TYPE + " TEXT, ";
        SQL_Lesson_Create += LESSON_DATE + " TEXT, ";
        SQL_Lesson_Create += LESSON_GPS + " TEXT, ";
        SQL_Lesson_Create += LESSON_FEEDBACK + " TEXT, ";
        SQL_Lesson_Create += LESSON_STATUS + " TEXT);";
        db.execSQL(SQL_Lesson_Create);
    }

    /**
     * updates the database
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SQL_Delete="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(SQL_Delete);
        //TODO onUpgrade another table
        SQL_Teacher_Delete = "DROP TABLE IF EXISTS "+TEACHER_TABLE_NAME;
        db.execSQL(SQL_Teacher_Delete);

        SQL_Request_Delete = "DROP TABLE IF EXISTS " + REQUEST_TABLE_NAME;
        db.execSQL(SQL_Request_Delete);

        SQL_Lesson_Delete = "DROP TABLE IF EXISTS " + LESSON_TABLE_NAME;
        db.execSQL(SQL_Lesson_Delete);
        onCreate(db);
    }

    /**
     * convert the image to a byte array
     * @param bitmap
     * @return image in a byte array
     */
    public byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * converts byte array to bitmap image
     * @param image
     * @return bitmap image
     */
    public Bitmap getPicture(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * function that deletes a user
     * @param userToDelete
     */
    public void deleteUser(User userToDelete){
        sqdb=getWritableDatabase();
        sqdb.delete(TABLE_NAME,USERNAME+"=? AND "+PASSWORD+"=?", new String[]{userToDelete.getUsername(), userToDelete.getPassword()});
        sqdb.close();
    }

    /**
     * function that deletes a request
     * @param r
     */
    public void deleteRequest(Request r){
        sqdb = getWritableDatabase();
        sqdb.delete(REQUEST_TABLE_NAME, REQUEST_KEY_ID+"=?", new String[]{String.valueOf(r.getId())});
        sqdb.close();
    }

    /**
     * a function that checks the validity of a request (past 30 days)
     * @param id
     * @param context
     */
    public void validateRequests(int id, Context context){
        // getting all the requests of a teacher.
        ArrayList<Request> requestArr = getAllTeacherRequests(id, context);
        for(int i=0; i<requestArr.size(); i++){
            if(requestArr.get(i).isValid()){
                deleteRequest(requestArr.get(i));
            }
        }
    }


    /**
     * function that inserts a new user to the database
     * @param user
     */
    public void insert(User user){
        sqdb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, user.getUsername());
        cv.put(PASSWORD, user.getPassword());
        cv.put(EMAIL, user.getEmail());
        cv.put(PHONE, user.getPhone());
        cv.put(PICTURE, getBytes(user.getImage()));
        cv.put(CURRENTTEACHERID, 0);
        cv.put(ISTEACHER, user.getIsTeacher());
        sqdb.insert(TABLE_NAME, null, cv);
        sqdb.close();
    }

    /**
     * function that inserts a teacher to the database
     * @param teacher
     */
    public void insertTeacher(Teacher teacher){
        sqdb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TEACHER_KEY_ID, teacher.getId());
        cv.put(TEACHER_RATING, teacher.getRating());
        cv.put(TEACHER_EXP, teacher.getExp());
        cv.put(TEACHER_REGION, teacher.getRegion());
        sqdb.insert(TEACHER_TABLE_NAME, null, cv);
        sqdb.close();
    }

    /**
     * function that inserts a request to the database
     * @param r
     */
    public void insertRequest(Request r){
        sqdb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(REQUEST_STUDENT_ID, r.getStudent_id());
        cv.put(REQUEST_TEACHER_ID, r.getTeacher_id());
        cv.put(REQUEST_TIMESTAMP, r.getTimestamp());
        cv.put(REQUEST_STATUS, r.getStatus());
        sqdb.insert(REQUEST_TABLE_NAME, null, cv);
        sqdb.close();
    }

    /**
     * function that insert lesson into the database
     * @param l
     */
    public void insertLesson(Lesson l){
        sqdb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LESSON_STUDENT_ID, l.getStudentId());
        cv.put(LESSON_TEACHER_ID, l.getTeacherId());
        cv.put(LESSON_TYPE, l.getType());
        cv.put(LESSON_DATE, l.getTimestamp());
        cv.put(LESSON_GPS, l.getGps());
        cv.put(LESSON_FEEDBACK, l.getFeedback());
        cv.put(LESSON_STATUS, l.getStatus());
        sqdb.insert(LESSON_TABLE_NAME, null, cv);
        sqdb.close();
    }

    /**
     * function that find all the users lessons
     * @param id
     * @return lessonArr
     */
    public ArrayList<Lesson> getAllUserLessons(int id){
        Cursor c;
        ArrayList<Lesson> lessonArr = new ArrayList<>();
        sqdb = getWritableDatabase();
        c = sqdb.query(LESSON_TABLE_NAME, null, LESSON_STUDENT_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        int lesson_id_col = c.getColumnIndex(LESSON_KEY_ID);
        int student_id_col = c.getColumnIndex(LESSON_STUDENT_ID);
        int teacher_id_col = c.getColumnIndex(LESSON_TEACHER_ID);
        int type_col = c.getColumnIndex(LESSON_TYPE);
        int date_col = c.getColumnIndex(LESSON_DATE);
        int gps_col = c.getColumnIndex(LESSON_GPS);
        int feedback_col = c.getColumnIndex(LESSON_FEEDBACK);
        int status_col = c.getColumnIndex(LESSON_STATUS);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int lesson_id = c.getInt(lesson_id_col);
            int student_id = c.getInt(student_id_col);
            int teacher_id= c.getInt(teacher_id_col);
            String type = c.getString(type_col);
            String date = c.getString(date_col);
            String gps = c.getString(gps_col);
            String feedback = c.getString(feedback_col);
            String status = c.getString(status_col);
            Lesson lesson = new Lesson(lesson_id, student_id, teacher_id, type ,date, gps, feedback, status);
            lessonArr.add(lesson);
            c.moveToNext();
        }
        return lessonArr;
    }

    /**
     * function that fetches all the teacher's lesson
     * @param id
     * @return ArrayList<Lesson> teacher's lessons
     */
    public ArrayList<Lesson> getAllTeacherLessons(int id){
        Cursor c;
        ArrayList<Lesson> lessonArr = new ArrayList<>();
        sqdb = getWritableDatabase();
        c = sqdb.query(LESSON_TABLE_NAME, null, LESSON_TEACHER_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        int lesson_id_col = c.getColumnIndex(LESSON_KEY_ID);
        int student_id_col = c.getColumnIndex(LESSON_STUDENT_ID);
        int teacher_id_col = c.getColumnIndex(LESSON_TEACHER_ID);
        int type_col = c.getColumnIndex(LESSON_TYPE);
        int date_col = c.getColumnIndex(LESSON_DATE);
        int gps_col = c.getColumnIndex(LESSON_GPS);
        int feedback_col = c.getColumnIndex(LESSON_FEEDBACK);
        int status_col = c.getColumnIndex(LESSON_STATUS);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int lesson_id = c.getInt(lesson_id_col);
            int student_id = c.getInt(student_id_col);
            int teacher_id= c.getInt(teacher_id_col);
            String type = c.getString(type_col);
            String date = c.getString(date_col);
            String gps = c.getString(gps_col);
            String feedback = c.getString(feedback_col);
            String status = c.getString(status_col);
            Lesson lesson = new Lesson(lesson_id, student_id, teacher_id, type ,date, gps, feedback, status);
            lessonArr.add(lesson);
            c.moveToNext();
        }
        return lessonArr;
    }

    /**
     * function that gets all the teacher requests
     * @param id
     * @param context
     * @return requestArr
     */
    public ArrayList<Request> getAllTeacherRequests(int id, Context context){
        Cursor c;
        ArrayList<Request> requestArr = new ArrayList<>();
        sqdb = getWritableDatabase();
        c = sqdb.query(REQUEST_TABLE_NAME, null, REQUEST_TEACHER_ID+"=? AND "+REQUEST_STATUS+"=?", new String[]{String.valueOf(id), "Pending"}, null, null, null);
        int request_id_col = c.getColumnIndex(REQUEST_KEY_ID);
        int student_id_col = c.getColumnIndex(REQUEST_STUDENT_ID);
        int teacher_id_col = c.getColumnIndex(REQUEST_TEACHER_ID);
        int timestamp_col = c.getColumnIndex(REQUEST_TIMESTAMP);
        int status_col = c.getColumnIndex(REQUEST_STATUS);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int request_id = c.getInt(request_id_col);
            int request_student_id = c.getInt(student_id_col);
            int request_teacher_id = c.getInt(teacher_id_col);
            String request_timestamp = c.getString(timestamp_col);
            String request_status = c.getString(status_col);
            Request request = new Request(request_id, request_student_id, request_teacher_id, request_timestamp, request_status);
            request.setMydb(context);
            requestArr.add(request);
            c.moveToNext();
        }
        sqdb.close();
        return requestArr;
    }

    /**
     * function that checks if a user exists
     * @param email
     * @param password
     * @return if the user exists
     */
    public boolean userExists(String email, String password){
        Cursor c;
        sqdb = getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, null, null, null, null, null);
        int col1 = c.getColumnIndex(EMAIL);
        int col2 = c.getColumnIndex(PASSWORD);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            if(s1.equals(email)&&s2.equals(password)){
                return true;
            }
            c.moveToNext();
        }
        sqdb.close();
        return false;
    }

    /**
     * function that find the user Id in the database
     * @param username
     * @param phone
     * @return userId
     */
    public int searchUserId(String username, String phone){
        Cursor c;
        int s_id=0;
        User user = null;
        sqdb=getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, USERNAME+"=? AND "+PHONE+"=?", new String[]{username,phone}, null, null, null);
        int col_id = c.getColumnIndex(KEY_ID);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6=c.getColumnIndex(ISTEACHER);
        c.moveToFirst();
        while(!c.isAfterLast()){
            s_id = c.getInt(col_id);
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image = getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            user = new User(s1, s2, s3, s4, image, s6);
            c.moveToNext();
        }
        sqdb.close();
        return s_id;
    }

    /**
     * function the find the user Id
     * @param email
     * @return userId
     */
    public int searchUserIdByEmail(String email){
        Cursor c;
        int s_id=0;
        User user = null;
        sqdb=getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, EMAIL+"=?", new String[]{email}, null, null, null);
        int col_id = c.getColumnIndex(KEY_ID);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6=c.getColumnIndex(ISTEACHER);
        c.moveToFirst();
        while(!c.isAfterLast()){
            s_id = c.getInt(col_id);
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image = getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            user = new User(s1, s2, s3, s4, image, s6);
            c.moveToNext();
        }
        sqdb.close();
        return s_id;
    }

    /**
     * function that gets the user by its id
     * @param id
     * @return user
     */
    public User getUserById(int id){
        Cursor c;
        User user = null;
        sqdb = getWritableDatabase();
        c = sqdb.query(TABLE_NAME, null, KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6=c.getColumnIndex(ISTEACHER);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image = getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            user = new User(s1, s2, s3, s4, image, s6);
            user.setId(id);
            c.moveToNext();
        }
        sqdb.close();
        return user;
    }

    /**
     * function the gets all the users in the database
     * @return arrayList of users
     */
    public ArrayList<User> getAllUsers(){
        Cursor c;
        ArrayList<User> arrUsers = new ArrayList<>();
        sqdb=getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, null, null, null, null, null);
        int id_col = c.getColumnIndex(KEY_ID);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6 = c.getColumnIndex(ISTEACHER);
        c.moveToNext();
        while(!c.isAfterLast()){
            int s_id= c.getInt(id_col);
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image=getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            User user = new User(s1,s2,s3,s4,image, s6);
            arrUsers.add(user);
            c.moveToNext();
        }
        sqdb.close();
        return arrUsers;
    }


    /**
     * function that updates the user information in the database
     * @param oldUser
     * @param newUser
     */
    public void updateUser(User oldUser, User newUser){
        sqdb=getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, newUser.getUsername());
        cv.put(PASSWORD, newUser.getPassword());
        cv.put(EMAIL, newUser.getEmail());
        cv.put(PHONE, newUser.getPhone());
        cv.put(PICTURE, getBytes(newUser.getImage()));
        sqdb.update(TABLE_NAME, cv, USERNAME+"=? AND "+PASSWORD+"=?", new String[]{oldUser.getUsername(), oldUser.getPassword()});
        sqdb.close();
    }

    /**
     * function that approves a request
     * @param requestId
     * @param studentId
     * @param teacherId
     */
    public void approveRequestDB(int requestId,int studentId, int teacherId){
        sqdb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CURRENTTEACHERID, teacherId);
        sqdb.update(TABLE_NAME, cv, KEY_ID+"=?", new String[]{String.valueOf(studentId)});
        ContentValues cv2 = new ContentValues();
        cv2.put(REQUEST_STATUS, "Approved");
        sqdb.update(REQUEST_TABLE_NAME, cv2, REQUEST_KEY_ID+"=?", new String[]{String.valueOf(requestId)});
        sqdb.close();
    }

    /**
     * function that check if a user is a teacher
     * @param email
     * @param password
     * @return isTeacher
     */
    public boolean isTeacherInDB(String email, String password){
        Cursor c;
        User user = null;
        sqdb=getWritableDatabase();
        c=sqdb.query(TABLE_NAME, null, EMAIL+"=? AND "+PASSWORD+"=?", new String[]{email,password}, null, null, null);
        int col_id = c.getColumnIndex(KEY_ID);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6=c.getColumnIndex(ISTEACHER);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int s_id = c.getInt(col_id);
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image = getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            user = new User(s1, s2, s3, s4, image, s6);
            c.moveToNext();
        }
        sqdb.close();
        if(user!=null){
            if(user.getIsTeacher()==1){
                return true;
            }
        }
        return false;
    }

    /**
     * function that gets all the teacher in the database
     * @return ArrayList of teachers
     */
    public ArrayList<Teacher> getAllTeachers(){
        // function loops through the teacher table and uses the teacher id to find the user information in the users table.
        Cursor c;
        Teacher teacher = null;
        User user = null;
        ArrayList<Teacher> arrTeachers = new ArrayList<>();
        sqdb = getWritableDatabase();
        c = sqdb.query(TEACHER_TABLE_NAME, null, null, null, null, null, null);
        int id_col = c.getColumnIndex(TEACHER_KEY_ID);
        int col1 = c.getColumnIndex(TEACHER_RATING);
        int col2 = c.getColumnIndex(TEACHER_EXP);
        int col3 = c.getColumnIndex(TEACHER_REGION);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int id = c.getInt(id_col);
            int teacher_rating = c.getInt(col1);
            int teacher_exp = c.getInt(col2);
            String teacher_region = c.getString(col3);
            user = getUserById(id);
            teacher = new Teacher(user, teacher_rating, teacher_exp, teacher_region);
            arrTeachers.add(teacher);
            c.moveToNext();
        }
        sqdb.close();
        return arrTeachers;
    }

    /**
     * function that checks if a user is without a teacher
     * @param email
     * @return isWithoutTeacher
     */
    public boolean isWithoutTeacher(String email){
        Cursor c;
        int current_teacher_id_final = 0;
        sqdb = getWritableDatabase();
        c = sqdb.query(TABLE_NAME, null, EMAIL+"=?", new String[]{email}, null, null, null);
        int teacher_id_col = c.getColumnIndex(CURRENTTEACHERID);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int current_teacher_id = c.getInt(teacher_id_col);
            current_teacher_id_final = current_teacher_id;
            c.moveToNext();
        }
        sqdb.close();
        return current_teacher_id_final == 0;
    }

    /**
     * get the pupil's teacher object
     * @param userId
     * @return teacher
     */
    public User getUserTeacher(int userId){
        Cursor c;
        int teacherId = 0;
        User teacher = null;
        sqdb = getWritableDatabase();
        c = sqdb.query(TABLE_NAME, null, KEY_ID+"=?", new String[]{String.valueOf(userId)}, null, null, null);
        int teacher_id_col = c.getColumnIndex(CURRENTTEACHERID);
        c.moveToFirst();
        while(!c.isAfterLast()){
            teacherId = c.getInt(teacher_id_col);
            c.moveToNext();
        }
        teacher = getUserById(teacherId);
        sqdb.close();
        return teacher;
    }

    /**
     * function that gets all the pupils of a teacher
     * @param teacherId
     * @return arrayList<User> contains all the teacher's pupils
     */
    public ArrayList<User> getTeacherPupils(int teacherId){
        Cursor c;
        User pupil = null;
        ArrayList<User> pupils = new ArrayList<>();
        sqdb = getWritableDatabase();
        c = sqdb.query(TABLE_NAME, null, CURRENTTEACHERID+"=?", new String[]{String.valueOf(teacherId)}, null,null,null);
        int col_id = c.getColumnIndex(KEY_ID);
        int col1=c.getColumnIndex(USERNAME);
        int col2=c.getColumnIndex(PASSWORD);
        int col3=c.getColumnIndex(EMAIL);
        int col4=c.getColumnIndex(PHONE);
        int col5=c.getColumnIndex(PICTURE);
        int col6=c.getColumnIndex(ISTEACHER);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int s_id = c.getInt(col_id);
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            String s3 = c.getString(col3);
            String s4 = c.getString(col4);
            Bitmap image = getPicture(c.getBlob(col5));
            int s6 = c.getInt(col6);
            pupil = new User(s1, s2, s3, s4, image, s6);
            pupil.setId(s_id);
            pupil.setCurrentTeacherId(teacherId);
            pupils.add(pupil);
            c.moveToNext();
        }
        sqdb.close();
        return pupils;
    }

    /**
     * function that gets the closest lesson to the current time
     * @param teacherId
     * @return lesson
     */
    public Lesson findClosestLesson(int teacherId){
        ArrayList<Lesson> lessons = getAllTeacherLessons(teacherId);
        long current = Calendar.getInstance().getTimeInMillis();
        long minDiff = Long.MAX_VALUE;
        Lesson lessonMin = null;
        for(int i = 0; i<lessons.size(); i++){
            Lesson l = lessons.get(i);
            Calendar cld = Calendar.getInstance();
            String[] timeArray = l.timestampToArray();
            cld.set(Integer.parseInt(timeArray[2]),Integer.parseInt(timeArray[1]),Integer.parseInt(timeArray[0]),Integer.parseInt(timeArray[3]),Integer.parseInt(timeArray[4]), 0);
            long diff = cld.getTimeInMillis() - current;
            if (minDiff > diff){
                minDiff = diff;
                lessonMin = l;
            }
        }
        return lessonMin;
    }

    /**
     * starting the lesson in the database (start to get gps location, and change the status on the db)
     * @param lessonId
     */
    public void startLesson(int lessonId){
        //TODO: find how to get the user gps location, and add it to the database. (find out how to do it in as few calls to the database as possible)
    }

    /**
     * ending the lesson in the database (changing the lesson status)
     * @param lessonId
     */
    public void endLesson(int lessonId){

    }
}
