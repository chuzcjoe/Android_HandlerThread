package com.example.handlerthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ExampleHandlerThread handlerThread = new ExampleHandlerThread();

    private ExampleRunnable1 runnable1 = new ExampleRunnable1();

    private ExampleRunnable2 runnable2 = new ExampleRunnable2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handlerThread.start();
    }

    public void doWork(View v) {
        Message msg = Message.obtain(handlerThread.getHandler());
        msg.what = 1;
        msg.arg1 = 5;
        msg.obj = "Obj String";
        msg.sendToTarget();

        handlerThread.getHandler().postDelayed(runnable1, 2000);
        handlerThread.getHandler().post(runnable2);
    }

    public void removeMessages(View v) {
        // handlerThread.getHandler().removeCallbacksAndMessages(null);
        handlerThread.getHandler().removeCallbacks(runnable1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }

    static private class ExampleRunnable1 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                Log.i(TAG, "Runnable 1: " + i);
                SystemClock.sleep(1000);
            }
        }
    }

    static private class ExampleRunnable2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                Log.i(TAG, "Runnable 2: " + i);
                SystemClock.sleep(1000);
            }
        }
    }
}