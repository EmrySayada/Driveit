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

/**
 * @author Emry Sayada
 * class that holds all the code for the lesson summery activity
 */
public class LessonSummeryActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapView;
    GoogleMap gmap;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    int lessonId;
    ArrayList<LatLng> route;
    TextView lessonFeedback;
    Lesson currentLesson;

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

    /**
     * function that initializes all the elements
     */
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

    /**
     * function that runs on the start of the map view
     */
    @Override
    protected void onStart(){
        super.onStart();
        mapView.onStart();
    }

    /**
     * function that is called when the map stops
     */
    @Override
    protected void onStop(){
        super.onStop();
        mapView.onStop();
    }

    /**
     * function that is called when the map is destroyed
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * function that is called when there is low memory left
     */
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /**
     * function that is called when the map is paused
     */
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    /**
     * function that is called when the map is resumed
     */
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    /**
     * function that is called when the map is ready to be displayed
     * @param googleMap
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
        drawRoute(route);
    }

    /**
     * function that draws the route to the map based on given points
     * @param route
     */
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