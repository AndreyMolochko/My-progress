package com.example.user.myprogress;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myprogress.data.ExerciseContract;
import com.example.user.myprogress.data.RunContract;
import com.example.user.myprogress.data.RunDBHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 09.04.2018.
 */

public class FragmentRunResult extends Fragment {
    GraphView graph;
    HashMap<String,ArrayList<Integer>>distanceDays;

    private RunDBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_result_run,null);
        graph = (GraphView) view.findViewById(R.id.graph);
        dbHelper = new RunDBHelper(getActivity());
        distanceDays = new HashMap<>();
        Log.i("RUNRESULT","before");
        setDataDB();
        DataPoint []dataPoint=
        new DataPoint[] {
                new DataPoint(0, 100),
                new DataPoint(1, 150),
                new DataPoint(2, 200),
                new DataPoint(3, 120),
                new DataPoint(4, distanceDays.size())
        };
        //graph.addSeries(series);
        drawGraphics(dataPoint);
        return view;
    }
    public void drawGraphics(DataPoint []dataPoints){
        LineGraphSeries<DataPoint>seties = new LineGraphSeries<>(dataPoints);
        graph.addSeries(seties);
    }

    private void setDataDB(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String []projection={
                RunContract.RunEntry.COLUMN_DATE,
                RunContract.RunEntry.COLUMN_DISTANCE
        };
        String selection = RunContract.RunEntry.COLUMN_DATE + "?=";
        Cursor cursor = db.query(
                RunContract.RunEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        try{
                int maxDistanceDay;
                int idDistance = cursor.getColumnIndex(RunContract.RunEntry.COLUMN_DISTANCE);
                int idDate = cursor.getColumnIndex(RunContract.RunEntry.COLUMN_DATE);
                cursor.moveToFirst();
                int distanse=cursor.getInt(idDistance);
                String lastDate = cursor.getString(idDate);
                Log.i("RUNRESULT1",lastDate);
                ArrayList<Integer>distanses=new ArrayList<Integer>();
                maxDistanceDay=cursor.getInt(idDistance);
                int i=0;
                int j=0;
                while(cursor.moveToNext()){
                    i++;
                    Log.i("RUNRESULTQ",lastDate + "     "+i);
                    int distanseInd = cursor.getInt(idDistance);
                    String dateInd=cursor.getString(idDate);
                    if(maxDistanceDay<distanseInd)maxDistanceDay=distanseInd;
                    //String content = String.valueOf(distanseInd)+"!"+dateInd;
                    if(!dateInd.equals(lastDate)){
                        Log.i("RUNRESULT2",lastDate+"     "+dateInd);
                        distanceDays.put(lastDate,new ArrayList<Integer>(distanses));
                        distanses.clear();
                        j++;
                        Log.i("RUNRESULT","jjjj = "+j);
                        Log.i("RUNRESULTMA",String.valueOf(maxDistanceDay));
                        lastDate=dateInd;
                        maxDistanceDay=0;
                    }
                    else if(cursor.getCount()-1==i){
                        distanses.add(distanseInd);
                        distanceDays.put(dateInd,new ArrayList<Integer>(distanses));
                    }
                    else distanses.add(distanseInd);

                }
            Log.i("RUNRESULTT",distanceDays.get("06.04.2018").get(0).toString());
            Log.i("RUNRESULTT",distanceDays.get("18.04.2018").get(0).toString());

            Log.i("RUNRESULT","jjjj = "+distanceDays.size());
        }
        finally {
            cursor.close();
        }
    }

}
