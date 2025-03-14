package com.example.quiz_game.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.quiz_game.R;
import com.example.quiz_game.data.FirebaseAuthManager;

public class MenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализируем элементы интерфейса
        TextView textWelcome = view.findViewById(R.id.textWelcome);
        Button buttonDifficulty = view.findViewById(R.id.buttonDifficulty);
        Button buttonHistory = view.findViewById(R.id.buttonHistory);
        Button buttonStartQuiz = view.findViewById(R.id.buttonStartQuiz);
        Button buttonLogout = view.findViewById(R.id.buttonLogout);

        // Загружаем имя пользователя из аргументов (если передан)
        Bundle args = getArguments();
        if (args != null && args.containsKey("username")) {
            String username = args.getString("username");
            textWelcome.setText("Добро пожаловать, " + username + "!");
        }

        // Кнопка "Выбрать сложность"
        buttonDifficulty.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_difficultyFragment)
        );

        // Кнопка "История прохождений"
        buttonHistory.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_historyFragment)
        );

        // Кнопка "Начать квиз" (по умолчанию сложность = "Легкий")
        buttonStartQuiz.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("difficulty", "easy");
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_quizFragment, bundle);
        });

        //Кнопка выхода
        buttonLogout.setOnClickListener(v -> {
            FirebaseAuthManager authManager = new FirebaseAuthManager();
            authManager.logoutUser();
            SharedPreferences preferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();
            Toast.makeText(getContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_authFragment);
        });
    }
}
