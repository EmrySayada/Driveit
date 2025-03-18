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
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Emry Sayada
 * fragment for the lesson appears in the teacher Activity
 */
public class LessonFragment extends Fragment {
    Button startLessonBtn, callPupilBtn;
    TextView lessontimeTv, pupilUsername, noLessonTv;
    Lesson currentLesson;
    ImageView pupilPic;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    SharedPreferences sp;

    /**
     * function that creates the fragment
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        init(view);
        currentLesson = mydb.findClosestLesson(sp.getInt("userId", 0));
        if(currentLesson!=null){
            noLessonTv.setVisibility(View.GONE);
            lessontimeTv.setText(currentLesson.getTimestamp());
            pupilUsername.setText(mydb.getUserById(currentLesson.getStudentId()).getUsername());
            pupilPic.setImageBitmap(mydb.getUserById(currentLesson.getStudentId()).getImage());
            callPupilBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tel = "tel:" + mydb.getUserById(currentLesson.getStudentId()).getPhone();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
                    startActivity(intent);
                }
            });
        }else{
            noLessonTv.setVisibility(View.VISIBLE);
        }
        return view;
    }

    /**
     * function that initializes all the elements
     * @param view
     */
    public void init(View view){
        sp = getContext().getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        mydb = new DBHelper(getActivity());
        startLessonBtn = view.findViewById(R.id.startLessonBtn);
        callPupilBtn = view.findViewById(R.id.callPupilBtn);
        lessontimeTv = view.findViewById(R.id.lessontimeTv);
        pupilUsername = view.findViewById(R.id.pupilUsername);
        pupilPic = view.findViewById(R.id.pupilPic);
        noLessonTv = view.findViewById(R.id.noLessonTv);
    }
}