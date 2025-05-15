package com.example.driveit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Guide extends AppCompatActivity {
    InputStreamReader isr;
    InputStream is;
    BufferedReader br;
    String guide;
    TextView guideTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guide);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        goReadFromFile();
        guideTxt.setText(guide);
    }

    public void init(){
        guideTxt = findViewById(R.id.guideTxt);
        guide= "";
    }
    public void goReadFromFile(){
        is=this.getResources().openRawResource(R.raw.guide);
        isr=new InputStreamReader(is);
        br=new BufferedReader(isr);
        String temp = "";
        try{
            while((temp = br.readLine())!=null){
                guide+=temp + "\n";
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}