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


public class RequestFragment extends Fragment {
    ListView requestLv;
    ArrayList<Request> arrRequest;
    RequestAdapter adapter;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    SharedPreferences sp;
    TextView noRequestsTv;
    boolean noRequestsFlag = false;

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
    public void init(View view){
        requestLv = view.findViewById(R.id.requestLv);
        sp = getActivity().getSharedPreferences("PREFS_FILE", MODE_PRIVATE);
        mydb = new DBHelper(getActivity());
        arrRequest = new ArrayList<>();
        noRequestsTv = view.findViewById(R.id.noRequestsTv);
    }

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