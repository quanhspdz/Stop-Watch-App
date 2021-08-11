package com.example.progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button btnStart, btnReset;
    TextView txtTienTrinh;
    boolean startIsClicked = false, resetIsClicked = false;
    long time = 0, startTime, pauseTime, startPauseTime = 0, oldTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        txtTienTrinh = findViewById(R.id.txtTienTrinh);

        txtTienTrinh.setText(convertToHours(time));
        startTime = System.nanoTime();

        Handler handler = new Handler();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startIsClicked) {
                    startIsClicked = false;
                    oldTime = time;
                    btnStart.setText("Start");
                }
                else {
                    startTime = System.nanoTime();
                    startIsClicked = true;
                    btnStart.setText("Stop");
                }
                runLoop(0);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIsClicked = true;
                reset();
            }
        });
    }

    private void runLoop(int time_ms) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (startIsClicked) {
                    time = oldTime + System.nanoTime() - startTime;
                    txtTienTrinh.setText(convertToHours(time));
                    if (startIsClicked && resetIsClicked == false) runLoop(time_ms);
                }
            }
        }, time_ms);
    }
    private void reset() {
        startTime = System.nanoTime();
        btnStart.setText("Start");
        startIsClicked = false;
        time = 0;
        oldTime = 0;
        txtTienTrinh.setText(convertToHours(time));
        resetIsClicked = false;
    }
    private String convertToHours(long time) {
        long hour = 0, minute = 0, second = 0, msecond;
        time /= 10000000;
        hour = time / 360000;
        minute = time % 360000 / 6000;
        second = time % 360000 % 6000 / 100;
        msecond = time % 360000 % 6000 % 100;

        return hour + "h " + minute + "m " + second + "s " + msecond;
    }
}

