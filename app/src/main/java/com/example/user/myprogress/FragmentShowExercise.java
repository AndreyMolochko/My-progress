package com.example.user.myprogress;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    HashMap<String, String> nameAddresses;
    List<HashMap<String, String>> listItems;
    SimpleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_show_exercises,null);
        resultListView = (ListView)view.findViewById(R.id.listShowExercises);
        nameAddresses = new HashMap<>();
        nameAddresses.put("Diana", "3214 Broadway Avenue");
        nameAddresses.put("Tyga", "343 Rack City Drive");
        listItems=new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(), listItems, R.layout.items_list_show_exercises,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.textViewItemName, R.id.textViewSubItem});
        Iterator it = nameAddresses.entrySet().iterator();
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
}
