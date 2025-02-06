package com.example.driveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * @author Emry Sayada
 * class that houses the main activity
 */
public class MainActivity extends AppCompatActivity {
    WebView wv;

    /**
     * function that handles the creation of the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.loadDataWithBaseURL("file:///android_res/drawable/", "<img align='middle' src='loading_anim.gif' width='100%'/>", "text/html", "utf-8", null);
        wv.reload();
    }

    /**
     * function that initializes all the elements
     */
    public void init(){
        wv = findViewById(R.id.wv);
    }

    /**
     * function that handles the thread which operates the animation.
     */
    @Override
    protected void onResume(){
        super.onResume();

        Thread t=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent go = new Intent(MainActivity.this, Start.class);
                    startActivity(go);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


}