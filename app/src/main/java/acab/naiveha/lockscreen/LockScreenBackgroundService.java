package acab.naiveha.lockscreen;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class LockScreenBackgroundService extends AccessibilityService {
    private boolean TurnOffScreen = true;
    @Override
    public void onServiceConnected() {
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver(){
            public void onReceive(Context context, Intent intent) {TurnOffScreen = true;}
        }, new IntentFilter("lock_screen"));
    }
    @Override
    public void onInterrupt() {
    }
    @Override
    public void onAccessibilityEvent(@NonNull AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (TurnOffScreen){
                performGlobalAction(8);
                TurnOffScreen = false;
            }
        }
    }
}