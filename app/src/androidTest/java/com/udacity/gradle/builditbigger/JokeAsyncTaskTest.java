package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by leicao on 25/2/16.
 */
public class JokeAsyncTaskTest extends InstrumentationTestCase implements JokeAsyncTask.AsyncTaskOnPostExecuteListener {
    CountDownLatch signal;
    Context context;
    JokeAsyncTask task;

    @Override
    protected void setUp() throws Exception {
        Log.d(getClass().getSimpleName(), "Joke : ");
        super.setUp();
        // create  a signal to let us know when our task is done.
        signal = new CountDownLatch(1);
        context = getInstrumentation().getTargetContext();
        task = new JokeAsyncTask(context, JokeAsyncTaskTest.this);
    }

    public final void testSuccessfulFetch() throws Throwable {

        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                task.execute();
            }
        });

        /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(30, TimeUnit.SECONDS);
    }


    @Override
    public void onPostExecute(String joke, Context context) {
        Log.d(getClass().getSimpleName(), "Joke : " + joke);
        if (joke.equals("")) {
            assertTrue(false);
        } else {
            assertTrue(true);
        }
        /* This is the key, normally you would use some type of listener
         * to notify your activity that the async call was finished.
         *
         * In your test method you would subscribe to that and signal
         * from there instead.
         */
        signal.countDown();

    }
}
