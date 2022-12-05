package edu.northeastern.numad22fa_team8.MeowFinder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;


import edu.northeastern.numad22fa_team8.MeowFinder.model.PostDetail;
import edu.northeastern.numad22fa_team8.R;

public class CatAdapter extends FirebaseRecyclerAdapter<PostDetail,CatAdapter.myviewholder>
{

    public CatAdapter(@NonNull FirebaseRecyclerOptions<PostDetail> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final PostDetail model) {
        holder.title.setText(String.format("Title: %s", model.getTitle().toString()));
        holder.authorName.setText(String.format("Author Name: %s",model.getAuthorName().toString() ));
        holder.authorEmail.setText(String.format("Author Email: %s",model.getAuthorEmail().toString()));

        holder.description.setText(String.format("Description: %s", model.getDescription().toString()));
        holder.location.setText(String.format("Location: %s", model.getLocation().toString()));
        holder.timestamp.setText(String.format("Timestamp: %s", model.getTimestamp().toString()));
        holder.status.setText(String.format("Status: %s", model.getStatus().toString()));


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_post,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView title, description, location, timestamp, status, authorName, authorEmail;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            location = itemView.findViewById(R.id.location);
            timestamp = itemView.findViewById(R.id.timestamp);
            status = itemView.findViewById(R.id.status);
            authorName = itemView.findViewById(R.id.AuthorName);
            authorEmail = itemView.findViewById(R.id.AuthorEmail);
        }
    }

}