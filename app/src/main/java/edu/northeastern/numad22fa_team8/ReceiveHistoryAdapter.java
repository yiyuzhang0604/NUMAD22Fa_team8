package edu.northeastern.numad22fa_team8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ReceiveHistoryAdapter extends FirebaseRecyclerAdapter<
        StickerMessage, ReceiveHistoryViewHolder> {

    public ReceiveHistoryAdapter(
            @NonNull FirebaseRecyclerOptions<StickerMessage> options) {
        super(options);
    }

    // Function to bind the view in Card view
    @Override
    protected void
    onBindViewHolder(@NonNull ReceiveHistoryViewHolder holder,
                     int position, @NonNull StickerMessage model) {
        holder.sender.setText(model.getSender());
        holder.receiver.setText(model.getReceiver());
        holder.image.setImageResource(model.getStickerId());
        holder.timestamp.setText(model.getTimestamp().toString());
    }

    // Function to tell the class about the Card view
    @NonNull
    @Override
    public ReceiveHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                       int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_receive_history_single, parent, false);
        return new ReceiveHistoryViewHolder(view);
    }

}