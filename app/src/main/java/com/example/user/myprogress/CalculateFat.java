package com.example.user.myprogress;

/**
 * Created by User on 21.03.2018.
 */

public class CalculateFat {
    public CalculateFat(){

    }
    public float getPercentageOfFatForMan(float growth, float waist, float neck){
        float answer;
        answer= (float) (86.010*(Math.log10(waist-neck))-70.041*(Math.log10(growth))+30.30);
        return answer;
    }
    public float getPercentageOfFatForWoman(float growth, float waist, float neck, float thigh){
        float answer;
        answer= (float) (163.205*(Math.log10(waist+thigh-neck))-97.684*(Math.log10(growth))-104.912);
        return answer;
    }
}
