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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myprogress.data.ExerciseContract;
import com.example.user.myprogress.data.ExerciseDBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 06.03.2018.
 */

public class FragmentAddExercise extends Fragment implements View.OnClickListener{

    //@BindView(R.id.buttonAddSet) Button btn;
    //@BindView(R.id.textViewExercise) TextView textViewSet;
    TextView textViewSet;
    private ExerciseDBHelper mDBHelper;
    String nameExercise;
    double weight;
    int type;
    int counterSet=0;
    int reps;
    String date;
    Button buttonAddSet;
    Button buttonCompleteExercise;
    EditText editTextWeight;
    EditText editTextReps;
    Spinner spinnerTypeWeight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_exers,null);
        ButterKnife.bind(view);
        init(view);
        return view;
    }
    public void init(View view){
        initView(view);
        initListners(view);
        initAdapter();
        nameExercise=this.getArguments().getString("titleExercise");
        date=getDate();
        mDBHelper = new ExerciseDBHelper(getActivity());
    }
    public void initView(View view){
         buttonAddSet= (Button)view.findViewById(R.id.buttonAddSet);
         buttonCompleteExercise=(Button)view.findViewById(R.id.buttonCompleteExercise);
         editTextWeight=(EditText)view.findViewById(R.id.editTextWeightBarbell);
         editTextReps=(EditText)view.findViewById(R.id.editTextReps);
         spinnerTypeWeight = (Spinner)view.findViewById(R.id.spinnerForWeight);
         textViewSet = (TextView)view.findViewById(R.id.textViewExercise);
    }
    public void initListners(View view){
        buttonAddSet.setOnClickListener(this);
        buttonCompleteExercise.setOnClickListener(this);
    }
    public void initAdapter(){
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.typeOfWeight, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeWeight.setAdapter(adapter);
    }

    private void displayDatabaseInfo(){
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

        Cursor cursor = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try{
            //textViewSet.setText("Таблица содержит " + cursor.getCount() + " гостей.\n\n");
            Log.i("dataBD","toasta");
        }
        finally {
            cursor.close();
            Log.i("dataBD","toastFinish");
        }
    }

    private boolean getDataView(){
        try {
            weight=Double.parseDouble(editTextWeight.getEditableText().toString());
            reps=Integer.parseInt(editTextReps.getEditableText().toString());
            type=spinnerTypeWeight.getSelectedItemPosition();//0-kilo,1-pounds
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(new Date());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAddSet:if(getDataView()){counterSet++;
                displayDatabaseInfo();
                Toast.makeText(getActivity(),date,Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(),R.string.incorrectData,Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonCompleteExercise:counterSet=0;
                break;
        }
    }
}
