package com.example.user.myprogress;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.myprogress.data.ExerciseDBHelper;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by User on 22.03.2018.
 */

public class FragmentDiary extends Fragment {
    private ExerciseDBHelper mDBHelper;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayExercise;
    FragmentTransaction fragmentTransaction;
    FragmentAddExercise fragmentAddExercise;
    FragmentCalendar fragmentCalendar;
    String Tag = "ProgresDiary";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sport_diary,null);
        infromLogger("1");
        init(view);
        infromLogger("2");
        return view;
    }
    public void init(View view){
        initView(view);
        fragmentCalendar=new FragmentCalendar();
        fragmentAddExercise = new FragmentAddExercise();
        fragmentTransaction=getFragmentManager().beginTransaction();
        infromLogger("3");
        mDBHelper = new ExerciseDBHelper(getActivity());
        infromLogger("4");
        arrayExercise = new ArrayList<>();
        arrayExercise.addAll(Arrays.asList(getResources().getStringArray(R.array.array_exercises)));
        infromLogger("Succes addAll");
        adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                arrayExercise);
        listView.setAdapter(adapter);
    }

    public void initView(View view){
        listView=(ListView)view.findViewById(R.id.listview);
    }
    public  void infromLogger(String statement){
        if(BuildConfig.DEBUG){
            Log.i(Tag,statement);
        }
    }
}
