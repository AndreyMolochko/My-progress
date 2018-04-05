package com.example.user.myprogress;

/**
 * Created by User on 05.04.2018.
 */

public class Coordinates {
    double lon,lat,lon2,lat2;
    double answer=0;
    public Coordinates(double lon,double lat, double lon2, double lat2){
        this.lon=lon;
        this.lat=lat;
        this.lon2=lon2;
        this.lat2=lat2;
    }
    public double getConvertCoordinates(){
        //answer = 2*Math.asin(Math.sqrt(Math.pow(Math.sin((x2-x1)/2),2)+
        //        Math.cos(x1)*Math.cos(x2)*Math.pow(Math.sin((Math.abs(y2-y1))/2),2)));
        answer = 111.2 * Math.sqrt( (lon - lon2)*(lon - lon2) + (lat - lat2)*Math.cos(Math.PI*lon/180)*(lat - lat2)*Math.cos(Math.PI*lon/180));
        return answer;
    }
}
