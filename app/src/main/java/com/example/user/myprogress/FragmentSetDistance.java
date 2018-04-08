package com.example.user.myprogress;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by User on 27.02.2018.
 */

public class FragmentSetDistance extends Fragment implements View.OnClickListener {

    Button buttonNext;
    EditText editTextDistance;
    int distance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.frag_set_distance,null);
        View view = inflater.inflate(R.layout.frag_set_distance,null);
        init(view);
        return view;
    }
    private void init(View view){
        initViews(view);
        initListners(view);
    }
    private void initViews(View view){
        buttonNext = (Button)view.findViewById(R.id.buttonNext);
        editTextDistance = (EditText)view.findViewById(R.id.editTextDistance);
    }
    private void initListners(View view){
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intentRun = new Intent(getActivity(),RunningActivity.class);
        switch(view.getId()){
            case R.id.buttonNext:
                try {
                    distance=Integer.parseInt(editTextDistance.getEditableText().toString());
                    if(distance>0) {
                        intentRun.putExtra("getDistance",distance);
                        startActivity(intentRun);
                    }
                    else Toast.makeText(getActivity(),getString(R.string.bigger_number_zero),Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(),R.string.incorrectData,Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;

        }
    }
}
