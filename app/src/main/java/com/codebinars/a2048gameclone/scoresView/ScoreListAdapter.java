package com.codebinars.a2048gameclone.scoresView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.codebinars.a2048gameclone.R;
import com.codebinars.a2048gameclone.database.ScoreModel;
import java.util.ArrayList;

public class ScoreListAdapter extends RecyclerView.Adapter<ScoresViewHolder>  {

    public ArrayList<ScoreModel> playersList;
    public OnItemClickListener itemListener;


    public void setOnItemclickListener(OnItemClickListener listener){
        itemListener = listener;
    }

    public ScoreListAdapter(ArrayList<ScoreModel> playersList) {
        this.playersList = playersList;
    }

    @NonNull
    @Override
    public ScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_score,null,false);
        ScoresViewHolder sch = new ScoresViewHolder(view, itemListener);
        return sch;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoresViewHolder holder, int position) {
        holder.score.setText(playersList.get(position).getScore().toString());
        holder.username.setText(playersList.get(position).getUsername());
        holder.datetime.setText(playersList.get(position).getDatetime());
        holder.duration.setText(playersList.get(position).getDuration().toString());

    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }


}