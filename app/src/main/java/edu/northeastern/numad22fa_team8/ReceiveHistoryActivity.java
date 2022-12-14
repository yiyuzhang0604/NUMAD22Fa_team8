package edu.northeastern.numad22fa_team8;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ReceiveHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReceiveHistoryAdapter adapter;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private String sender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sender = extras.getString("sender");
        } else {
            Toast.makeText(ReceiveHistoryActivity.this, "No sender name given!", Toast.LENGTH_SHORT).show();
        }

        setContentView(R.layout.activity_receive_history);

        recyclerView = findViewById(R.id.receiveHistoryRecycleViewHistory);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        DatabaseReference historyDBRef = db.child("users").child(sender).child("receiveHistory");
        FirebaseRecyclerOptions<StickerMessage> options
                = new FirebaseRecyclerOptions.Builder<StickerMessage>()
                .setQuery(historyDBRef, StickerMessage.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new ReceiveHistoryAdapter(options);
        // Connecting Adapter class with the Recycler view
        recyclerView.setAdapter(adapter);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}


