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
 * class that holds all the code for the guide activity
 */
public class Guide extends AppCompatActivity {
    InputStreamReader isr;
    InputStream is;
    BufferedReader br;
    String guide;
    TextView guideTxt;

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

    /**
     * function that initializes all the elements of the screen
     */
    public void init(){
        guideTxt = findViewById(R.id.guideTxt);
        guide= "";
    }

    /**
     * function that read the contents of a file
     */
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