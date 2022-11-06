package edu.northeastern.numad22fa_team8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class SendHistoryActivity extends AppCompatActivity {

    RecyclerView historyRecycleView;
    TextView totalSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyRecycleView = findViewById(R.id.recycleViewHistory);
        totalSent = findViewById(R.id.textTotalSent);
        historyRecycleView.setLayoutManager(new LinearLayoutManager(this));

        StickerAdapter adapter = new StickerAdapter(this);
        StickerFactory.getInstance().setAdapter(adapter);
        historyRecycleView.setAdapter(adapter);

        totalSent.setText("Total stickers sent: " + adapter.getItemCount());
    }
}