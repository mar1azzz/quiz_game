package com.example.quiz_game.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.quiz_game.R;
import com.example.quiz_game.data.FirebaseAuthManager;
import com.google.firebase.auth.FirebaseUser;

public class AuthFragment extends Fragment {
    private FirebaseAuthManager authManager;
    private EditText emailInput, passwordInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authManager = new FirebaseAuthManager();
        emailInput = view.findViewById(R.id.editTextEmail);
        passwordInput = view.findViewById(R.id.editTextPassword);
        Button loginButton = view.findViewById(R.id.buttonLogin);
        Button registerButton = view.findViewById(R.id.buttonRegister);

        loginButton.setOnClickListener(v -> loginUser(view));
        registerButton.setOnClickListener(v -> registerUser(view));
    }

    private void loginUser(View view) {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Введите email и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        authManager.loginUser(email, password, new FirebaseAuthManager.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                Toast.makeText(getContext(), "Вход выполнен", Toast.LENGTH_SHORT).show();

                // Передаем email в MenuFragment
                Bundle bundle = new Bundle();
                bundle.putString("userEmail", authManager.getCurrentUserEmail());
                Navigation.findNavController(view).navigate(R.id.action_authFragment_to_menuFragment, bundle);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerUser(View view) {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Введите email и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        authManager.registerUser(email, password, new FirebaseAuthManager.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                Toast.makeText(getContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();

                // Передаем email в MenuFragment
                Bundle bundle = new Bundle();
                bundle.putString("userEmail", authManager.getCurrentUserEmail());
                Navigation.findNavController(view).navigate(R.id.action_authFragment_to_menuFragment, bundle);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
