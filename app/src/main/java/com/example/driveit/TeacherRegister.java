package com.example.driveit;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TeacherRegister extends AppCompatActivity {

    EditText etTeacherExp;
    Teacher teacher;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    Button finishBtn;
    Spinner spRegions;
    ArrayAdapter<String> adapRegions;
    ArrayList<String> regions;
    InputStreamReader isr;
    InputStream is;
    BufferedReader br;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        regions.add("Select Region");
        Intent i = getIntent();
        teacher = (Teacher) i.getSerializableExtra("teacher");
        Toast.makeText(this, teacher.toString(), Toast.LENGTH_SHORT).show();
        goReadFromFile();
        spRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacher.setRegion(regions.get(position));
                Toast.makeText(TeacherRegister.this, "selected: "+regions.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeacherRegister.this, teacher.getRegion()+"", Toast.LENGTH_SHORT).show();
            }
        });
        // when the user finishes the signing up steps, the activity closes, and the user has to log in again.

    }

    public void goReadFromFile(){
        is=this.getResources().openRawResource(R.raw.regions);
        isr=new InputStreamReader(is);
        br=new BufferedReader(isr);
        String temp = "";
        try{
            while((temp = br.readLine())!=null){
                regions.add(temp);
            }
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void init(){
        etTeacherExp = findViewById(R.id.etTeacherExp);
        mydb = new DBHelper(this);
        finishBtn = findViewById(R.id.finishBtn);
        spRegions = findViewById(R.id.spinner);
        regions = new ArrayList<>();
        adapRegions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, regions);
        spRegions.setAdapter(adapRegions);
    }
}