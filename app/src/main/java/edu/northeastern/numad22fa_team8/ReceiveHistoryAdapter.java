package edu.northeastern.numad22fa_team8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ReceiveHistoryAdapter extends FirebaseRecyclerAdapter<
        StickerMessage, ReceiveHistoryViewHolder> {

    public ReceiveHistoryAdapter(
            @NonNull FirebaseRecyclerOptions<StickerMessage> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull ReceiveHistoryViewHolder holder,
                     int position, @NonNull StickerMessage model)
    {
        holder.sender.setText(model.getSender());
        holder.receiver.setText(model.getReceiver());
        holder.image.setImageResource(model.getStickerId());
        holder.timestamp.setText(model.getTimestamp().toString());
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public ReceiveHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)  {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_receive_history_single, parent, false);
        return new ReceiveHistoryViewHolder(view);
    }

}