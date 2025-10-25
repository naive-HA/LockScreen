package acab.naiveha.lockscreen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secondary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Context context = this;
        String prefString = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (prefString != null && prefString.contains(context.getPackageName() + "/" + LockScreenBackgroundService.class.getCanonicalName())) {
            LocalBroadcastManager.getInstance(SecondaryActivity.this).sendBroadcast(new Intent("finish_activity"));
            SecondaryActivity.this.finish();
        }
    }
    public void navigateToSettings(View view){
        Intent settings = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(settings);
        LocalBroadcastManager.getInstance(SecondaryActivity.this).sendBroadcast(new Intent("finish_activity"));
        SecondaryActivity.this.finish();
    }
    public void maybeLater(View view){
        LocalBroadcastManager.getInstance(SecondaryActivity.this).sendBroadcast(new Intent("finish_activity"));
        SecondaryActivity.this.finish();
    }

    public void copyToClipboard(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("BTC Address", "1HwgShr1TniuBxNQwy2xAhpQaNuZhtw6sh");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "BTC Address copied to clipboard", Toast.LENGTH_SHORT).show();
    }

}