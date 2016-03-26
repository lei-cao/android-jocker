package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends ActionBarActivity {
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke() {

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        JokeAsyncTask.AsyncTaskListener listener = new JokeAsyncTask.AsyncTaskListener() {
            @Override
            public void onPreExecute() {
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPostExecute(final String joke, final Context context) {
                spinner.setVisibility(View.INVISIBLE);
                Intent i = new Intent(context, JokeActivity.class);
                i.putExtra("JOKE_STRING", joke);
                context.startActivity(i);
            }
        };
        new JokeAsyncTask(this, listener).execute();
    }
}
