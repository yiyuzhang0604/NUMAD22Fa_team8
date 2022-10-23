package edu.northeastern.numad22fa_team8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {

    private List<PostModel> postList;

    public PostAdapter(List<PostModel> postList) {
        this.postList = postList;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        holder.catName.setText("Cat name: " + postList.get(position).getCatName());
        holder.zipcode.setText("Zipcode: " + postList.get(position).getZipcode());
        holder.description.setText("Description: " + postList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}