package com.example.driveit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LessonSummeryTeacherActivity extends AppCompatActivity {
    EditText feedbackEt;
    Button submitButton;
    DBHelper mydb;
    int lessonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson_summery_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedbackEt.getText().length() > 0) {
                    mydb.insertFeedback(lessonId, feedbackEt.getText().toString());
                    finish();
                }
            }
        });
    }

    public void init(){
        Intent intent = getIntent();
        lessonId = intent.getIntExtra("lessonId", 0);
        feedbackEt = findViewById(R.id.feedbackEt);
        submitButton = findViewById(R.id.submitButton);
        mydb = new DBHelper(this);
    }
}