package com.example.quiz_game.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quiz_game.R;
import com.example.quiz_game.data.models.ResultWithUser;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<ResultWithUser> results;

    public HistoryAdapter(List<ResultWithUser> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultWithUser result = results.get(position);
        holder.textEmail.setText("Email: " + result.getEmail());
        holder.textDifficulty.setText("Сложность: " + result.getDifficulty());
        holder.textScore.setText("Очки: " + result.getScore());
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textEmail, textScore, textDifficulty;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textEmail = itemView.findViewById(R.id.textEmail);
            textDifficulty = itemView.findViewById(R.id.textDifficulty);
            textScore = itemView.findViewById(R.id.textScore);
        }
    }

    public void updateResults(List<ResultWithUser> newResults) {
        results.clear();
        results.addAll(newResults);
        notifyDataSetChanged();
    }
}
