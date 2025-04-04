package com.example.driveit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class LessonSummeryActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapView;
    GoogleMap gmap;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    int lessonId;
    ArrayList<LatLng> route;
    TextView lessonFeedback;
    Lesson currentLesson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson_summery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        mapView.onCreate(null);
        mapView.getMapAsync(this);
        route = mydb.getRoute(lessonId);
        lessonFeedback.setText(currentLesson.getFeedback());
    }

    public void init(){
        route = new ArrayList<>();
        Intent intent = getIntent();
        lessonId = intent.getIntExtra("lessonId", 0);
        Toast.makeText(this, ""+lessonId, Toast.LENGTH_SHORT).show();
        mapView = findViewById(R.id.mapView);
        mydb = new DBHelper(this);
        lessonFeedback = findViewById(R.id.lessonFeedback);
        currentLesson = mydb.getLesson(lessonId);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
        drawRoute(route);
    }

    public void drawRoute(ArrayList<LatLng> route){
        if(route.isEmpty()) return;
        PolylineOptions polylineOptions = new PolylineOptions().addAll(route).width(10).color(0xFF2196F3);
        Polyline polyline = gmap.addPolyline(polylineOptions);
        gmap.addMarker(new MarkerOptions().position(route.get(0)).title("Start"));
        gmap.addMarker(new MarkerOptions().position(route.get(route.size()-1)).title("End"));
        // Move camera to the first point
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.get((int) Math.ceil(route.size()/2)), 13));
    }
}