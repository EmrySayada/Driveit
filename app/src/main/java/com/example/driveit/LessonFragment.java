package com.example.driveit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LessonFragment extends Fragment {
    Button startLessonBtn, callPupilBtn;
    TextView lessontimeTv, pupilUsername;
    Lesson currentLesson;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        init(view);
        currentLesson = mydb.findClosestLesson(sp.getInt("userId", 0));
        if(currentLesson!=null){
            lessontimeTv.setText(currentLesson.getTimestamp());
            pupilUsername.setText(mydb.getUserById(currentLesson.getStudentId()).getUsername());
            callPupilBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tel = "tel:" + mydb.getUserById(currentLesson.getStudentId()).getPhone();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
                    startActivity(intent);
                }
            });
        }
        return view;
    }
    public void init(View view){
        sp = getContext().getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        mydb = new DBHelper(getActivity());
        startLessonBtn = view.findViewById(R.id.startLessonBtn);
        callPupilBtn = view.findViewById(R.id.callPupilBtn);
        lessontimeTv = view.findViewById(R.id.lessontimeTv);
        pupilUsername = view.findViewById(R.id.pupilUsername);

    }
}