package com.example.driveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.loadDataWithBaseURL("file:///android_res/drawable/", "<img align='middle' src='loading_anim.gif' width='100%'/>", "text/html", "utf-8", null);
        wv.reload();
    }

    public void init(){
        wv = findViewById(R.id.wv);
    }

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