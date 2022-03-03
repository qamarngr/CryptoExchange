package com.qamar.cryptoexchange.util;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class Timer extends CountDownTimer {
    private static final String TAG = "Timer";
    private OnTimeElapsed onTimeElapsed;

    private Timer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public Timer(long millisInFuture, long countDownInterval, LifecycleOwner owner, OnTimeElapsed onTimeElapsed) {
        super(millisInFuture, countDownInterval);
        this.onTimeElapsed = onTimeElapsed;
        owner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                Log.d(TAG, "onPause");
                cancel();
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                Log.d(TAG, "onResume");
                start();
            }

        });
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (onTimeElapsed != null) {
            onTimeElapsed.onTimeElapsed();
        }
    }

    @Override
    public void onFinish() {

    }
}
