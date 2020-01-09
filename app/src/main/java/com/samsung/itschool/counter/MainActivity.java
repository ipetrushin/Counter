package com.samsung.itschool.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    int seconds = 0;
    CountTask task = new CountTask();

    // теория http://developer.alexanderklimov.ru/android/theory/asynctask.php
    class CountTask extends AsyncTask<Void, Void, Void> {
        boolean isRunning = true;
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("mytag", "count started");
            // пока флаг включен, цикл выполняется
            while (isRunning){
                try { Thread.sleep(1000); } catch (InterruptedException e) { /* do nothing */ }
                Log.d("mytag", "seconds = "+seconds);
                seconds ++;
            }

            Log.d("mytag", "count finished");
            return null;
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            isRunning = false;
            Log.d("mytag", "onCancelled");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v) {
         switch (task.getStatus()) { // проверяем состояние задания
             // запущен
             case RUNNING: task.isRunning = false; break;
             // выполнен, нужно создать объект заново
             case FINISHED: task = new CountTask();
             // ожидает запуска
             case PENDING:  task.execute();
         }
    }
}
