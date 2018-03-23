package com.example.user.myprogress;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 27.02.2018.
 */

public class FragmentFreeRunning extends Fragment {
    //TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_free_running,null);
        init(view);
        return view;
    }

    public void init(View view){
        initView(view);
    }

    public void initView(View view){
        //textView = (TextView)view.findViewById(R.id.textViewTestingGPS);
    }
}
