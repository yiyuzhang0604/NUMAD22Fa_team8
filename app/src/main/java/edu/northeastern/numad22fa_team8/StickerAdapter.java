package edu.northeastern.numad22fa_team8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {

    private final Context context;

    public StickerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerViewHolder(LayoutInflater.from(context).inflate(R.layout.text_row_item, null), this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        StickerMessage stickerMessage = StickerFactory.getInstance().get(position);
        holder.username.setText(stickerMessage.getSender());
        holder.friend.setText(stickerMessage.getReceiver());
    }

    @Override
    public int getItemCount() {
        return StickerFactory.getInstance().size();
    }
}
