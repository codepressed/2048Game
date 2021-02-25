package com.codebinars.a2048gameclone.scoresView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebinars.a2048gameclone.R;
import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.database.ScoreModel;
import com.codebinars.a2048gameclone.scoresView.ScoresViewHolder;

import java.util.ArrayList;


public class ScoreListAdapter extends RecyclerView.Adapter<ScoresViewHolder> {

    ArrayList<ScoreModel> playersList;

    public ScoreListAdapter(ArrayList<ScoreModel> playersList) {
        this.playersList = playersList;
    }

    @Override
    public ScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_score,null,false);
        return new ScoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoresViewHolder holder, int position) {
        holder.score.setText(playersList.get(position).getScore());
        holder.username.setText(playersList.get(position).getUsername());
        holder.datetime.setText(playersList.get(position).getDatetime());
        holder.duration.setText(playersList.get(position).getDuration().toString());

    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }


}