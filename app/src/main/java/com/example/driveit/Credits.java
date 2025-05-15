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

public class Credits extends AppCompatActivity {
    InputStreamReader isr;
    InputStream is;
    BufferedReader br;
    String credits;
    TextView creditsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_credits);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        goReadFromFile();
        creditsTxt.setText(credits);
    }

    public void init(){
        creditsTxt = findViewById(R.id.creditsTxt);
        credits= "";
    }
    public void goReadFromFile(){
        is=this.getResources().openRawResource(R.raw.credits);
        isr=new InputStreamReader(is);
        br=new BufferedReader(isr);
        String temp = "";
        try{
            while((temp = br.readLine())!=null){
                credits+=temp + "\n";
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}