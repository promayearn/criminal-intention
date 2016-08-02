package com.augmentis.ayp.criminalintention;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.augmentis.ayp.criminalintention.CrimeDbSchema.CrimeTable;

/**
 * Created by Chayanit on 8/1/2016.
 */
public class CrimesBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 3;

    private static final String DATABASE_NAME = "CrimeBase.db";
    private static final String TAG = "CrimeBaseHelper";

    public CrimesBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Create DataBase");

        sqLiteDatabase.execSQL("CREATE TABLE " + CrimeTable.NAME
                + "("
                + "_id integer primary key autoincrement, "
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED + ","
                + CrimeTable.Cols.SUSPECT + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        Log.d(TAG, "running upgrade db");

//        1 rename table to oldVersion
        sqLiteDatabase.execSQL("alter table " + CrimeTable.NAME + " rename to " + CrimeTable.NAME + "_" + oldVersion);
//        2 drop table
        sqLiteDatabase.execSQL("drop table if exists " + CrimeTable.NAME);
//        3 create new table
        sqLiteDatabase.execSQL("CREATE TABLE " + CrimeTable.NAME +
                "("
                + "_id integer primary key autoincrement, "
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED + ","
                + CrimeTable.Cols.SUSPECT + ")"
        );

//        4 insert data from temp table
        sqLiteDatabase.execSQL("insert into " + CrimeTable.NAME
                + "("
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED
                + ")"
                + " select "
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED
                + " from " + CrimeTable.NAME + "_" + oldVersion
        );

        Log.d(TAG, "insert data from temp table already");
//        5 drop temp table
        sqLiteDatabase.execSQL("drop table if exists " + CrimeTable.NAME + "_" + oldVersion);
    }
}
