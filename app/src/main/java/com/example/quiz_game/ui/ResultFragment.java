package com.example.quiz_game.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.quiz_game.R;
import com.example.quiz_game.utils.AnimationHelper;
import com.example.quiz_game.utils.SoundManager;
import com.example.quiz_game.utils.VibrationHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.Objects;

import nl.dionsegijn.konfetti.xml.KonfettiView;

public class ResultFragment extends Fragment {
    private DatabaseReference databaseRef;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("results").child(userId);

        TextView textResult = view.findViewById(R.id.textResult);
        Button buttonBackToMenu = view.findViewById(R.id.buttonBackToMenu);
        KonfettiView konfettiView = view.findViewById(R.id.konfettiView);

        int score = getArguments().getInt("score");
        textResult.setText("Ваш результат: " + score);

        // 🎉 Вибрация, звук и анимация конфетти
        VibrationHelper.vibrate(getContext(), 500);
        SoundManager soundManager = new SoundManager();
        soundManager.playSound(getContext(), R.raw.quiz_complete);
        AnimationHelper.playConfettiAnimation(konfettiView);

        buttonBackToMenu.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_resultFragment_to_menuFragment)
        );
    }

}