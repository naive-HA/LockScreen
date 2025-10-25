package acab.naiveha.lockscreen;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver(){
            public void onReceive(Context context, Intent intent) {
                MainActivity.this.finish();
                System.exit(0);
            }
        }, new IntentFilter("finish_activity"));
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent("lock_screen"));
        MainActivity.this.startActivity(new Intent(MainActivity.this, SecondaryActivity.class));
    }
    public void navigateToSettings(View view){
        Intent settings = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(settings);
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent("finish_activity"));
        MainActivity.this.finish();
    }
    public void maybeLater(View view){
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent("finish_activity"));
        MainActivity.this.finish();
    }

    public void copyToClipboard(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("BTC Address", "1HwgShr1TniuBxNQwy2xAhpQaNuZhtw6sh");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "BTC Address copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}
