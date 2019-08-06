package com.example.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyStartedService extends Service {

    private static final String TAG = MyStartedService.class.getSimpleName();

    @Override
    public void onCreate() {

        Log.i(TAG, "onCreate, Thread name " + Thread.currentThread().getName());
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand, Thread name " + Thread.currentThread().getName());

        int sleepTime = intent.getIntExtra("sleepTime", 1);

        new MyAsyncTask().execute(sleepTime);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.i(TAG, "onBind, Thread name " + Thread.currentThread().getName());
        return null;
    }

    @Override
    public void onDestroy() {

        Log.i(TAG, "onDestroy, Thread name " + Thread.currentThread().getName());
        super.onDestroy();
    }

    class MyAsyncTask extends AsyncTask<Integer, String, Void>{

        private final String TAG = MyAsyncTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute, Thread " + Thread.currentThread().getName());
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "onPostExecute, Thread " + Thread.currentThread().getName());
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Integer... params) {
            Log.i(TAG, "doInBackground, Thread " + Thread.currentThread().getName());

            int sleepTime = params[0];
            int ctr = 1;
            while (ctr <= sleepTime){
                publishProgress("Counter is now" + ctr);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctr++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.i(TAG, "Counter Value" + values[0] + "onProgressUpdate, " + Thread.currentThread().getName());

            Toast.makeText(MyStartedService.this, values[0], Toast.LENGTH_SHORT).show();
            super.onProgressUpdate(values);
        }
    }
}


