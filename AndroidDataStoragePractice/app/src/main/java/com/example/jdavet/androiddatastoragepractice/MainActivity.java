package com.example.jdavet.androiddatastoragepractice;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    // SharedPreferences will be saved in a file named MyPrefsFile
    // public static final String PREFS_NAME = "MyPrefsFile";

    TextView tv;

    // create db
    DB_Helper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        // get SharedPreferences from file named MyPrefsFile
        // SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);

        // set num to savedNumber if SharedPreferences exists
        // if not, set num to 0
        // int num = sp.getInt("savedNumber", 0);

        int num;
        // instantiate db
        dbh = new DB_Helper(this);
        if (checkDB()) {
            num = getNumber();
        }
        else {
            num = 0;
        }
        tv.setText(Integer.toString(num));
    }

    /**
     * saves number in DB - SharedPreferences done, but commented out
     *
     * @param view
     */
    public void saveNumber(View view) {
        // get number from text view
        int number = Integer.parseInt(tv.getText().toString());

        // Code to save int to SharedPreferences
        // Alternate way to write the first line of code below
        // SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
        // SharedPreferences.Editor editor = sp.edit();
        // SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        // editor.putInt("savedNumber", number);
        // editor.commit();

        // UPDATE if db exists, INSERT if it doesn't
        if (checkDB()) {
            updateNumber(number);
        }
        else {
            putNumber(number);
        }
    }

    /**
     * Adds 1 to the number in the text view
     *
     * @param view
     */
    public void advanceNumber(View view) {
        int number = Integer.parseInt(tv.getText().toString());
        tv.setText(Integer.toString(++number));
    }

    /**
     * for DB - get number in only row
     * @return
     */
    public int getNumber() {
        int number = 0;
        // create readable sqlite db
        SQLiteDatabase sqLiteDatabase = dbh.getReadableDatabase();

        // Define a projection that specifies which columns
        // you will actually use after this query.
        String[] projection = {
                ContractClassPractice.DB_Entry._ID,
                ContractClassPractice.DB_Entry.COL_NUM
        };

        // Filter results WHERE "id" = 1
        String selection = ContractClassPractice.DB_Entry._ID + " =?";
        String[] selectionArgs = { "1" };

        Cursor cursor = sqLiteDatabase.query(
                ContractClassPractice.DB_Entry.TABLE_NAME,  // The table to query
                projection,                                 // columns to return
                selection,                                  // columns for WHERE clause
                selectionArgs,                              // values for WHERE clause
                null,                                       // group by rows
                null,                                       // filter by row groups
                null                                        // sort order
        );
        cursor.moveToFirst();   // go to first result of query

        // get number from 'number' column
        number = cursor.getInt(cursor.getColumnIndex(ContractClassPractice.DB_Entry.COL_NUM));
        return number;
    }

    /**
     * INSERT into DB
     *
     * @param number
     */
    public void putNumber(int number) {
        // getWriteableDatabase() should be called with AsyncTask
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();

        // Create a new map of values; column names are the keys
        ContentValues values = new ContentValues();
        values.put(ContractClassPractice.DB_Entry.COL_NUM, number);

        // insert new row while returning primary key value of the new row
        long newRowId = sqLiteDatabase.insert(ContractClassPractice.DB_Entry.TABLE_NAME, null, values);
    }

    /**
     * UPDATE 1st (and only) row of DB
     *
     * @param number
     */
    public void updateNumber(int number) {
        // getReadableDatabase() should be called with AsyncTask
        SQLiteDatabase sqLiteDatabase = dbh.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ContractClassPractice.DB_Entry.COL_NUM, number);

        // update 1st row
        String selection = ContractClassPractice.DB_Entry._ID + " LIKE ?";
        String[] selectionArgs = { "1" };

        int count = sqLiteDatabase.update(
                ContractClassPractice.DB_Entry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    /**
     * check for existence of db
     *
     * @return
     */
    private boolean checkDB() {
        File dbFile = this.getDatabasePath("dbpractice.db");
        return dbFile.exists();
    }

    /**
     * sets number to 0 and updates Text View
     *
     * @param view
     */
    public void resetNumber(View view) {
        updateNumber(0);
        tv.setText("0");
    }
}
