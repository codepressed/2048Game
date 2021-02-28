package com.codebinars.a2048gameclone.scoresView;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
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
        this.username = (TextView) itemView.findViewById(R.id.playerRecycler);
        this.score = (TextView) itemView.findViewById(R.id.scoreRecycler);
        this.datetime = (TextView) itemView.findViewById(R.id.dateRecycler);
        this.duration = (TextView) itemView.findViewById(R.id.durationRecycler);
        this.deleteImage = (ImageView) itemView.findViewById(R.id.imageDelete);
        this.editImage = (ImageView) itemView.findViewById(R.id.imageEdit);

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            }
        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(position);
                    }
                }
            }
        });



    }


}
