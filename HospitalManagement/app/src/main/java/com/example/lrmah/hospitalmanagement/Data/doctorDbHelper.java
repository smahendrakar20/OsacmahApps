package com.example.lrmah.hospitalmanagement.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class doctorDbHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME= doctorDbHelper.class.getSimpleName();
    static  final int DATABASE_VERSION=1;
    public doctorDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //this creates a table in the database
        db.execSQL(HospitalContract.doctorsEntry.CREATE_ENTRIES);//create entries is a string which has the sql statement and execSQL performs that operation
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
