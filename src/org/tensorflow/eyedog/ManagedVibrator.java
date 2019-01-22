package org.tensorflow.eyedog;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.Vibrator;
import android.util.Log;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ManagedVibrator {

    public static final String TAG = ManagedVibrator.class.getSimpleName();

    private Context mContext;
    private Vibrator mVibrator;
    private boolean mIsVibrating = false;
    private ScheduledThreadPoolExecutor mExecutor;

    private Runnable mVibrationEndRunnable = new Runnable() {
        @Override
        public void run() {
            setVibrating(false);
        }
    };

    public ManagedVibrator(Context context) {
        this.mContext = context;
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mExecutor = new ScheduledThreadPoolExecutor(1);
    }

    public boolean hasVibrator() {
        return mVibrator.hasVibrator();
    }

    public void vibrate(long milliseconds) {
        setVibrating(true);
        mVibrator.vibrate(milliseconds);
        notifyOnVibrationEnd(milliseconds);
    }

    // Requires API v21
    public void vibrate(long milliseconds, AudioAttributes attributes) {
        setVibrating(true);
        mVibrator.vibrate(milliseconds, attributes);
        notifyOnVibrationEnd(milliseconds);
    }

    public void vibrate(long[] pattern, int repeat) {
        setVibrating(true);
        mVibrator.vibrate(pattern, repeat);
    }

    // Requires API v21
    public void vibrate(long[] pattern, int repeat, AudioAttributes attributes) {
        setVibrating(true);
        mVibrator.vibrate(pattern, repeat, attributes);
    }

    public void cancel() {
        mVibrator.cancel();
        setVibrating(false);
    }

    public boolean isVibrating() {
        return mIsVibrating;
    }

    private void setVibrating(boolean isVibrating) {
        mIsVibrating = isVibrating;
    }

    private void notifyOnVibrationEnd(long milliseconds) {
        try {
            mExecutor.schedule(mVibrationEndRunnable, milliseconds, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            Log.e(TAG, e.getMessage());
        }
    }

}