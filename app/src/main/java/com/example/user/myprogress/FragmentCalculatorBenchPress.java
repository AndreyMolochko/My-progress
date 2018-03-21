package com.example.user.myprogress;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by User on 12.03.2018.
 */

public class FragmentCalculatorBenchPress extends Fragment implements View.OnClickListener {
    EditText editTextWeight;
    EditText editTextReps;
    TextView textViewResult;
    Button buttonCalculate;
    double weight;
    int reps;
    Formula formula;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_calcul_benchpress,null);
        init(view);
        return view;
    }
    public void init(View view){
        initViews(view);
        initListners(view);
        formula = new Formula();
    }
    public void initViews(View view){
        editTextWeight=(EditText)view.findViewById(R.id.editTextWeightBrech);
        editTextReps=(EditText)view.findViewById(R.id.editTextForRepsPress);
        buttonCalculate=(Button)view.findViewById(R.id.buttonCalculatePressBrench);
        textViewResult = (TextView)view.findViewById(R.id.textViewResultBenchPress);
    }
    public void initListners(View view){
        buttonCalculate.setOnClickListener(this);
    }

    private boolean getDataView(){
        try {
            weight=Double.parseDouble(editTextWeight.getEditableText().toString());
            reps=Integer.parseInt(editTextReps.getEditableText().toString());
            if(isntEmptyView(weight,reps))return true;
            else return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isntEmptyView(Double weight,int reps){
        if(weight!=null && reps!=0)return true;
        return false;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonCalculatePressBrench:

                if(getDataView()){
                    String formattedDouble = new DecimalFormat("#0.00").format(formula.formulaAverage(weight,reps));
                    //String str=String.valueOf(formula.formulaAverage(weight,reps));
                    textViewResult.setText(formattedDouble);
                }
                else Toast.makeText(getActivity(),R.string.incorrectData,Toast.LENGTH_SHORT).show();
                break;
            //case R.id.buttonCompleteExercise:

        }
    }

}
