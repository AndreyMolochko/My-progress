package com.example.user.myprogress.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 06.04.2018.
 */

public class Data {
    public Data(){

    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(new Date());
    }
}
