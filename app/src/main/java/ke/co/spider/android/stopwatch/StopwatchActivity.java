package ke.co.spider.android.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends Activity {
    private boolean running;
    private boolean wasRunning;
    private int seconds = 0;
    final private String RUNNING_STR = "running";
    final private String SECOND_STR = "seconds";
    final private String WAS_RUNNING = "was running";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);


        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt(SECOND_STR);
            running = savedInstanceState.getBoolean(RUNNING_STR);
            wasRunning = savedInstanceState.getBoolean(WAS_RUNNING);
        }
        runTimer();
    }

    @Override
    protected void onStart(){
        super.onStart();

        if (wasRunning) {
            running = true;
        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(RUNNING_STR, running);
        savedInstanceState.putBoolean(WAS_RUNNING, wasRunning);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = ( seconds % 3600 ) / 60;
                int secs = seconds % 60;

                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);

                if ( running ) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });

    }
}
