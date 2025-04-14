package com.example.driveit;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Emry Sayada
 * class that holds the code for the ctivity
 */
public class TeacherActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    DBHelper mydb;
    Window window;
    TextView greetingTvTeacher, teacherNameTv;
    ImageView teacherPic, menuBtnTeacher;
    User user;
    Calendar cld;
    ViewPagerAdapterTeacher pagerAdapter;
    ViewPager2 vp2Pupil;
    Context context;
    String stWhere="", channelID="", channelName="";
    NotificationChannel channel;
    NotificationManager manager;

    /**
     * function that creates the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.CALL_PHONE, Manifest.permission.FOREGROUND_SERVICE}, 1);
        init();
        int time = cld.get(Calendar.HOUR_OF_DAY);
        user = mydb.getUserById(sp.getInt("userId", 0));
        teacherPic.setImageBitmap(user.getImage());
        teacherNameTv.setText(user.getUsername());
        menuBtnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuOptions();
            }
        });
        if(time < 12 && time > 0){
            greetingTvTeacher.setText("Good Morning,");
        }else if(time >= 12 && time < 18){
            greetingTvTeacher.setText("Good Afternoon,");
        }else{
            greetingTvTeacher.setText("Good Night,");
        }
        mydb.validateRequests(sp.getInt("userId", 0), this);
        setNotificationsForLessons(sp.getInt("userId", 0));
    }

    /**
     * function that initializes the elements in the activity
     */
    public void init(){
        sp = getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        editor = sp.edit();
        mydb = new DBHelper(this);
        window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.mainColor));
        greetingTvTeacher = findViewById(R.id.greetingTvTeacher);
        teacherNameTv = findViewById(R.id.teacherNameTv);
        teacherPic = findViewById(R.id.teacherPic);
        menuBtnTeacher = findViewById(R.id.menuBtnTeacher);
        cld = Calendar.getInstance();
        vp2Pupil = findViewById(R.id.vp2Pupil);
        pagerAdapter = new ViewPagerAdapterTeacher(this);
        vp2Pupil.setAdapter(pagerAdapter);
        channelID="channel_id";
        channelName="Alert_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel=new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        }
        manager=getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel);
        }
        context = TeacherActivity.this;
    }

    /**
     * function that sets the notifications for the lessons.
     * @param userId
     */
    public void setNotificationsForLessons(int userId){
        // DD/MM/yyyy HH:mm
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons = mydb.getAllTeacherLessons(userId);
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

    public void menuOptions(){
        final CharSequence[] options = {"Sign Out", "Credits", "Guide"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(options[i].equals("Sign Out")){
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.putBoolean("isConnected", false);
                    editor.putBoolean("isTeacher", false);
                    editor.putInt("userId", 0);
                    editor.commit();
                    finish();
                }else if(options[i].equals("Credits")){
                    //TODO: create credits activity and transfer the user there
                }else if(options[i].equals("Guide")){
                    //TODO: create guide activity and transfer the user there
                }
            }
        });
        builder.show();
    }
}