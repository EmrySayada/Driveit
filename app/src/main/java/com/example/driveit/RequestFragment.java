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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Emry Sayada
 * class that houses the logic for the request fragment in the Teacher Activity
 */
public class RequestFragment extends Fragment {
    ListView requestLv;
    ArrayList<Request> arrRequest;
    RequestAdapter adapter;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    SharedPreferences sp;
    TextView noRequestsTv;
    boolean noRequestsFlag = false;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        init(view);
        createListOfRequests();
        if(noRequestsFlag){
            noRequestsTv.setVisibility(View.VISIBLE);
        }else{
            noRequestsTv.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    /**
     * function that initializes all the elements
     * @param view
     */
    public void init(View view){
        requestLv = view.findViewById(R.id.requestLv);
        sp = getActivity().getSharedPreferences("PREFS_FILE", MODE_PRIVATE);
        mydb = new DBHelper(getActivity());
        arrRequest = new ArrayList<>();
        noRequestsTv = view.findViewById(R.id.noRequestsTv);
    }

    /**
     * function that creates the list of the request
     */
    public void createListOfRequests(){
        String email = sp.getString("email", "");
        int id = mydb.searchUserIdByEmail(email);
        arrRequest.clear();
        arrRequest=mydb.getAllTeacherRequests(id, getActivity());
        adapter = new RequestAdapter(getActivity(), R.layout.list_requests, arrRequest);
        adapter.setActivity(getActivity());
        if(arrRequest.isEmpty()){
            noRequestsFlag = true;
        }
        requestLv.setAdapter(adapter);
    }
}