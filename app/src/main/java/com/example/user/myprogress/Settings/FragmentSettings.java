package com.example.user.myprogress.Settings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myprogress.R;

/**
 * Created by User on 17.03.2018.
 */

public class FragmentSettings extends Fragment implements View.OnClickListener {
    TextView textViewSupport;
    TextView textViewLanguage;
    TextView textViewThemes;
    FragmentLanguage fragmentLanguage;
    FragmentSupport fragmentSupport;
    FragmentThemes fragmentThemes;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_settings,null);
        init(view);
        return view;
    }
    public void init(View view){
        fragmentLanguage = new FragmentLanguage();
        fragmentSupport = new FragmentSupport();
        fragmentThemes = new FragmentThemes();
        initViews(view);
        initListers(view);
    }
    public void initViews(View view){
        textViewLanguage = (TextView)view.findViewById(R.id.textViewLanguage);
        textViewSupport = (TextView)view.findViewById(R.id.textViewSupport);
        textViewThemes = (TextView)view.findViewById(R.id.textViewChangeTheme);
    }
    public void initListers(View view){
        textViewThemes.setOnClickListener(this);
        textViewSupport.setOnClickListener(this);
        textViewLanguage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.textViewLanguage:fragmentTransaction.replace(R.id.layoutSportFrag,fragmentLanguage);
            break;
            case R.id.textViewSupport:fragmentTransaction.replace(R.id.layoutSportFrag,fragmentSupport);
            break;
            case R.id.textViewChangeTheme:fragmentTransaction.replace(R.id.layoutSportFrag,fragmentThemes);
            break;
        }
        fragmentTransaction.commit();
    }
}
