package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.leicao.jokes.backend.jokeApi.JokeApi;

import java.io.IOException;

public class JokeAsyncTask extends AsyncTask<Context, Void, String> {
    private static JokeApi jokeApi = null;
    private Context context;
    private AsyncTaskOnPostExecuteListener listener;

    public JokeAsyncTask(Context context, AsyncTaskOnPostExecuteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Context... params) {
        if(jokeApi == null) {
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.3.2:8181/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            jokeApi = builder.build();
        }

        try {
            return jokeApi.tell().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onPostExecute(result, context);
    }

    public interface AsyncTaskOnPostExecuteListener {
        void onPostExecute(String joke, Context context);
    }
}

