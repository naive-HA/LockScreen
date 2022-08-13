package com.naiveha.lockscreen;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SecondaryActivity extends AppCompatActivity {
    public void onCreate(Bundle bundle) {
        Context context = this;
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_secondary);
        new CountDownTimer(10, 10) {
            public void onTick(long j) {
            }
            public void onFinish() {
                String prefString = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (prefString != null && prefString.contains(context.getPackageName() + "/" + GlobalActionBarService.class.getCanonicalName())) {
                    SecondaryActivity.this.sendBroadcast(new Intent("finish_activity"));
                    SecondaryActivity.this.finish();
                }
            }
        }.start();
    }
    public void navigateToSettings(View view){
        Intent settings = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(settings);
        SecondaryActivity.this.sendBroadcast(new Intent("finish_activity"));
        SecondaryActivity.this.finish();
    }
}
