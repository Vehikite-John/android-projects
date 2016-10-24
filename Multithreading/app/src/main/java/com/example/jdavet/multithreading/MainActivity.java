package com.example.jdavet.multithreading;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String filename = "numbers.txt";
    ListView listView;
    ProgressBar progressBar;
    List<String> fileContents;
    InputStreamReader isr;
    BufferedReader br;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

    public void create(View view) {
        new CreateFileTask().execute();
    }

    class CreateFileTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... args) {
            try {
                OutputStreamWriter osw = new OutputStreamWriter(MainActivity.this.openFileOutput(filename, Context.MODE_PRIVATE));
                for (int i = 1; i <= 10; i++) {
                    String temp = "" + i;
                    if (i < 10) {
                        temp += "\n";
                    }

                    osw.write(temp);
                    Thread.sleep(250);
                    publishProgress(i * 10);

                }
                osw.close();
                return null;
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
            @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setProgress(0);
            Toast.makeText(MainActivity.this, "File \"numbers.txt\" created!", Toast.LENGTH_SHORT).show();
        }
    }

    public void load(View view) {
        new LoadContentsTask().execute(view);
    }

    class LoadContentsTask extends AsyncTask<View, Integer, Void> {
        String dir = getFilesDir().getAbsolutePath() + "/" + filename;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(View... views) {
            try {
                fileContents = new ArrayList();
                InputStream is = new FileInputStream(dir);
                if (is != null) {
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);
                    String line;
                    boolean hasText = true;
                    int i = 1;
                    do {
                        line = br.readLine();
                        Thread.sleep(250);
                        publishProgress(i * 10);
                        i++;
                        if (line != null) {
                            fileContents.add(line);
                        }
                        else {
                            hasText = false;
                        }
                    } while(hasText);
                }

                //String[] test = {"test", "testing", "tester"};
                adapter = new ArrayAdapter(MainActivity.this, R.layout.simple_list_item_1, fileContents);
                // listView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setProgress(0);
            listView.setAdapter(adapter);
            Toast.makeText(MainActivity.this, "Load complete!", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(View view) {
        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>)listView.getAdapter();
        if (adapter != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }
}
