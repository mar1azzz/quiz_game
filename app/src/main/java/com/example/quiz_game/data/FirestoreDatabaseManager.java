package com.example.quiz_game.data;

import android.util.Log;

import com.example.quiz_game.data.models.Question;
import com.example.quiz_game.data.models.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FirestoreDatabaseManager {
    private FirebaseFirestore db;

    public FirestoreDatabaseManager(FirebaseFirestore db) {
        this.db = db;
    }

    public void getQuestionsByDifficulty(String difficulty, Consumer<List<Question>> callback) {
        db.collection("questions")
                .whereEqualTo("difficulty", difficulty)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Question> questions = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            questions.add(document.toObject(Question.class));
                        }
                        callback.accept(questions);
                    }
                });
    }

    public void getUserResults(String userId, Consumer<List<Result>> callback) {
        db.collection("results")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Result> results = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Result result = document.toObject(Result.class);
                        results.add(result);
                    }
                    callback.accept(results);
                })
                .addOnFailureListener(e -> {
                    callback.accept(new ArrayList<>()); // В случае ошибки передаем пустой список
                });
    }



}
