package edu.northeastern.numad22fa_team8.MeowFinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import edu.northeastern.numad22fa_team8.MeowFinder.search.ResultAdapter;
import edu.northeastern.numad22fa_team8.R;

public class SearchResult extends AppCompatActivity {

    private RecyclerView searchRecycleView;
    private ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchRecycleView = findViewById(R.id.search_recycle_view);
        searchRecycleView.setLayoutManager(
                new LinearLayoutManager(this));

        adapter = new ResultAdapter(this);
        searchRecycleView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("ALERT")
                .setMessage("Closing this page will lose all searching results.\nAre you sure to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Results shown.", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .show();
    }
}