package com.udacity.gradle.builditbigger.jokeactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        View root = this.findViewById(android.R.id.content);
        TextView jokeText = (TextView) root.findViewById(R.id.jokeTextView);

        String joke;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                joke = null;
            } else {
                joke = extras.getString("JOKE_STRING");
            }
        } else {
            joke = (String) savedInstanceState.getSerializable("JOKE_STRING");
        }
        jokeText.setText(joke);
    }
}
