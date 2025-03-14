package com.example.quiz_game.data;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;

public class FirebaseAuthManager {
    private static final String TAG = "FirebaseAuthManager";
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthManager() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    // Регистрация пользователя
    public void registerUser(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Авторизация пользователя
    public void loginUser(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Выход пользователя
    public void logoutUser() {
        firebaseAuth.signOut();
    }

    // Получение текущего пользователя
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    // Получение email текущего пользователя
    public String getCurrentUserEmail() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        return (user != null) ? user.getEmail() : null;
    }

    // Интерфейс для обработки успеха/ошибок
    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception e);
    }
}
