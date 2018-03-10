package com.example.user.myprogress.data;

import android.provider.BaseColumns;

/**
 * Created by User on 09.03.2018.
 */

public final class ExerciseContract {
    private ExerciseContract(){

    };
    public static final class ExerciseEntry implements BaseColumns{
        public final static String TABLE_NAME = "exercises";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_WEIGHT = "weight";
        public final static String COLUMN_TYPE = "type";
        public final static String COLUMN_SET = "sets";
        public final static String COLUMN_REP = "rep";
        public final static String COLUMN_DATE="date";

        public static final int TYPE_KILO = 0;
        public static final int TYPE_POUNDS=1;
    }
}
