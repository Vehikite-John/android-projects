package com.example.jdavet.androiddatastoragepractice;

/**
 * Created by jdavet on 11/10/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Helper extends SQLiteOpenHelper {
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContractClassPractice.DB_Entry.TABLE_NAME + " (" +
                    ContractClassPractice.DB_Entry._ID + INT_TYPE + " PRIMARY KEY," +
                    ContractClassPractice.DB_Entry.COL_NUM + INT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractClassPractice.DB_Entry.TABLE_NAME;

    // increment database version when schema is changed
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "dbpractice.db";

    public DB_Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldNewVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
