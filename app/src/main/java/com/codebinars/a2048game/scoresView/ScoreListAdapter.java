package com.codebinars.a2048game.scoresView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.codebinars.a2048game.R;
import com.codebinars.a2048game.database.ScoreDisplay;

import java.util.ArrayList;

public class ScoreListAdapter extends RecyclerView.Adapter<ScoresViewHolder>  {

    public ArrayList<ScoreDisplay> playersList;
    public OnItemClickListener itemListener;

    public void setOnItemclickListener(OnItemClickListener listener){
        itemListener = listener;
    }

    public ScoreListAdapter(ArrayList<ScoreDisplay> playersList) {
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
        holder.country.setText(playersList.get(position).getCountry());
        if(playersList.get(position).getAvatar() !=null && playersList.get(position).getAvatar().length() > 5){
            holder.avatarImage.setImageBitmap(ImageUtils.loadImage(playersList.get(position).getAvatar()));
        }
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }


}