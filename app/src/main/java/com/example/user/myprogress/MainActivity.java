package com.example.user.myprogress;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myprogress.Settings.FragmentSettings;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

//git remote add origin https://github.com/AndreyMolochko/My-progress.git
//git push -u origin master
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textViewGPS;
    //FragmentStartRun fragmentStartRun;
    FragmentChooseRun fragmentChooseRun;
    FragmentTransaction fragmentTransaction;
    //FragmentFreeRunning fragmentFreeRunning;
    FragmentSetDistance fragmentSetDistance;
    FragmentSport fragmentSport;
    FragmentCalculator fragmentCalculator;
    FragmentAddExercise fragmentAddExercise;
    FragmentSettings fragmentSettings;
    FragmentDiary fragmentDiary;
    FragmentCalculatorBenchPress fragmentCalculatorBenchPress;
    EditText editTextDistance;
    Boolean beginRunning;
    //int distance;


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
        //initFloatButton();
        initActionBar();
        initFragments();
        beginRunning = false;
        editTextDistance = (EditText)findViewById(R.id.editTextDistance);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    /*public void initFloatButton(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }*/

    public void initActionBar() {
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

    public void initFragments() {
        //fragmentStartRun = new FragmentStartRun();
        fragmentSettings = new FragmentSettings();
        fragmentChooseRun = new FragmentChooseRun();
        //fragmentFreeRunning = new FragmentFreeRunning();
        fragmentSetDistance = new FragmentSetDistance();
        fragmentSport = new FragmentSport();
        fragmentDiary = new FragmentDiary();
        fragmentCalculator = new FragmentCalculator();
        fragmentAddExercise = new FragmentAddExercise();
        fragmentCalculatorBenchPress = new FragmentCalculatorBenchPress();
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
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (id == R.id.nav_run) {
            fragmentTransaction.replace(R.id.layoutSportFrag, fragmentChooseRun);
            // Handle the sport action
        } else if (id == R.id.nav_strength_exercises) {
            Intent intentDiary = new Intent(MainActivity.this, SportDiary.class);
            startActivity(intentDiary);
            //fragmentTransaction.replace(R.id.layoutSportFrag,fragmentDiary);
        } else if (id == R.id.nav_fat_calculator) {
            fragmentTransaction.replace(R.id.layoutSportFrag, fragmentCalculator);

        } else if (id == R.id.nav_press) {
            fragmentTransaction.replace(R.id.layoutSportFrag, fragmentCalculatorBenchPress);
        } else if (id == R.id.nav_settings) {
            fragmentTransaction.replace(R.id.layoutSportFrag, fragmentSettings);
        }
        //Log.i("checking","ffffffff");
        fragmentTransaction.commit();
        //check via log this place

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickSport(View view) {

        fragmentTransaction = getFragmentManager().beginTransaction();
        Intent intentRun = new Intent(MainActivity.this,RunningActivity.class);
        switch (view.getId()) {
            case R.id.imageButtonRun:
                fragmentTransaction.replace(R.id.layoutSportFrag, fragmentChooseRun);
                break;
            case R.id.imageButtonCalculator:
                Intent intentCalculator = new Intent(MainActivity.this, CalculatorFat.class);
                startActivity(intentCalculator);
                break;
            case R.id.imageButtonSportDiary:
                Intent intentDiary = new Intent(MainActivity.this, SportDiary.class);
                startActivity(intentDiary);
                break;
                /*fragmentTransaction.replace(R.id.layoutSportFrag,fragmentAddExercise);
                break;*/
            case R.id.buttonSetDistance:
                fragmentTransaction.add(R.id.layoutSportFrag, fragmentSetDistance);
                break;
            case R.id.buttonFreeRunning:
                //fragmentTransaction.replace(R.id.layoutSportFrag, fragmentFreeRunning);
                startActivity(intentRun);

                break;
            //case R.id.buttonNext:
                //fragmentTransaction.replace(R.id.layoutSportFrag, fragmentFreeRunning);


                    //distance=Integer.parseInt(editTextDistance.getEditableText().toString());

                    //intentRun.putExtra(getString(R.string.gettingDistance),distance);
                    //startActivity(intentRun);

                //else Toast.makeText(getApplicationContext(),R.string.incorrectData,Toast.LENGTH_SHORT).show();

                //break;

        }
        fragmentTransaction.commit();
    }


}
