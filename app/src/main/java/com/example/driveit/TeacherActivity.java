package com.example.driveit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TeacherActivity extends AppCompatActivity {
    Button teacherSignOutBtn, requestViewBtn;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    DBHelper mydb;

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
        teacherSignOutBtn.setOnClickListener(new View.OnClickListener() {
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
        requestViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, RequestActivity.class);
                startActivity(intent);
            }
        });
        mydb.validateRequests(sp.getInt("userId", 0), this);
    }

    public void init(){
        teacherSignOutBtn = findViewById(R.id.teacherSignOutBtn);
        requestViewBtn = findViewById(R.id.requestViewBtn);
        sp = getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        editor = sp.edit();
        mydb = new DBHelper(this);
    }
}