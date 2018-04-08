package com.example.user.myprogress.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 06.04.2018.
 */

public class RunDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="runnings.db";
    private static final int DATABASE_VERSION = 1;
    public RunDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_EXERCISES_NAME = "CREATE TABLE " + RunContract.RunEntry.TABLE_NAME
                +" ("+ RunContract.RunEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RunContract.RunEntry.COLUMN_TIME + " INTEGER NOT NULL, "
                + RunContract.RunEntry.COLUMN_DISTANCE + " INTEGER NOT NULL, "
                + RunContract.RunEntry.COLUMN_DATE + " TEXT NOT NULL );";
        sqLiteDatabase.execSQL(SQL_CREATE_EXERCISES_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("dataBD","refresh from "+ i +" to "+ i1);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RunContract.RunEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
