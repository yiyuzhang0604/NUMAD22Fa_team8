package edu.northeastern.numad22fa_team8;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

    public ImageView sticker;
    public TextView times;

    public StickerViewHolder(@NonNull View itemView) {
        super(itemView);
        this.sticker = itemView.findViewById(R.id.stickerImage);
        this.times = itemView.findViewById(R.id.sendTimesText);
    }
}
