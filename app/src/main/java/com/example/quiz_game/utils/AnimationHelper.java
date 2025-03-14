package com.example.quiz_game.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.example.quiz_game.R;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Rotation;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class AnimationHelper {
    public static void playCorrectAnswerAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.correct_answer_anim);
        view.startAnimation(animation);
    }

    public static void playWrongAnswerAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.wrong_answer_anim);
        view.startAnimation(animation);
    }

    public static void playConfettiAnimation(KonfettiView konfettiView) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Левый верхний угол (0.0, 0.0)
            Party leftParty = new Party(
                    /* Направление вниз */
                    360,
                    /* Разброс частиц */
                    360,
                    /* Скорость */
                    5f, 8f,
                    /* Дампинг */
                    0.9f,
                    /* Размер */
                    Arrays.asList(new Size(12, 5f, 0.2f)),
                    /* Цвета */
                    Arrays.asList(0xFFE91E63, 0xFFFFC107, 0xFF3F51B5),
                    /* Форма */
                    Arrays.asList(Shape.Circle.INSTANCE, Shape.Square.INSTANCE),
                    /* Время жизни частиц */
                    3500,
                    /* Исчезновение */
                    true,
                    /* Позиция в левом верхнем углу */
                    new Position.Relative(0.23, 0.0),
                    /* Задержка */
                    0,
                    /* Вращение */
                    new Rotation(),
                    /* Эмиттер */
                    new Emitter(2, TimeUnit.SECONDS).max(200)
            );

            // Правый верхний угол (1.0, 0.0)
            Party rightParty = new Party(
                    360,
                    360,
                    5f, 8f,
                    0.9f,
                    Arrays.asList(new Size(12, 5f, 0.2f)),
                    Arrays.asList(0xFFE91E63, 0xFFFFC107, 0xFF3F51B5),
                    Arrays.asList(Shape.Circle.INSTANCE, Shape.Square.INSTANCE),
                    3500,
                    true,
                    new Position.Relative(0.77, 0.0),
                    0,
                    new Rotation(),
                    new Emitter(2, TimeUnit.SECONDS).max(200)
            );

            konfettiView.start(leftParty);
            konfettiView.start(rightParty);

        }, 2000); // Запуск через 2 секунды
    }
}
