package com.example.android.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.services.BoundService.SecondActivity;
import com.example.android.services.MessengerService.MyMessengerActivity;
import com.example.android.services.StartedService.MyIntentService;
import com.example.android.services.StartedService.MyStartedService;

public class MainActivity extends AppCompatActivity {

    private TextView txvIntentServiceResult, txvStartedServiceResult;
    private Handler handler = new Handler();
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvIntentServiceResult = findViewById(R.id.txvIntentServiceResult);
        txvStartedServiceResult = findViewById(R.id.txvStartedServiceResult);
    }

    public void startStartedService(View view) {

        Intent intent = new Intent(MainActivity.this, MyStartedService.class);

        intent.putExtra("sleepTime", 10);
        startService(intent);
    }

    public void stopStartedService(View view) {
        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
        stopService(intent);
    }

    public void startIntentService(View view) {

        ResultReceiver myResultReceiver = new MyResultReceiver(null);

        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        intent.putExtra("sleepTime", 10);

        intent.putExtra("receiver", myResultReceiver);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.service.to.activity");
        registerReceiver(myStartedServiceReceiver, intentFilter);
    }

    private BroadcastReceiver myStartedServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("StartServiceResult");
            txvStartedServiceResult.setText(result);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(myStartedServiceReceiver);
    }

    public void moveToSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void moveToMessengerActivity(View view) {
        Intent intent = new Intent(this, MyMessengerActivity.class);
        startActivity(intent);
    }

    // To receive the data back from MyIntentService.java using Result Receiver
    private class MyResultReceiver extends ResultReceiver{

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            Log.i(TAG, "onReceiveResult, Thread " + Thread.currentThread().getName());
            if (resultCode == 18 && resultData != null){
              final  String result = resultData.getString("resultIntentService");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "myHandler Thread"+ Thread.currentThread().getName());
                        txvIntentServiceResult.setText(result);
                    }
                });

            }
        }
    }

}
