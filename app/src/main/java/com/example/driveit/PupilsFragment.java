package com.example.driveit;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class PupilsFragment extends Fragment {
    ListView pupilsLessonLv;
    ArrayList<User> pupils;
    PupilAdapter adapter;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    SharedPreferences sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pupils, container, false);
        init(view);
        createListOfPupils();
        return view;
    }
    public void init(View view){
        pupilsLessonLv = view.findViewById(R.id.pupilsLessonLv);
        mydb = new DBHelper(getActivity());
        pupils = new ArrayList<>();
        sp = getActivity().getSharedPreferences("PREFS_FILE", MODE_PRIVATE);
    }

    public void createListOfPupils(){
        int id = sp.getInt("userId", 0);
        pupils.clear();
        pupils=mydb.getTeacherPupils(id);
        adapter = new PupilAdapter(getActivity(), R.layout.list_pupils, pupils);
        pupilsLessonLv.setAdapter(adapter);
    }
}