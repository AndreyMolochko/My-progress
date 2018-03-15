package com.example.user.myprogress;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.user.myprogress.R;
import com.example.user.myprogress.data.ExerciseContract;
import com.example.user.myprogress.data.ExerciseDBHelper;

import java.util.ArrayList;

/**
 * Created by User on 14.03.2018.
 */

public class FragmentCalendar extends Fragment {
    CalendarView calendarView;
    String selectedDate;
    ArrayList<String>dataExercises;
    FragmentTransaction fragmentTransaction;
    FragmentShowExercise fragmentShowExercise;
    private ExerciseDBHelper mDBHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_calendar,null);
        mDBHelper = new ExerciseDBHelper(getActivity());
        calendarView = (CalendarView)view.findViewById(R.id.calendarView);
        fragmentShowExercise = new FragmentShowExercise();
        fragmentTransaction = getFragmentManager().beginTransaction();
        dataExercises = new ArrayList<>();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int mYear = i;
                String mMonth;//=String.valueOf(i1+1);
                if(i1+1<10)mMonth="0"+(i1+1);
                else mMonth = String.valueOf(i1+1);
                //mMonth = String.valueOf(i1+1);
                int mDay = i2;
                selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth).append(".").append(mYear)
                        .append("").toString();
                if(displayDatabaseInfo(selectedDate)) {//Toast.makeText(getActivity(), selectedDate, Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("listExercises",dataExercises);
                    fragmentShowExercise.setArguments(bundle);
                    fragmentTransaction.replace(R.id.layoutForAddExercise,fragmentShowExercise);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }
    private boolean displayDatabaseInfo(String clickData){
        dataExercises.clear();
        Log.i("dataBD","1");
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Log.i("dataBD","after");
        String [] projection  ={
                ExerciseContract.ExerciseEntry._ID,
                ExerciseContract.ExerciseEntry.COLUMN_NAME,
                ExerciseContract.ExerciseEntry.COLUMN_WEIGHT,
                ExerciseContract.ExerciseEntry.COLUMN_TYPE,
                ExerciseContract.ExerciseEntry.COLUMN_SET,
                ExerciseContract.ExerciseEntry.COLUMN_REP,
                ExerciseContract.ExerciseEntry.COLUMN_DATE
        };
        String selection = ExerciseContract.ExerciseEntry.COLUMN_DATE + "=?";
        String[] selectionArgs = {clickData};

        Cursor cursor = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        try{
            if(cursor.getCount()>0){
                int idIndex = cursor.getColumnIndex(ExerciseContract.ExerciseEntry._ID);
                int idName = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_NAME);
                int idWeight = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_WEIGHT);
                int idType = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_TYPE);
                int idSet = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_SET);
                int idRep = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_REP);
                int idDate = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_DATE);
                while(cursor.moveToNext()){
                    int ind = cursor.getInt(idIndex);
                    int typeInd = cursor.getInt(idType);
                    int setInd = cursor.getInt(idSet);
                    int repInd = cursor.getInt(idRep);
                    String nameInd=cursor.getString(idName);
                    String dateInd=cursor.getString(idDate);
                    double weightInd = cursor.getDouble(idWeight);
                    String valWeigth = String.valueOf(weightInd);
                    dataExercises.add(
                            dateInd+"!"
                            +nameInd+"!"
                            +setInd+"!"
                            +valWeigth+"!" +
                            typeInd+"!"+
                                    repInd
                    );
                }
                return true;
            }
            else return false;
        }
        finally {
            cursor.close();
            Log.i("dataBD","toastFinish");
        }
    }
}
