package com.example.driveit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

/**
 * class that holds that pupil activity
 * @author Emry Sayada
 */
public class PupilActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button pupilSignOutBtn;
    DBHelper mydb;
    SQLiteDatabase sqdb;

    /**
     * function that create the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pupil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        if(mydb.isWithoutTeacher(sp.getString("email", ""))){
            Intent intent = new Intent(PupilActivity.this, ChooseTeacher.class);
            startActivity(intent);
        }

        pupilSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("email", "");
                editor.putString("password", "");
                editor.putBoolean("isConnected", false);
                editor.putBoolean("isTeacher", false);
                editor.commit();
                finish();
            }
        });
    }

    /**
     * function that initializes all the elements
     */
    public void init(){
        pupilSignOutBtn = findViewById(R.id.pupilSignOutBtn);
        sp = getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        mydb = new DBHelper(this);
        editor = sp.edit();
    }

}