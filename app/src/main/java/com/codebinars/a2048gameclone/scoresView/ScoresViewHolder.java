package com.codebinars.a2048gameclone.scoresView;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codebinars.a2048gameclone.R;

public class ScoresViewHolder extends RecyclerView.ViewHolder{
    TextView username, score, datetime, duration;

    /**
     * View holder of my Score textView items
     * @param itemView
     */
    public ScoresViewHolder(View itemView){
        super(itemView);
        username = (TextView) itemView.findViewById(R.id.playerRecycler);
        score = (TextView) itemView.findViewById(R.id.scoreRecycler);
        datetime = (TextView) itemView.findViewById(R.id.dateRecycler);
        duration = (TextView) itemView.findViewById(R.id.durationRecycler);
    }
}