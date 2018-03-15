package com.example.user.myprogress;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by User on 14.03.2018.
 */

public class FragmentShowExercise extends Fragment {
    ListView resultListView;
    ArrayList<String>receivedData;
    HashMap<String, String> exercises;
    List<HashMap<String, String>> listItems;
    SimpleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_show_exercises,null);
        receivedData=this.getArguments().getStringArrayList("listExercises");
        resultListView = (ListView)view.findViewById(R.id.listShowExercises);
        exercises = new HashMap<>();
        /*exercises.put("Diana", "3214 Broadway Avenue");
        exercises.put("Tyga", "343 Rack City Drive");*/
        parseReceivedData();
        listItems=new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(), listItems, R.layout.items_list_show_exercises,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.textViewItemName, R.id.textViewSubItem});
        Iterator it = exercises.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }
        resultListView.setAdapter(adapter);


        return  view;
    }

    private void parseReceivedData(){

        for(int i=0;i<receivedData.size();i++){

            String []arr = receivedData.get(i).split("!");
            outputData(arr);
            Log.i("showExerc",i+ "  "+arr[0]+" "+ arr[1]+"  "+arr[2]+" "+arr[3]+"  "+arr[4]+" "+arr[5]);
            Log.i("showExerc",String.valueOf(receivedData.size()));
        }
    }

    private void outputData(String []arr){
        if(arr[4].equals("0"))exercises.put(arr[1],arr[0]+" set "+arr[2] + " weight = "+arr[3]+" kg " + arr[5]+" reps");
        else exercises.put(arr[1],arr[0]+" set "+arr[2] + " weight = "+arr[3]+" pounds "+ arr[5]+" reps");
    }
}
