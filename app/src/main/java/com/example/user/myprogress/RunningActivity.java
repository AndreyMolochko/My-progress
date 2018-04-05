package com.example.user.myprogress;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunningActivity extends AppCompatActivity {
    @BindView(R.id.chronometerForRun)Chronometer chronometer;
    @BindView(R.id.textViewDistance)TextView distance;
    @BindView(R.id.buttonResetTime)Button buttonResetTime;
    @BindView(R.id.buttonStartFinishRunning)Button buttonStartFinish;
    Boolean clickButtonStart = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chronometer.stop();
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
    public void onClickButtonStart(){
        if(!clickButtonStart){
            buttonStartFinish.setText(R.string.finish_running);
            clickButtonStart=true;
            chronometer.start();
            buttonResetTime.setVisibility(View.VISIBLE);
        }
        else {
            buttonStartFinish.setText(R.string.start_running);
            clickButtonStart=false;
            chronometer.stop();
        }
    }
    @OnClick(R.id.buttonResetTime)
    public void onClickButtonReset(View view){
        Snackbar.make(view, "Reset time", Snackbar.LENGTH_LONG).
                setAction("Yes", snackbarOnClick).show();
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

}
