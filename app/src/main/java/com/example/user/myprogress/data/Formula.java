package com.example.user.myprogress.data;

import java.text.Normalizer;

/**
 * Created by User on 13.03.2018.
 */

public class Formula {
    public Formula(){

    }
    private double formulaEpley(double weight,int reps){//weight of barbell
        return (weight*reps/30)+weight;//return maximal weight of barbell, which user can do
    }

    private double formulaMattBrzycki(double weight,int reps){
        return weight*(36/(37-reps));
    }

    private double formulaLander(double weight,int reps){
        return (100*weight)/(-2.67123*reps+101.3);
    }

    private double formulaOConner(double weight,int reps){
        return weight*(0.025*reps+1);
    }

    public double formulaAverage(double weight,int reps){//if(reps<30)
        return (formulaEpley(weight,reps)+formulaLander(weight,reps)
                +formulaMattBrzycki(weight, reps)+formulaOConner(weight, reps))/4;
    }
}
