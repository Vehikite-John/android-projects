package com.example.jdavet.scripturereference;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * creates class that holds values for a scripture and passes it to DisplayScriptureActivity
 *
 * @author John Vehikite
 */
public class ScriptureInput extends AppCompatActivity {
    // constant that holds string that DisplayScriptureActivity will retrieve

    public final static String EXTRA_MESSAGE = "com.example.jdavet.scripturereference.MESSAGE";

    @Override
    /**
     * similar to main(), but this seems required for each activity instead of one per program
     *
     *  @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view by pointing to appropriate xml
        setContentView(R.layout.activity_scripture_input);

        // TextView to display instructions to user
        TextView textView = (TextView) findViewById(R.id.appInstructions);
        textView.setText("Enter your favorite scripture and press the Share button.");

        // set text size of TextView
        textView.setTextSize(20);
    }

    /**
     * Called when the user clicks the Share button
     * connected via activity_scripture_input.xml (Button element, onclick attribute)
     * @param view
     */
    public void sendMessage(View view) {
        // An Intent is an object that provides runtime binding between
        // separate components (such as two activities).
        // Source: https://developer.android.com/training/basics/firstapp/starting-activity.html
        // 2 params for intent: Context (Activity is subclass of Context) and
        // the class where the intent is delivered
        Intent intent = new Intent(this, DisplayScriptureActivity.class);

        // get information from EditText fields
        EditText editText = (EditText) findViewById(R.id.edit_book);
        EditText editText2 = (EditText) findViewById(R.id.edit_chapter);
        EditText editText3 = (EditText) findViewById(R.id.edit_verse);

        // output string to be sent as an intent to DisplayScriptureActivity
        String message = "Your favorite scripture is: " + editText.getText().toString() + " " +
                editText2.getText().toString() + ":" + editText3.getText().toString() + ".";

        // The putExtra() method adds the EditText's value to the intent. An Intent can carry data
        // types as key-value pairs called extras. Your key is a public constant EXTRA_MESSAGE
        // because the next activity uses the key to retrieve the text value.
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
