package edu.northeastern.numad22fa_team8.MeowFinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import edu.northeastern.numad22fa_team8.R;

public class CommentsAdapter extends FirebaseRecyclerAdapter<String, ViewHolder> {
    private Context context;
    ProgressDialog progress_dialog;

    public CommentsAdapter(@NonNull FirebaseRecyclerOptions<String> options, Context context, ProgressDialog progress_dialog) {
        super(options);
        this.context = context;
        this.progress_dialog = progress_dialog;
    }

    public CommentsAdapter(@NonNull FirebaseRecyclerOptions<String> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull String model) {
        holder.tvComment.setText(model);
        progress_dialog.dismiss();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_comments, parent, false);
        return new ViewHolder(view);
    }
}
class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvComment;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvComment = itemView.findViewById(R.id.tvComment);
    }
}
