package com.example.user.myprogress;

import android.Manifest;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myprogress.Settings.Data;
import com.example.user.myprogress.data.ExerciseContract;
import com.example.user.myprogress.data.RunContract;
import com.example.user.myprogress.data.RunDBHelper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    Boolean firstCoordinat = true;
    private LocationManager locationManager;
    private long lastPause = SystemClock.elapsedRealtime();
    Coordinates coordinates;
    double x1, x2, y1, y2;
    double answer = 0;
    int time;
    String date;
    int distanse;
    Boolean finishRun;
    private RunDBHelper runDBHelper;
    Data data;
    FragmentRunResult fragmentRunResult;
    FragmentTransaction fragmentTransaction;
    Intent notificationIntent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_run, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (item.getItemId()){
            case R.id.item_run_result:fragmentTransaction.replace(R.id.linearDistance,fragmentRunResult);
            fragmentTransaction.commit();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        runDBHelper = new RunDBHelper(this);
        data = new Data();
        Bundle extras = getIntent().getExtras();
        fragmentRunResult = new FragmentRunResult();
        distanse=extras.getInt("getDistance");
        if(distanse>0)distance.setText(String.valueOf(answer)+" / "+String.valueOf(distanse) + " M");
        else distance.setText(String.valueOf(answer)+" M");
        notificationIntent = new Intent(this,MainActivity.class);
        finishRun=false;
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
            //displayDatabaseInfo();
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
            time = getTimeSeconds(chronometer);
            date = data.getDate();
            if(distanse>0){
                addRunsDB(getTimeSeconds(chronometer),distanse,data.getDate());
            }
            else addRunsDB(getTimeSeconds(chronometer),(int)answer,data.getDate());
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
                1000 * 2, 2, locationListener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 2, 2,
                locationListener);
        //checkEnabled();
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //show(location);
            if (!finishRun) {
                if(firstCoordinat){
                    firstCoordinat=false;
                    x1=location.getLongitude();
                    y1=location.getLatitude();
                }
                x2 = location.getLongitude();
                y2 = location.getLatitude();
                coordinates = new Coordinates(x1,y1,x2,y2);

                answer += coordinates.getConvertCoordinates()*30;
                Log.i("runnnn",String.valueOf(answer));
                String formattedDouble = new DecimalFormat("#0.00").format(answer);
                x1 = x2;
                y1 = y2;
                if(distanse>0){
                    if(answer>distanse){
                        addRunsDB(getTimeSeconds(chronometer),distanse,data.getDate());
                        distance.setText(String.valueOf(distanse)+" / "+String.valueOf(distanse) + " M");
                        chronometer.stop();
                        setNotification(String.valueOf(getTimeSeconds(chronometer)),String.valueOf(distanse));
                        finishRun=true;
                    }
                    else distance.setText(String.valueOf(formattedDouble)+" / "+String.valueOf(distanse) + " M");
                }
                else distance.setText(String.valueOf(formattedDouble)+ " M");
            }
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
        //distance.setText(formatLocation(location));
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    public void addRunsDB(int time,int distanse, String date){
        SQLiteDatabase db = runDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RunContract.RunEntry.COLUMN_TIME,time);
        contentValues.put(RunContract.RunEntry.COLUMN_DISTANCE,distanse);
        contentValues.put(RunContract.RunEntry.COLUMN_DATE,date);
        long newRowId = db.insert(RunContract.RunEntry.TABLE_NAME,null,contentValues);
    }

    public int getTimeSeconds(Chronometer chronometer){
        long a= SystemClock.elapsedRealtime()-chronometer.getBase();
        int answer = (int)a/1000;
        return answer;
    }

    public void setNotification(String time, String path){

        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Resources res = this.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(contentIntent)
                // обязательные настройки
                .setSmallIcon(R.drawable.ic_action_run)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle(getString(R.string.finish_running))
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText(getString(R.string.your_result)+" "+getString(R.string.time)+" "+time
                        +" "+ getString(R.string.distance)+" "+path) // Текст уведомления
                // необязательные настройки
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.your_result)+
                        " "+getString(R.string.time)+" "+time
                        +" "+ getString(R.string.distance)+" "+path))
                .setTicker(getString(R.string.ticker))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true); // автоматически закрыть уведомление после нажатия

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Альтернативный вариант
        // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }


}
