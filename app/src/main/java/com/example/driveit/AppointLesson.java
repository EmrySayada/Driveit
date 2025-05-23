package com.example.driveit;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

/**
 * @author Emry Sayada
 * activity that houses the lesson appointment logic
 */
public class AppointLesson extends AppCompatActivity {
    TextView appointLessonHeaderTv, appointLessonTimeTv;
    CalendarView appointLessonCV;
    Button appointLessonTimePickerBtn, approveDetailsBtn;
    Switch examSwitch;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    String date;
    Calendar c;
    int userId;
    int teacherId;
    int currentHour, currentMinute;

    /**
     * function that creates the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appoint_lesson);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        appointLessonHeaderTv.setText("Appoint Lesson");
        appointLessonCV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = "";
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                date += mDay + "/" + (mMonth + 1) + "/" + mYear;
                appointLessonTimePickerBtn.setEnabled(true);
            }
        });
        appointLessonCV.setMinDate(c.getTimeInMillis());
        appointLessonTimePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(AppointLesson.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = "";
                        int mhour = hourOfDay;
                        int mminute = minute;
                        if (mminute < 10){
                            time = " " + mhour + ":" + "0" + mminute;
                        }else{
                            time = " " + mhour + ":" + mminute;
                        }
                        int msecond=0;
                        date += time;
                        appointLessonTimeTv.setText(date);
                    }
                }, currentHour, currentMinute, true);
                tpd.show();
            }
        });
        approveDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lesson l = null;
                if(examSwitch.isChecked()){
                    l = new Lesson(userId, teacherId, Lesson.EXAM_LESSON_TYPE, date);
                }else{
                    l = new Lesson(userId, teacherId, Lesson.REG_LESSON_TYPE, date);
                }
                mydb.insertLesson(l);
                Toast.makeText(AppointLesson.this, "Lesson created!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    /**
     * function that initializes all the UI elements
     */
    public void init(){
        appointLessonCV = findViewById(R.id.appointLessonCV);
        appointLessonHeaderTv = findViewById(R.id.appointLessonHeaderTv);
        appointLessonTimePickerBtn = findViewById(R.id.appointLessonTimePickerBtn);
        appointLessonTimeTv = findViewById(R.id.appointLessonTimeTv);
        approveDetailsBtn = findViewById(R.id.approveDetailsBtn);
        examSwitch = findViewById(R.id.examSwitch);
        mydb = new DBHelper(this);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 1);
        teacherId = intent.getIntExtra("teacherId", 0);
        date = "";
        c = Calendar.getInstance();
        currentHour = c.get(Calendar.HOUR_OF_DAY);
        currentMinute = c.get(Calendar.MINUTE);
        appointLessonTimePickerBtn.setEnabled(false);
    }
}