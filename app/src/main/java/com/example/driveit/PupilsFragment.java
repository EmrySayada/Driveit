package com.example.driveit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PupilsFragment extends Fragment {
    ListView pupilsLessonLv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pupils, container, false);
        init(view);
        return view;
    }
    public void init(View view){
        pupilsLessonLv = view.findViewById(R.id.pupilsLessonLv);
    }
}