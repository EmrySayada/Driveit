package com.example.driveit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

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
    ImageView teacherPic;
    User user;
    Calendar cld;
    ViewPagerAdapterTeacher pagerAdapter;
    ViewPager2 vp2Pupil;

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
        init();
        int time = cld.get(Calendar.HOUR_OF_DAY);
        user = mydb.getUserById(sp.getInt("userId", 0));
        teacherPic.setImageBitmap(user.getImage());
        teacherNameTv.setText(user.getUsername());
        if(time < 12 && time > 0){
            greetingTvTeacher.setText("Good Morning,");
        }else if(time >= 12 && time < 18){
            greetingTvTeacher.setText("Good Afternoon,");
        }else{
            greetingTvTeacher.setText("Good Night,");
        }
        mydb.validateRequests(sp.getInt("userId", 0), this);
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
        cld = Calendar.getInstance();
        vp2Pupil = findViewById(R.id.vp2Pupil);
        pagerAdapter = new ViewPagerAdapterTeacher(this);
        vp2Pupil.setAdapter(pagerAdapter);
    }
}