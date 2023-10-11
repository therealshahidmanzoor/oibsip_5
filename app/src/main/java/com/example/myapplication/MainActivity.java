package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton, stopButton, holdButton;
    private TextView timeTextView;
    private boolean isRunning = false;
    private long startTime = 0;
    private long elapsedTime = 0;
    private Handler handler = new Handler();
    private Runnable updateTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = findViewById(R.id.timeTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        holdButton = findViewById(R.id.holdButton);

        // Initialize the updateTimer Runnable
        updateTimer = new Runnable() {
            @Override
            public void run() {
                updateTimerText();
                handler.postDelayed(this, 10); // Update every 10 milliseconds
            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    startTime = System.currentTimeMillis() - elapsedTime;
                    handler.post(updateTimer);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    isRunning = false;
                    handler.removeCallbacks(updateTimer);
                }
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                handler.removeCallbacks(updateTimer);
            }
        });
    }

    private void updateTimerText() {
        long currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - startTime;
        int minutes = (int) (elapsedTime / 60000);
        int seconds = (int) (elapsedTime / 1000) % 60;
        int milliseconds = (int) (elapsedTime % 1000);

        String timeString = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
        timeTextView.setText(timeString);
    }
}
