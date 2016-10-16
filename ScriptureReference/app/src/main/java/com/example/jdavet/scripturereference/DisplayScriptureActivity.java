package com.example.jdavet.scripturereference;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * class that gets intent from ScriptureInput and displays info to user
 */
public class DisplayScriptureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_scripture);

        // gets intent
        Intent intent = getIntent();

        // store string from intent sent from ScriptureInput
        String message = intent.getStringExtra(ScriptureInput.EXTRA_MESSAGE);

        // Create TextView object that will display EXTRA_MESSAGE to user
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // You add the TextView to the layout identified by R.id.activity_display_message.
        // You cast the layout to ViewGroup because it is the superclass of all layouts
        // and contains the addView() method.
        // Source: https://developer.android.com/training/basics/firstapp/starting-activity.html#CreateActivity
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_scripture);
        layout.addView(textView);
    }
}
