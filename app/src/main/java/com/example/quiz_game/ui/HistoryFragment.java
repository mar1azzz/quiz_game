package com.example.quiz_game.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quiz_game.R;
import com.example.quiz_game.data.models.ResultWithUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Устанавливаем пустой адаптер, чтобы избежать ошибок
        adapter = new HistoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance().getReference("results");
        loadHistory();
    }

    private void loadHistory() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ResultWithUser> results = new ArrayList<>();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String email = decodeEmail(userSnapshot.getKey()); // Декодируем email

                    for (DataSnapshot resultSnapshot : userSnapshot.getChildren()) {
                        int score = resultSnapshot.child("score").getValue(Integer.class);
                        String difficulty = resultSnapshot.child("difficulty").getValue(String.class);

                        results.add(new ResultWithUser(email, difficulty, score));
                    }
                }
                adapter.updateResults(results);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Логируем ошибку (можно добавить Toast)
            }
        });
    }

    private String decodeEmail(String encodedEmail) {
        return encodedEmail.replace("_dot_", ".").replace("_at_", "@");
    }
}
