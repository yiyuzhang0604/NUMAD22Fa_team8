package edu.northeastern.numad22fa_team8;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiveHistoryViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

    public TextView sender;
    public TextView receiver;
    public ImageView image;
    public TextView timestamp;

    public ReceiveHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.sender = itemView.findViewById(R.id.sender);
        this.receiver = itemView.findViewById(R.id.receiver);
        this.image = itemView.findViewById(R.id.stickerImage);
        this.timestamp = itemView.findViewById(R.id.sendTimesText);
    }
}

