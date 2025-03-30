package com.example.driveit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    CardView pupilPicWrapper;

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
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 2);
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
            startLessonBtn.setEnabled(true);
        }else{
            lessontimeTv.setVisibility(View.GONE);
            pupilPic.setVisibility(View.GONE);
            pupilUsername.setVisibility(View.GONE);
            callPupilBtn.setVisibility(View.GONE);
            noLessonTv.setVisibility(View.VISIBLE);
            pupilPicWrapper.setVisibility(View.INVISIBLE);
            startLessonBtn.setEnabled(false);
        }
        startLessonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentStatus = mydb.getLessonStatus(currentLesson.getLessonId());
                if(currentStatus.equals("pending")){
                    mydb.startLesson(currentLesson.getLessonId());
                    Intent go = new Intent(getContext(), GPSService.class);
                    go.putExtra("lessonId", currentLesson.getLessonId());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        getActivity().startForegroundService(go);
                    }
                    startLessonBtn.setText("End");
                    Toast.makeText(getContext(), "Lesson Started!", Toast.LENGTH_SHORT).show();
                }else if(currentStatus.equals("ongoing")){
                    Intent endService = new Intent(getContext(), GPSService.class);
                    getActivity().stopService(endService);
                    startLessonBtn.setText("Start");
                    Toast.makeText(getContext(), "Lesson Ended!", Toast.LENGTH_SHORT).show();
                    Intent go = new Intent(getActivity(), LessonSummeryActivity.class);
                    startActivity(go);

                }
            }
        });
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
        pupilPicWrapper = view.findViewById(R.id.pupilPicWrapper);
    }
}