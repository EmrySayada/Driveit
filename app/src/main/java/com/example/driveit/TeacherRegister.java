package com.example.driveit;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class TeacherRegister extends AppCompatActivity {

    EditText etTeacherExp;
    ChipGroup chipGroup;
    Chip center, beersheva, eilat, gazaPerimeter, haifa, golan;
    Teacher teacher;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    Button finishBtn;

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
        teacher = (Teacher) i.getExtras().get("teacher");
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> regionList = new ArrayList<>();
                Toast.makeText(TeacherRegister.this, chipGroup.getCheckedChipIds()+"", Toast.LENGTH_SHORT).show();
//                teacher.setRegion();
            }
        });
        // when the user finishes the signing up steps, the activity closes, and the user has to log in again.

    }

    public void init(){
        center = findViewById(R.id.centerChip);
        beersheva = findViewById(R.id.beershevaShip);
        eilat = findViewById(R.id.eilatChip);
        gazaPerimeter = findViewById(R.id.gazaPerimeterChip);
        haifa = findViewById(R.id.haifaChip);
        golan = findViewById(R.id.golanChip);
        chipGroup = findViewById(R.id.chipGroup);
        etTeacherExp = findViewById(R.id.etTeacherExp);
        mydb = new DBHelper(this);
        finishBtn = findViewById(R.id.finishBtn);
    }
}