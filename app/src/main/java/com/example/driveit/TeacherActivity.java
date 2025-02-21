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

import java.util.Calendar;

/**
 * @author Emry Sayada
 * class that holds the code for the ctivity
 */
public class TeacherActivity extends AppCompatActivity {
    Button teacherSignOutBtn, requestViewBtn;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    DBHelper mydb;
    Window window;
    TextView greetingTvTeacher, teacherNameTv;
    ImageView teacherPic;
    User user;
    Calendar cld;

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
        teacherSignOutBtn.setOnClickListener(new View.OnClickListener() {
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
        requestViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, RequestActivity.class);
                startActivity(intent);
            }
        });
        mydb.validateRequests(sp.getInt("userId", 0), this);
    }

    /**
     * function that initializes the elements in the activity
     */
    public void init(){
        teacherSignOutBtn = findViewById(R.id.teacherSignOutBtn);
        requestViewBtn = findViewById(R.id.requestViewBtn);
        sp = getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        editor = sp.edit();
        mydb = new DBHelper(this);
        window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.mainColor));
        greetingTvTeacher = findViewById(R.id.greetingTvTeacher);
        teacherNameTv = findViewById(R.id.teacherNameTv);
        teacherPic = findViewById(R.id.teacherPic);
        cld = Calendar.getInstance();
    }
}