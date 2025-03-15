package com.example.driveit;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * class that holds that pupil activity
 * @author Emry Sayada
 */
public class PupilActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button pupilSignOutBtn;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    ImageView pupilPic;
    TextView greetingTv, pupilNameTv;
    User user;
    Calendar cld;
    Window window;
    ArrayList<Lesson> arrLesson;
    LessonAdapter adapter;
    ListView lessonsLv;
    Context context;
    String stWhere="", channelID="", channelName="";
    NotificationChannel channel;
    NotificationManager manager;

    /**
     * function that create the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pupil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        init();
        createListOfLessons();
        if(mydb.isWithoutTeacher(sp.getString("email", ""))){
            Intent intent = new Intent(PupilActivity.this, ChooseTeacher.class);
            startActivity(intent);
        }
        user = mydb.getUserById(sp.getInt("userId", 0));
        int time = cld.get(Calendar.HOUR_OF_DAY);
        if(time < 12 && time > 0){
            greetingTv.setText("Good Morning,");
        }else if(time >= 12 && time < 18){
            greetingTv.setText("Good Afternoon,");
        }else{
            greetingTv.setText("Good Night,");
        }
        pupilNameTv.setText(user.getUsername());
        pupilPic.setImageBitmap(user.getImage());
        pupilSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("email", "");
                editor.putString("password", "");
                editor.putBoolean("isConnected", false);
                editor.putBoolean("isTeacher", false);
                editor.putInt("userId", 0);
                editor.commit();
                finish();
            }
        });
        setNotificationsForLessons(sp.getInt("userId", 0));
    }

    /**
     * function that initializes all the elements
     */
    public void init(){
        pupilSignOutBtn = findViewById(R.id.pupilSignOutBtn);
        sp = getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        mydb = new DBHelper(this);
        editor = sp.edit();
        pupilPic = findViewById(R.id.pupilPic);
        greetingTv = findViewById(R.id.greetingTv);
        pupilNameTv = findViewById(R.id.pupilNameTv);
        cld = Calendar.getInstance();
        window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.mainColor));
        lessonsLv = findViewById(R.id.lessonsLv);
        arrLesson = new ArrayList<>();
        channelID="channel_id";
        channelName="Alert_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel=new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        }
        manager=getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel);
        }
        context = PupilActivity.this;
    }

    /**
     * function that creates the UI list of the lessons
     */
    public void createListOfLessons(){
        int id = sp.getInt("userId", 0);
        arrLesson.clear();
        arrLesson = mydb.getAllUserLessons(id);
        adapter = new LessonAdapter(this, R.layout.list_lessons, arrLesson);
        lessonsLv.setAdapter(adapter);
    }

    /**
     * function that creates the notifications for all the lessons
     * @param userId
     */
    public void setNotificationsForLessons(int userId){
        // DD/MM/yyyy HH:mm
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons = mydb.getAllUserLessons(userId);
        for(int i = 0; i<lessons.size(); i++){
            Lesson l = lessons.get(i);
            String[] date = l.timestampToArray();
            cld = Calendar.getInstance();
            cld.setTimeInMillis(System.currentTimeMillis());
            cld.set(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]),Integer.parseInt(date[3]),Integer.parseInt(date[4]),0);
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = context.getSystemService(AlarmManager.class);
            long alarmTime = cld.getTimeInMillis();
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        }
    }

}