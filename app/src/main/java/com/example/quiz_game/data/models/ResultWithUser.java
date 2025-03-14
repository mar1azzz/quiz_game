package com.example.quiz_game.data.models;

public class ResultWithUser {
    private String email;
    private String difficulty;
    private int score;

    public ResultWithUser(String email, String difficulty, int score) {
        this.email = email;
        this.difficulty = difficulty;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getScore() {
        return score;
    }
}
