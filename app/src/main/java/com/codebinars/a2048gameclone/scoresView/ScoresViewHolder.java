package com.codebinars.a2048gameclone.scoresView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codebinars.a2048gameclone.R;

public class ScoresViewHolder extends RecyclerView.ViewHolder{
    public TextView username, score, datetime, duration;
    public ImageView editImage, deleteImage;




    /**
     * View holder of my Score textView items
     * @param itemView
     */
    public ScoresViewHolder(View itemView, final OnItemClickListener listener){
        super(itemView);
        this.username = itemView.findViewById(R.id.playerRecycler);
        this.score = itemView.findViewById(R.id.scoreRecycler);
        this.datetime = itemView.findViewById(R.id.dateRecycler);
        this.duration = itemView.findViewById(R.id.durationRecycler);
        this.deleteImage = itemView.findViewById(R.id.imageDelete);
        this.editImage = itemView.findViewById(R.id.imageEdit);

        deleteImage.setOnClickListener(v -> {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position);
                }
            }
        });

        editImage.setOnClickListener(v -> {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(position);
                }
            }
        });



    }


}
