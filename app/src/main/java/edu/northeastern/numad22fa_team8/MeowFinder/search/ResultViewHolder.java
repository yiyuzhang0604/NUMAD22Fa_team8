package edu.northeastern.numad22fa_team8.MeowFinder.search;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad22fa_team8.R;

public class ResultViewHolder extends RecyclerView.ViewHolder {
    public TextView ownerName;
    public TextView email;
    public TextView location;
    public TextView title;

    public ResultViewHolder(@NonNull View itemView) {
        super(itemView);
        this.ownerName = itemView.findViewById(R.id.ownerNameDisplay);
        this.email = itemView.findViewById(R.id.ownerEmailDisplay);
        this.title = itemView.findViewById(R.id.titleDisplay);
        this.location = itemView.findViewById(R.id.locationDisplay);
    }
}
