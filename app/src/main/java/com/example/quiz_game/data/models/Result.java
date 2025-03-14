package com.example.quiz_game.data.models;

public class Result {
    public String difficulty;
    public int score;

    public Result() {
        // Пустой конструктор нужен для Firebase
    }

    public Result(int score, String difficulty) {
        this.score = score;
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
