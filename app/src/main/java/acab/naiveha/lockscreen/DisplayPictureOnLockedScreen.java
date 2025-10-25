package acab.naiveha.lockscreen;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class DisplayPictureOnLockedScreen extends AppCompatActivity {
    private boolean justCreated = true;
    private ImageView fullScreenImage;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    // Variables for panning
    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId = MotionEvent.INVALID_POINTER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowWhenLocked(true);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_picture_on_locked_screen);

        fullScreenImage = findViewById(R.id.fullScreenImage);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        String type = intent.getType();
        if (type != null && type.startsWith("image/")) {
            Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri.class);
            if (imageUri != null) {
                LocalBroadcastManager.getInstance(DisplayPictureOnLockedScreen.this).sendBroadcast(new Intent("finish_activity"));
                justCreated = true;
                fullScreenImage.setImageURI(imageUri);
                fullScreenImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                fullScreenImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mScaleFactor = 1.0f;
                if (fullScreenImage != null) {
                    fullScreenImage.setScaleX(mScaleFactor);
                    fullScreenImage.setScaleY(mScaleFactor);
                    fullScreenImage.setTranslationX(0f);
                    fullScreenImage.setTranslationY(0f);
                }
                mLastTouchX = 0f;
                mLastTouchY = 0f;
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScaleGestureDetector != null) {
            mScaleGestureDetector.onTouchEvent(ev);
        }
        final int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = ev.getActionIndex();
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);
                mLastTouchX = x;
                mLastTouchY = y;
                mActivePointerId = ev.getPointerId(pointerIndex);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex == -1) {
                     break;
                }
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;
                if (mScaleFactor != 1.0f) {
                    if (fullScreenImage != null) {
                        fullScreenImage.setTranslationX(fullScreenImage.getTranslationX() + dx);
                        fullScreenImage.setTranslationY(fullScreenImage.getTranslationY() + dy);
                    }
                }
                mLastTouchX = x;
                mLastTouchY = y;
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = ev.getActionIndex();
                final int pointerId = ev.getPointerId(pointerIndex);

                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    if (ev.getPointerCount() > newPointerIndex) {
                        mLastTouchX = ev.getX(newPointerIndex);
                        mLastTouchY = ev.getY(newPointerIndex);
                        mActivePointerId = ev.getPointerId(newPointerIndex);
                    } else {
                        mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                    }
                }
                break;
            }
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            if (fullScreenImage != null) {
                fullScreenImage.setScaleX(mScaleFactor);
                fullScreenImage.setScaleY(mScaleFactor);
            }
            return true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        KeyguardManager KM = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        boolean screenLocked = KM.isKeyguardLocked();
        if ((!screenLocked && justCreated) || (screenLocked && !justCreated)) {
            justCreated = false;
            return;
        }
        LocalBroadcastManager.getInstance(DisplayPictureOnLockedScreen.this).sendBroadcast(new Intent("finish_activity"));
        this.finish();
    }
}