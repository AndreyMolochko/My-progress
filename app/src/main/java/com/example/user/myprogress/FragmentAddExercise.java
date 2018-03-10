package com.example.user.myprogress;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 06.03.2018.
 */

public class FragmentAddExercise extends Fragment {
    @BindView(R.id.textViewExercise) TextView textViewSet;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_exers,null);
        ButterKnife.bind(view);
        //textViewSet.setText("hello");
        return view;
    }
}
