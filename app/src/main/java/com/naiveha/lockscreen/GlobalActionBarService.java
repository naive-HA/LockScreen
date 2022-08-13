package com.naiveha.lockscreen;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class GlobalActionBarService extends AccessibilityService {
    public void onServiceConnected() {
    }
    public void onInterrupt() {
    }
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            performGlobalAction(8);
        }
    }
}
