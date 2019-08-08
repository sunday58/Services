package com.example.android.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyWorkerThread");
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate, Thread " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent, Thread " + Thread.currentThread().getName());

        int sleepTime = intent.getIntExtra("sleepTime", 1);

        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");

        int ctr = 1;
        while (ctr <= sleepTime){
            Log.i(TAG, "Counter is now" + ctr);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctr++;
        }
        Bundle bundle = new Bundle();
        bundle.putString("resultIntentService", "Counter stopped at " + ctr + " seconds");
        resultReceiver.send(18, bundle);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy, Thread " + Thread.currentThread().getName());
        super.onDestroy();
    }
}
