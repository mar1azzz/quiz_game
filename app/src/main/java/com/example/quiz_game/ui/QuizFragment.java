package com.example.quiz_game.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quiz_game.R;
import com.example.quiz_game.data.models.Question;
import com.example.quiz_game.data.models.Result;
import com.example.quiz_game.utils.AnimationHelper;
import com.example.quiz_game.utils.SoundManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class QuizFragment extends Fragment {
    private TextView textQuestion, textTimer;
    private RadioGroup radioGroup;
    private RadioButton radioAnswer1, radioAnswer2, radioAnswer3, radioAnswer4;
    private Button buttonNext, buttonHint, buttonCloseHint;
    private View hintOverlay;
    private TextView textHint;
    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;
    private String difficulty;
    private CountDownTimer countDownTimer;
    private static final long TIME_LIMIT = 60000; // 60 секунд
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textQuestion = view.findViewById(R.id.textQuestion);
        textTimer = view.findViewById(R.id.textTimer);
        radioGroup = view.findViewById(R.id.radioGroupAnswers);
        radioAnswer1 = view.findViewById(R.id.radioAnswer1);
        radioAnswer2 = view.findViewById(R.id.radioAnswer2);
        radioAnswer3 = view.findViewById(R.id.radioAnswer3);
        radioAnswer4 = view.findViewById(R.id.radioAnswer4);
        buttonNext = view.findViewById(R.id.buttonNext);
        buttonHint = view.findViewById(R.id.buttonHint);
        hintOverlay = view.findViewById(R.id.hintOverlay);
        textHint = view.findViewById(R.id.textHint);
        buttonCloseHint = view.findViewById(R.id.buttonCloseHint);

        difficulty = getArguments() != null ? getArguments().getString("difficulty", "easy") : "easy";

        databaseRef = FirebaseDatabase.getInstance().getReference("questions").child(difficulty);

        loadQuestions();

        buttonNext.setOnClickListener(v -> nextQuestion(view));

        buttonHint.setOnClickListener(v -> showHint());
        buttonCloseHint.setOnClickListener(v -> hideHint());
    }

    private void loadQuestions() {
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.clear();
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    if (question != null) {
                        questions.add(question);
                    }
                }
                Collections.shuffle(questions);
                if (!questions.isEmpty()) {
                    loadQuestion();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void loadQuestion() {
        if (currentIndex >= questions.size()) {
            saveResult();
            return;
        }

        Question question = questions.get(currentIndex);
        textQuestion.setText(question.getQuestion());
        radioAnswer1.setText(question.getOptions().get(0));
        radioAnswer2.setText(question.getOptions().get(1));
        radioAnswer3.setText(question.getOptions().get(2));
        radioAnswer4.setText(question.getOptions().get(3));
        radioGroup.clearCheck();
        textHint.setText(question.getHint());

        startTimer();
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();  // Останавливаем предыдущий таймер перед созданием нового
        }

        textTimer.setText("60");  // Сбрасываем значение таймера на UI

        countDownTimer = new CountDownTimer(TIME_LIMIT, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if (getView() != null) {
                    nextQuestion(getView());  // Безопасно вызываем переход к следующему вопросу
                }
            }
        }.start();
    }

    private void nextQuestion(View view) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;  // Явно обнуляем, чтобы избежать повторных вызовов
        }

        checkAnswer();
        // Добавляем задержку перед загрузкой следующего вопроса
        new android.os.Handler().postDelayed(() -> {
            currentIndex++;

            if (currentIndex < questions.size()) {
                loadQuestion();  // Загружаем новый вопрос после задержки
            } else {
                saveResult();
                goToResult(view);
            }
        }, 1500); // 1000 миллисекунд = 1 секунда
    }

    private void checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = getView().findViewById(selectedId);
            String selectedAnswer = selectedRadioButton.getText().toString();

            SoundManager soundManager = new SoundManager();

            if (selectedAnswer.equals(questions.get(currentIndex).getOptions().get(questions.get(currentIndex).getCorrectIndex()))) {
                score += 10;
                soundManager.playSound(getContext(), R.raw.correct_answer);
                AnimationHelper.playCorrectAnswerAnimation(selectedRadioButton);
            } else {
                soundManager.playSound(getContext(), R.raw.wrong_answer);
                AnimationHelper.playWrongAnswerAnimation(selectedRadioButton);
            }
        }
    }


    private void goToResult(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putInt("totalQuestions", questions.size());
        Navigation.findNavController(view).navigate(R.id.action_quizFragment_to_resultFragment, bundle);
    }

    private void showHint() {
        hintOverlay.setVisibility(View.VISIBLE);
    }

    private void hideHint() {
        hintOverlay.setVisibility(View.GONE);
    }

    private void saveResult() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) return;

        String email = auth.getCurrentUser().getEmail();
        if (email == null) return;

        // Правильное кодирование email
        String userKey = email.replace(".", "_dot_").replace("@", "_at_");

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("results").child(userKey);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Object> results = new ArrayList<>();

                if (snapshot.exists()) {
                    for (DataSnapshot resultSnapshot : snapshot.getChildren()) {
                        Object savedResult = resultSnapshot.getValue(Object.class);
                        if (savedResult != null) {
                            results.add(savedResult);
                        }
                    }
                }

                // Добавляем новый результат с правильным форматом
                results.add(new Result(score, difficulty));

                userRef.setValue(results);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
