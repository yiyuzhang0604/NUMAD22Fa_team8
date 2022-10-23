package edu.northeastern.numad22fa_team8;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class PostHolder extends RecyclerView.ViewHolder {
    public TextView catName;
    public TextView zipcode;
    public TextView description;

    public PostHolder(View itemView) {
        super(itemView);
        catName = itemView.findViewById(R.id.holder_cat_name);
        zipcode = itemView.findViewById(R.id.holder_zipcode);
        description = itemView.findViewById(R.id.holder_description);
    }
}