package edu.northeastern.numad22fa_team8.MeowFinder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import android.content.Intent;
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

public class CatAdapter extends FirebaseRecyclerAdapter<PostDetail,CatAdapter.myviewholder> {
    private RecyclerInterface recyclerInterface;


    public CatAdapter(@NonNull FirebaseRecyclerOptions<PostDetail> options, RecyclerInterface recyclerInterface) {
        super(options);
        this.recyclerInterface = recyclerInterface;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((AppCompatActivity)view.getContext(), CatPostDetail.class);
                intent.putExtra("title", model.getTitle());
                view.getContext().startActivity(intent);

            }
        });
        /*holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerInterface.onItemClick(holder.getAdapterPosition());
                *//*AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.recycler1, new PostDetailFragment(model.getTitle(), model.getDescription(), model.getLocation(), model.getTimestamp(),model.getStatus().toString(),model.getAuthorEmail(),model.getAuthorName())).addToBackStack(null).commit();*//*

            }
        });
*/


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the item
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_post,parent,false);
        return new myviewholder(view, recyclerInterface);
    }

    public class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, description, location, timestamp, status, authorName, authorEmail;
        RecyclerInterface recyclerInterface;


        public myviewholder(@NonNull View itemView, RecyclerInterface recyclerInterface) {
            super(itemView);
            this.recyclerInterface = recyclerInterface;


            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            location = itemView.findViewById(R.id.location);
            timestamp = itemView.findViewById(R.id.timestamp);
            status = itemView.findViewById(R.id.status);
            authorName = itemView.findViewById(R.id.AuthorName);
            authorEmail = itemView.findViewById(R.id.AuthorEmail);

        }

        @Override
        public void onClick(View view) {
            this.recyclerInterface.onItemClick(getAbsoluteAdapterPosition());

        }
    }

    public interface RecyclerInterface {
        void onItemClick(int position);
    }

}