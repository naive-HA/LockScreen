package com.naiveha.lockscreen;
import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("finish_activity")) {
                    MainActivity.this.finish();
                    System.exit(0);
                }
            }
        }, new IntentFilter("finish_activity"));
        new CountDownTimer(10, 10) {
            public void onTick(long j) {
            }
            public void onFinish() {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SecondaryActivity.class));
            }
        }.start();
    }
}