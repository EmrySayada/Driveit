package com.example.driveit;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class PupilActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button pupilSignOutBtn;
    ListView lv;
    ArrayList<Teacher> arrTeachers;
    TeacherAdapter adapter;
    DBHelper mydb;
    SQLiteDatabase sqdb;


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
        init();
        createListOfTeachers();

        pupilSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("email", "");
                editor.putString("password", "");
                editor.putBoolean("isConnected", false);
                editor.putBoolean("isTeacher", false);
                editor.commit();
                finish();
            }
        });
    }

    public void init(){
        pupilSignOutBtn = findViewById(R.id.pupilSignOutBtn);
        sp = getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        lv = findViewById(R.id.lv);
        mydb = new DBHelper(this);
        arrTeachers = new ArrayList<>();
        editor = sp.edit();
    }

    public void createListOfTeachers(){
        arrTeachers.clear();
        arrTeachers=mydb.getAllTeachers();
        adapter = new TeacherAdapter(this, R.layout.list_teachers, arrTeachers);
        lv.setAdapter(adapter);
    }
}