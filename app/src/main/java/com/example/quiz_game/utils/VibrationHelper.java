package com.example.quiz_game.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;

public class VibrationHelper {
    @SuppressLint("MissingPermission")
    public static void vibrate(Context context, long duration) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(duration);
        }
    }
}
