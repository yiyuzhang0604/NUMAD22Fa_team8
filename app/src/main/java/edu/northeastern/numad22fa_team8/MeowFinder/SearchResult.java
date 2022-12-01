package edu.northeastern.numad22fa_team8.MeowFinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import edu.northeastern.numad22fa_team8.R;

public class SearchResult extends AppCompatActivity {

    private RecyclerView searchRecycleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchRecycleView = findViewById(R.id.search_recycle_view);
        searchRecycleView.setLayoutManager(
                new LinearLayoutManager(this));


    }
}