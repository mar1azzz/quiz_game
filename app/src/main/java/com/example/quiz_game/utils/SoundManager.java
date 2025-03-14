package com.example.quiz_game.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private MediaPlayer mediaPlayer;

    public void playSound(Context context, int soundResId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, soundResId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
