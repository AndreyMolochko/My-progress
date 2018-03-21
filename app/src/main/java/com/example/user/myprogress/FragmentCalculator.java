package com.example.user.myprogress;

import android.app.Fragment;
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

import java.text.DecimalFormat;

import butterknife.BindView;

/**
 * Created by User on 05.03.2018.
 */

public class FragmentCalculator extends Fragment implements View.OnClickListener {
    @BindView(R.id.spinnerForSex)Spinner spinnerSex;
    @BindView(R.id.editTextForGrowth)EditText editTextForGrowth;
    @BindView(R.id.editTextForNeck)EditText editTextForNeck;
    @BindView(R.id.editTextForWaist)EditText editTextForWaist;
    @BindView(R.id.editTextForThigh)EditText editTextForThigh;
    @BindView(R.id.buttonForShowingResult)Button buttonForShowingResult;
    @BindView(R.id.textViewResult)TextView textViewResult;
    CalculateFat calculateFat;
    String dataForSpinner []={"Male","Female"};
    Boolean sex=true;//true - male, false - female
    Float growth,waist,neck,thigh;
    String Tag = "CalculatorFats";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_calculator_fats,null);
        init(view);
        return view;
    }
    public void init(View view){
        initView(view);
        initAdapterSpinner();
        calculateFat = new CalculateFat();
        buttonForShowingResult.setOnClickListener(this);
    }
    public void initView(View view){
        spinnerSex=(Spinner)view.findViewById(R.id.spinnerForSex);
        editTextForGrowth=(EditText)view.findViewById(R.id.editTextForGrowth);
        editTextForNeck=(EditText)view.findViewById(R.id.editTextForNeck);
        editTextForWaist=(EditText)view.findViewById(R.id.editTextForWaist);
        editTextForThigh=(EditText)view.findViewById(R.id.editTextForThigh);
        buttonForShowingResult=(Button)view.findViewById(R.id.buttonForShowingResult);
        textViewResult=(TextView)view.findViewById(R.id.textViewResult);
    }
    public void initAdapterSpinner(){
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,dataForSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapter);
        spinnerSex.setPrompt("Male");
    }

    @Override
    public void onClick(View view) {
        String answer;
        switch (view.getId()){
            case R.id.buttonForShowingResult:
                if(inicializationParameteres()) {

                    if (sex == true) {
                        answer = new DecimalFormat("#0.00").format(calculateFat.getPercentageOfFatForMan(growth, waist, neck));
                    } else answer = new DecimalFormat("#0.00").format(calculateFat.getPercentageOfFatForWoman(growth, waist, neck, thigh));

                    textViewResult.setText(answer);
                }
                else Toast.makeText(getActivity(),"You entered incorrect data",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public boolean inicializationParameteres(){
        try {
            if(spinnerSex.getSelectedItemPosition()==0)sex=true;
            else sex=false;
            growth = Float.parseFloat(String.valueOf(editTextForGrowth.getEditableText()));
            waist = Float.parseFloat(String.valueOf(editTextForWaist.getEditableText()));
            neck = Float.parseFloat(String.valueOf(editTextForNeck.getEditableText()));
            thigh = Float.parseFloat(String.valueOf(editTextForThigh.getEditableText()));
            infromLogger("Lucky parsing");
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            infromLogger("exception parse");
            return false;
        }
        //infromLogger(Float.toString(growth));
    }
    public  void infromLogger(String statement){
        if(BuildConfig.DEBUG){
            Log.i(Tag,statement);
        }
    }
}
