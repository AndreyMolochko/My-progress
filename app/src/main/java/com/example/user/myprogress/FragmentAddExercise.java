package com.example.user.myprogress;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 06.03.2018.
 */

public class FragmentAddExercise extends Fragment implements View.OnClickListener{

    //@BindView(R.id.buttonAddSet) Button btn;
    @BindView(R.id.textViewExercise) TextView textViewSet;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_exers,null);
        ButterKnife.bind(view);
        Button buttonAddSet = (Button)view.findViewById(R.id.buttonAddSet);
        buttonAddSet.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAddSet:Toast.makeText(getActivity(),"sdfsdfsf",Toast.LENGTH_SHORT).show();
            break;
        }
    }
}
