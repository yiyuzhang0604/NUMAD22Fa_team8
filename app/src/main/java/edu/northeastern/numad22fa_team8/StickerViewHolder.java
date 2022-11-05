package edu.northeastern.numad22fa_team8;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

    public TextView username;
    public TextView friend;

    public StickerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.username = itemView.findViewById(R.id.editTextEnterUserName);
        this.friend = itemView.findViewById(R.id.editTextEnterFriendName);
    }
}
