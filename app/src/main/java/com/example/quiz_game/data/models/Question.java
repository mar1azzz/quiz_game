package com.example.quiz_game.data.models;

import com.google.firebase.firestore.PropertyName;
import java.util.List;

public class Question {
    @PropertyName("question")
    private String question;

    @PropertyName("options")
    private List<String> options;

    @PropertyName("correctIndex")
    private int correctIndex;

    @PropertyName("hint")
    private String hint;

    public Question() {}

    public Question(String question, List<String> options, int correctIndex, String hint) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
        this.hint = hint;
    }

    @PropertyName("question")
    public String getQuestion() {
        return question;
    }

    @PropertyName("options")
    public List<String> getOptions() {
        return options;
    }

    @PropertyName("correctIndex")
    public int getCorrectIndex() {
        return correctIndex;
    }

    @PropertyName("hint")
    public String getHint() {
        return hint;
    }
}
