package com.example.quiz_game.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.quiz_game.R;

public class DifficultyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_difficulty, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonEasy = view.findViewById(R.id.buttonEasy);
        Button buttonMedium = view.findViewById(R.id.buttonMedium);
        Button buttonHard = view.findViewById(R.id.buttonHard);

        // Навигация в QuizFragment с переданным уровнем сложности
        buttonEasy.setOnClickListener(v -> navigateToQuiz(view, "easy"));
        buttonMedium.setOnClickListener(v -> navigateToQuiz(view, "medium"));
        buttonHard.setOnClickListener(v -> navigateToQuiz(view, "hard"));
    }

    private void navigateToQuiz(View view, String difficulty) {
        Bundle bundle = new Bundle();
        bundle.putString("difficulty", difficulty);
        Navigation.findNavController(view).navigate(R.id.action_difficultyFragment_to_quizFragment, bundle);
    }
}
