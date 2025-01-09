package com.example.driveit;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TeacherRegister extends AppCompatActivity {

    EditText etTeacherExp;
    Teacher teacher;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    Button finishBtn;
    Spinner spFiles;
    ArrayList<String> allRegions;
    ArrayAdapter<String> adapRegions;


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
        Intent i = getIntent();
        teacher = (Teacher) i.getSerializableExtra("teacher");
        Toast.makeText(this, teacher.toString(), Toast.LENGTH_SHORT).show();
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(TeacherRegister.this, chipGroup.getCheckedChipIds()+"", Toast.LENGTH_SHORT).show();
//                teacher.setRegion();
            }
        });
        // when the user finishes the signing up steps, the activity closes, and the user has to log in again.

    }

    private void goReadFromFile() {
        is=this.getResources().openRawResource(R.raw.names);
        isr=new InputStreamReader(is);
        br=new BufferedReader(isr);
        String temp = "";
        int i = 0;
        try{
            while((temp = br.readLine())!=null){
                patients[i]=temp;
                i++;
            }
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                file_name="visit_in: "+dayOfMonth+"_"+month+"_"+year;
                tvDate.setText(file_name);
            }
        });
    }

    public void init(){
//        center = findViewById(R.id.centerChip);
//        beersheva = findViewById(R.id.beershevaShip);
//        eilat = findViewById(R.id.eilatChip);
//        gazaPerimeter = findViewById(R.id.gazaPerimeterChip);
//        haifa = findViewById(R.id.haifaChip);
//        golan = findViewById(R.id.golanChip);
//        chipGroup = findViewById(R.id.chipGroup);
        etTeacherExp = findViewById(R.id.etTeacherExp);
        mydb = new DBHelper(this);
        finishBtn = findViewById(R.id.finishBtn);
    }
}