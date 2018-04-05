package com.example.user.myprogress;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunningActivity extends AppCompatActivity {
    @BindView(R.id.chronometerForRun)
    Chronometer chronometer;
    @BindView(R.id.textViewDistance)
    TextView distance;
    @BindView(R.id.buttonResetTime)
    Button buttonResetTime;
    @BindView(R.id.buttonStartFinishRunning)
    Button buttonStartFinish;
    Boolean clickButtonStart = false;
    Boolean firstClick = true;
    private LocationManager locationManager;
    private long lastPause = SystemClock.elapsedRealtime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //chronometer.stop();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @OnClick(R.id.buttonStartFinishRunning)
    public void onClickButtonStart() {
        if (!clickButtonStart) {
            buttonStartFinish.setText(R.string.finish_running);
            clickButtonStart = true;
            chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
            chronometer.start();
            buttonResetTime.setVisibility(View.VISIBLE);
            if (!checkEnabled()) Toast.makeText(getApplicationContext(),
                    R.string.turn_gps_internet, Toast.LENGTH_SHORT).show();
        } else {
            buttonStartFinish.setText(R.string.start_running);
            clickButtonStart = false;
            lastPause = SystemClock.elapsedRealtime() + 1;
            chronometer.stop();
        }
    }

    @OnClick(R.id.buttonResetTime)
    public void onClickButtonReset(View view) {
        Snackbar.make(view, "Reset time", Snackbar.LENGTH_LONG).
                setAction("Yes", snackbarOnClick).show();
        lastPause = SystemClock.elapsedRealtime();
    }

    View.OnClickListener snackbarOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            buttonStartFinish.setText(R.string.start_running);
            buttonResetTime.setVisibility(View.INVISIBLE);
            chronometer.stop();
            clickButtonStart = false;
        }
    };

    private boolean checkEnabled() {
        //distance.setText(String.valueOf(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)));
        Boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) return true;
        else return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 2, 10,
                locationListener);
        //checkEnabled();
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            show(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            //show(locationManager.getLastKnownLocation(s));
        }

        @Override
        public void onProviderDisabled(String s) {
            if(!checkEnabled())Toast.makeText(getApplicationContext(),
                    R.string.turn_gps_internet,Toast.LENGTH_SHORT).show();
            //create notify
        }
    };

    private void show(Location location){
        distance.setText(formatLocation(location));
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

}