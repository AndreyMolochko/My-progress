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
    Map<String, String> exercises;
    //List<Exercise> listExercises;
    List<Map<String, String>> listItems;
    //List<List<Exercise>> listItems;
    SimpleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_show_exercises,null);
        init(view);
        return  view;
    }

    public void init(View view){
        receivedData=this.getArguments().getStringArrayList("listExercises");
        resultListView = (ListView)view.findViewById(R.id.listShowExercises);
        exercises = new HashMap<>();
        parseReceivedData();
        listItems=new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(), listItems, R.layout.items_list_show_exercises,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.textViewItemName, R.id.textViewSubItem});
        Iterator it = exercises.entrySet().iterator();
        while (it.hasNext()){
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            String []str =pair.getValue().toString().split("!");
            resultsMap.put("First Line", str[0]);
            resultsMap.put("Second Line", str[1]);
            listItems.add(resultsMap);
        }
        resultListView.setAdapter(adapter);
        /*receivedData=this.getArguments().getStringArrayList("listExercises");
        resultListView = (ListView)view.findViewById(R.id.listShowExercises);
        listExercises = new ArrayList<Exercise>();
        parseReceivedData();
        listItems=new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(),listItems,R.layout.items_list_show_exercises,)*/
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
        Log.i("showExerc","outputDataDInamicididiidi  "+arr[0]);
        if(arr[5].equals("0"))exercises.put(arr[0],arr[2]+"!"+arr[1]+" set "+arr[3] + " weight = "+arr[4]+" kg " + arr[6]+" reps");
        else exercises.put(arr[0],arr[2]+"!"+arr[1]+" set "+arr[3] + " weight = "+arr[4]+" pounds "+ arr[6]+" reps");
        /*if(arr[4].equals("0"))listExercises.add(new Exercise(arr[1],arr[0]+" set "+
                arr[2] + " weight = "+arr[3]+" kg " + arr[5]+" reps"));
        else listExercises.add(new Exercise(arr[1],arr[0]+" set "+arr[2] +
                 " weight = "+arr[3]+" pounds "+ arr[5]+" reps"));*/
    }
}
