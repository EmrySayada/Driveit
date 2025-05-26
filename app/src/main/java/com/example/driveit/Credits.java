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

/**
 * @author Emry Sayada
 * class that holds all the code for the credits page
 */
public class Credits extends AppCompatActivity {
    InputStreamReader isr;
    InputStream is;
    BufferedReader br;
    String credits;
    TextView creditsTxt;

    /**
     * function that creates and initializes all the elements in the page
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * functio that initializes all the elements in the activity
     */
    public void init(){
        creditsTxt = findViewById(R.id.creditsTxt);
        credits= "";
    }

    /**
     * function that reads the contents of a file
     */
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