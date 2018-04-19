package com.example.user.myprogress;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 14.04.2018.
 */

public class RunningDay {


    public RunningDay(ArrayList<Integer>distancesDay){

    }
    public int maxDistanceDay (ArrayList<Integer>distancesDay){
        int max=0;
        for(int i=0;i<distancesDay.size();i++){
            if(distancesDay.get(i)>max)max=distancesDay.get(i);
        }
        return max;
    }
}
