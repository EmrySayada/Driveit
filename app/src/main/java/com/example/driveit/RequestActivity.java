package com.example.driveit;

import android.Manifest;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {
    ListView requestLv;
    ArrayList<Request> arrRequest;
    RequestAdapter adapter;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        init();
        createListOfRequests();
    }

    public void init(){
        requestLv = findViewById(R.id.requestLv);
        sp = getSharedPreferences("PREFS_FILE", MODE_PRIVATE);
        mydb = new DBHelper(this);
        arrRequest = new ArrayList<>();
    }
    public void createListOfRequests(){
        String email = sp.getString("email", "");
        int id = mydb.searchUserIdByEmail(email);
        arrRequest.clear();
        arrRequest=mydb.getAllTeacherRequests(id, this);
        adapter = new RequestAdapter(this, R.layout.list_requests, arrRequest);
        requestLv.setAdapter(adapter);
    }
}