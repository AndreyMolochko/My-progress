package com.example.user.myprogress;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

//git remote add origin https://github.com/AndreyMolochko/My-progress.git
//git push -u origin master
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /*TextView tvEnabledGPS;
    TextView tvStatusGPS;
    TextView tvLocationGPS;
    TextView tvEnabledNet;
    TextView tvStatusNet;
    TextView tvLocationNet;*/
    FragmentStartRun fragmentStartRun;
    FragmentChooseRun fragmentChooseRun;
    FragmentTransaction fragmentTransaction;
    FragmentFreeRunning fragmentFreeRunning;
    FragmentSetDistance fragmentSetDistance;
    FragmentSport fragmentSport;
    FragmentCalculator fragmentCalculator;
    Boolean beginRunning;


    public LocationManager locationManager;
    StringBuilder sbGPS = new StringBuilder();
    StringBuilder sbNet = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        initFloatButton();
        initActionBar();
        initFragments();
        beginRunning=false;
        /*tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
        tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
        tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
        tvEnabledNet = (TextView) findViewById(R.id.tvEnabledNet);
        tvStatusNet = (TextView) findViewById(R.id.tvStatusNet);
        tvLocationNet = (TextView) findViewById(R.id.tvLocationNet);*/

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    public void initFloatButton(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void initActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initFragments(){
        fragmentStartRun = new FragmentStartRun();
        fragmentChooseRun = new FragmentChooseRun();
        fragmentFreeRunning = new FragmentFreeRunning();
        fragmentSetDistance = new FragmentSetDistance();
        fragmentSport = new FragmentSport();
        fragmentCalculator = new FragmentCalculator();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentTransaction=getFragmentManager().beginTransaction();
        if (id == R.id.nav_sport) {
            fragmentTransaction.add(R.id.layoutSportFrag,fragmentSport);
            // Handle the sport action
        } else if (id == R.id.nav_gallery) {
            fragmentTransaction.replace(R.id.layoutSportFrag,fragmentChooseRun);

        } else if (id == R.id.nav_slideshow) {
            fragmentTransaction.replace(R.id.layoutSportFrag,fragmentFreeRunning);

        } else if (id == R.id.nav_manage) {
            fragmentTransaction.replace(R.id.layoutSportFrag,fragmentSetDistance);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        //Log.i("checking","ffffffff");
        fragmentTransaction.commit();
        //check via log this place

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onClickSport(View view){

        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.imageButtonRun:
                fragmentTransaction.replace(R.id.layoutSportFrag,fragmentChooseRun);
                break;
            case R.id.imageButtonCalculator:
                fragmentTransaction.replace(R.id.layoutSportFrag,fragmentCalculator);
                break;
            case R.id.buttonSetDistance:
                fragmentTransaction.add(R.id.layoutSportFrag,fragmentSetDistance);
                break;
            case R.id.buttonFreeRunning:
                fragmentTransaction.replace(R.id.layoutSportFrag,fragmentFreeRunning);
                break;
            case R.id.buttonNext:
                fragmentTransaction.replace(R.id.layoutSportFrag,fragmentFreeRunning);
                break;
            case R.id.buttonStartFinishRunning:
                if(!beginRunning) {
                    ((Button) fragmentFreeRunning.getView().findViewById(R.id.buttonStartFinishRunning)).
                            setText("Finish running");
                    ((Chronometer)fragmentFreeRunning.getView().findViewById(R.id.chronometerForRun)).
                            start();
                    beginRunning=true;
                    ((Button) fragmentFreeRunning.getView().findViewById(R.id.buttonResetTime)).
                            setVisibility(View.VISIBLE);
                }
                else{
                    ((Button) fragmentFreeRunning.getView().findViewById(R.id.buttonStartFinishRunning)).
                            setText("Start running");
                    ((Chronometer)fragmentFreeRunning.getView().findViewById(R.id.chronometerForRun)).
                            stop();
                    beginRunning=false;
                }
                break;
            case R.id.buttonResetTime:
                Snackbar.make(view,"Reset time",Snackbar.LENGTH_LONG).
                        setAction("Yes",snackbarOnClick).show();
                break;
        }
        fragmentTransaction.commit();
    }
    View.OnClickListener snackbarOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            ((Chronometer)fragmentFreeRunning.getView().findViewById(R.id.chronometerForRun)).
                    setBase(SystemClock.elapsedRealtime());
            ((Button) fragmentFreeRunning.getView().findViewById(R.id.buttonStartFinishRunning)).
                    setText("Start running");
            ((Button) fragmentFreeRunning.getView().findViewById(R.id.buttonResetTime)).
                    setVisibility(View.INVISIBLE);
            ((Chronometer)fragmentFreeRunning.getView().findViewById(R.id.chronometerForRun)).
                    stop();
            beginRunning=false;
        }
    };


    /*@Override
    protected void onResume() {
        super.onResume();
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10,locationListener);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,);
        Log.i("mistake", "4");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, locationListener);
        Log.i("mistake", "5");
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
        Log.i("mistake","6");
        checkEnabled();
        Log.i("mistake","7");
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }*/

    /*private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                tvStatusNet.setText("Status: " + String.valueOf(status));
            }
        }
    };*/

   /* private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGPS.setText(formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            tvLocationNet.setText(formatLocation(location));
        }
    }*/

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    /*private void checkEnabled() {
        tvEnabledGPS.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
        tvEnabledNet.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }*/

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };
}
