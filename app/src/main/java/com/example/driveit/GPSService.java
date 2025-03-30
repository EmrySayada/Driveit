package com.example.driveit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.ArrayList;

public class GPSService extends Service {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private int lessonId;
    private DBHelper mydb;
    private ArrayList<Location> locationArr;
    public GPSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        locationArr = new ArrayList<>();
        lessonId = intent.getIntExtra("lessonId",0);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("LocationTracking", "Service started");
        startForeground(1, getNotification());
        requestLocationUpdates();
        return START_STICKY;
    }

    public void requestLocationUpdates(){
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).setMinUpdateIntervalMillis(2000).build();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult!=null){
                    for (Location location : locationResult.getLocations()){
                        locationArr.add(location);
                        Log.d("Location tracking", "New location: " + location.getLatitude() + " " + location.getLongitude());
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    public void onDestroy(){
        mydb = new DBHelper(this);
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        mydb.insertRoute(lessonId, locationArr);
        super.onDestroy();
    }

    private Notification getNotification() {
        String channelId = "location_tracking_channel";
        String channelName = "Location Tracking";

        // Create Notification Channel (Android 8+ requires this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // Create the notification
        return new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Tracking Your Location")
                .setContentText("Your location is being tracked in the background.")
                .setSmallIcon(R.drawable.baseline_car_rental_24) // Make sure to add an icon in res/drawable
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true) // Prevents user from swiping it away
                .build();
    }


}