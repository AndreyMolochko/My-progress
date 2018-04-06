package com.example.user.myprogress.data;

import android.provider.BaseColumns;

/**
 * Created by User on 06.04.2018.
 */

public final class RunContract {
        private RunContract(){

        };

        public static final class RunEntry implements BaseColumns{
            public final static String TABLE_NAME = "runnings";

            public final static String _ID = BaseColumns._ID;
            public final static String COLUMN_TIME = "time";
            public final static String COLUMN_DISTANCE = "distance";
            public final static String COLUMN_DATE="date";
        }
}
