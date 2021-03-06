package com.example.jdavet.sharedpreferencespractice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // SharedPreferences will be saved in a file named MyPrefsFile
    public static final String PREFS_NAME = "MyPrefsFile";

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        // get SharedPreferences from file named MyPrefsFile
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);

        // set num to savedNumber if SharedPreferences exists
        // if not, set num to 0
        int num = sp.getInt("savedNumber", 0);

        tv.setText(Integer.toString(num));
    }

    /**
     * saves number SharedPreferences
     *
     * @param view
     */
    public void saveNumber(View view) {
        // get number from text view
        int number = Integer.parseInt(tv.getText().toString());

        // Code to save int to SharedPreferences
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("savedNumber", number);
        editor.commit();
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
     * sets number in SharedPreferences to 0 and sets text view number
     * to 0
     *
     * not in requirements for Teach 09
     *
     * @param view
     */
    public void resetNumber(View view) {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("savedNumber", 0);
        editor.commit();
        tv.setText("0");
    }
}