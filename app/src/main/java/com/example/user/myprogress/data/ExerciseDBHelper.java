package com.example.user.myprogress.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 09.03.2018.
 */

public class ExerciseDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="exercise.db";
    private static final int DATABASE_VERSION = 6;
    public ExerciseDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("dataBD","3");
            String SQL_CREATE_EXERCISES_NAME = "CREATE TABLE " + ExerciseContract.ExerciseEntry.TABLE_NAME
                    +" ("+ ExerciseContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ExerciseContract.ExerciseEntry.COLUMN_NAME + " TEXT NOT NULL, "
                    + ExerciseContract.ExerciseEntry.COLUMN_WEIGHT + " INTEGER NOT NULL, "
                    + ExerciseContract.ExerciseEntry.COLUMN_TYPE + " INTEGER NOT NULL DEFAULT 0, "
                    + ExerciseContract.ExerciseEntry.COLUMN_SET + " INTEGER NOT NULL, "
                    + ExerciseContract.ExerciseEntry.COLUMN_REP + " INTEGER NOT NULL, "
                    + ExerciseContract.ExerciseEntry.COLUMN_DATE + " TEXT NOT NULL );";
            sqLiteDatabase.execSQL(SQL_CREATE_EXERCISES_NAME);
        Log.i("dataBD","4");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("dataBD","refresh from "+ i +" to "+ i1);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ExerciseContract.ExerciseEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
