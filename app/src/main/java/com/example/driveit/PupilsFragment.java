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

/**
 * @author Emry Sayada
 * class that houses the logic for the pupil's fragment
 */
public class PupilsFragment extends Fragment {
    ListView pupilsLessonLv;
    ArrayList<User> pupils;
    PupilAdapter adapter;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    SharedPreferences sp;

    /**
     * function that creates the UI
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pupils, container, false);
        init(view);
        createListOfPupils();
        return view;
    }

    /**
     * function that initializes all the elements
     * @param view
     */
    public void init(View view){
        pupilsLessonLv = view.findViewById(R.id.pupilsLessonLv);
        mydb = new DBHelper(getActivity());
        pupils = new ArrayList<>();
        sp = getActivity().getSharedPreferences("PREFS_FILE", MODE_PRIVATE);
    }

    /**
     * function that creates the list of pupils
     */
    public void createListOfPupils(){
        int id = sp.getInt("userId", 0);
        pupils.clear();
        pupils=mydb.getTeacherPupils(id);
        adapter = new PupilAdapter(getActivity(), R.layout.list_pupils, pupils);
        pupilsLessonLv.setAdapter(adapter);
    }
}