package com.example.driveit;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class ChooseTeacher extends AppCompatActivity {
    // this activity will launch when a user first signs in, if the teacher_id = 0;
    ListView lv;
    ArrayList<Teacher> arrTeachers;
    TeacherAdapter adapter;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        createListOfTeachers();

    }

    public void init(){
        lv = findViewById(R.id.lv);
        mydb = new DBHelper(this);
        arrTeachers = new ArrayList<>();
        sp = getSharedPreferences("PREFS_FILE", MODE_PRIVATE);
    }
    public void createListOfTeachers(){
        int id = sp.getInt("userId", 0);
        arrTeachers.clear();
        arrTeachers=mydb.getAllTeachers();
        adapter = new TeacherAdapter(this, R.layout.list_teachers, arrTeachers);
        adapter.setStudentId(id);
        lv.setAdapter(adapter);
    }
}