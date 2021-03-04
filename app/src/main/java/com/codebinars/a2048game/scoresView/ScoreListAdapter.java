package com.codebinars.a2048game.scoresView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.codebinars.a2048game.R;
import com.codebinars.a2048game.database.DatabaseHelper;
import com.codebinars.a2048game.database.ScoreModel;
import java.util.ArrayList;

import static com.codebinars.a2048game.scoresView.ScoreConstants.USER_AVATAR;

public class ScoreListAdapter extends RecyclerView.Adapter<ScoresViewHolder>  {

    public ArrayList<ScoreModel> playersList;
    public OnItemClickListener itemListener;
    public DatabaseHelper databaseHelper;


    public void setOnItemclickListener(OnItemClickListener listener){
        itemListener = listener;
    }

    public ScoreListAdapter(ArrayList<ScoreModel> playersList, Context context) {
        this.playersList = playersList;
        databaseHelper = DatabaseHelper.getInstance(context);
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
        holder.username.setText(databaseHelper.getUser(playersList.get(position).getUsernameId()));
        holder.datetime.setText(playersList.get(position).getDatetime());
        holder.duration.setText(playersList.get(position).getDuration().toString());
        holder.country.setText(databaseHelper.getCountry(playersList.get(position).getUsernameId()));
        if(databaseHelper.getImage(playersList.get(position).getUsernameId()) != null){
            holder.avatarImage.setImageBitmap(databaseHelper.getImage(playersList.get(position).getUsernameId()));
        }
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }


}