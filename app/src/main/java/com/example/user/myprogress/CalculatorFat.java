package com.example.user.myprogress;

/**
 * Created by User on 05.03.2018.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculatorFat extends AppCompatActivity {
    String dataForSpinner []={"Male","Female"};
    Boolean sex=true;//true - male, false - female
    Float growth,waist,neck,thigh,weight;
    @BindView(R.id.spinnerForSex)Spinner spinnerSex;
    @BindView(R.id.editTextForGrowth)EditText editTextForGrowth;
    @BindView(R.id.editTextForNeck)EditText editTextForNeck;
    @BindView(R.id.editTextForWaist)EditText editTextForWaist;
    @BindView(R.id.editTextForThigh)EditText editTextForThigh;
    @BindView(R.id.editTextForWeight)EditText editTextForWeight;
    @BindView(R.id.buttonForShowingResult)Button buttonForShowingResult;
    @BindView(R.id.textViewResult)TextView textViewResult;
    String Tag = "CalculatorFats";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_fat);
        ButterKnife.bind(this);
        ArrayAdapter<String>adapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataForSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapter);
        spinnerSex.setPrompt("Male");
    }
    private float getPercentageOfFatForMan(float growth, float waist, float neck){
        float answer;
        answer= (float) (86.010*(Math.log10(waist-neck))-70.041*(Math.log10(growth))+30.30);
        return answer;
    }
    private float getPercentageOfFatForWoman(float growth, float waist, float neck, float thigh){
        float answer;
        answer= (float) (163.205*(Math.log10(waist+thigh-neck))-97.684*(Math.log10(growth))-104.912);
        return answer;
    }
    @OnClick({R.id.buttonForShowingResult})
    public void showResult(){
        String answer;
        if(inicializationParameteres()) {
            if (sex == true) {
                answer = Float.toString(getPercentageOfFatForMan(growth, waist, neck));
            } else answer = Float.toString(getPercentageOfFatForWoman(growth, waist, neck, thigh));
            textViewResult.setText(answer);
        }
        else Toast.makeText(getApplicationContext(),"You entered incorrect data",
                Toast.LENGTH_SHORT).show();
    }

    public boolean inicializationParameteres(){
        try {
            if(spinnerSex.getSelectedItemPosition()==0)sex=true;
            else sex=false;
            growth = Float.parseFloat(String.valueOf(editTextForGrowth.getEditableText()));
            waist = Float.parseFloat(String.valueOf(editTextForWaist.getEditableText()));
            neck = Float.parseFloat(String.valueOf(editTextForNeck.getEditableText()));
            thigh = Float.parseFloat(String.valueOf(editTextForThigh.getEditableText()));
            weight = Float.parseFloat(String.valueOf(editTextForWeight.getEditableText()));
            infromLogger("Lucky parsing");
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            infromLogger("exception parse");
            return false;
        }
        //infromLogger(Float.toString(growth));
    }
    public Boolean isCheckingCorrectEnter(){
        try {
            Float.parseFloat(String.valueOf(editTextForNeck.getEditableText()));
            Float.parseFloat(String.valueOf(editTextForWaist.getEditableText()));
            return true;
        } catch (NumberFormatException e) {
            infromLogger("mistake in parse");
            return false;

        }
    }
    //проверка на правильный ввод
    //проверка на ввод всех значений
    public  void infromLogger(String statement){
        if(BuildConfig.DEBUG){
            Log.i(Tag,statement);
        }
    }

}
