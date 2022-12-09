package edu.northeastern.numad22fa_team8.MeowFinder.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad22fa_team8.MeowFinder.model.PostDetail;
import edu.northeastern.numad22fa_team8.R;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private final Context context;

    public ResultAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_result_display_card, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        PostDetail post = SearchResultBroker.getInstance().getResult(position);
        holder.ownerName.setText(post.getAuthorName());
        holder.email.setText(post.getAuthorEmail());
        holder.title.setText(post.getTitle());
        holder.location.setText(post.getLocation());
    }

    @Override
    public int getItemCount() {
        return SearchResultBroker.getInstance().getSize();
    }
}
