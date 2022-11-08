package edu.northeastern.numad22fa_team8;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class SendHistoryActivity extends AppCompatActivity {

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private String sender = null;
    private TextView times1, times2, times3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sender = extras.getString("sender");
        } else {
            Toast.makeText(SendHistoryActivity.this, "No sender name given!", Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_send_history);

        times1 = findViewById(R.id.joeyTimesText);
        times2 = findViewById(R.id.rickTimesText);
        times3 = findViewById(R.id.simpsonTimesText);

        db.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            DataSnapshot snapshot = task.getResult();
            if (!snapshot.hasChild("users")) {
                return;
            }
            DataSnapshot users = snapshot.child("users");
            if (!users.hasChild(sender)) {
                return;
            }
            User user = users.child(sender).getValue(User.class);
            assert user != null;
            for (Map.Entry<String, Integer> sends : user.getSendHistory().entrySet()) {
                switch (Integer.parseInt(sends.getKey().split("_")[0])) {
                    case 1:
                        times1.setText(sends.getValue().toString());
                        break;
                    case 2:
                        times2.setText(sends.getValue().toString());
                        break;
                    case 3:
                        times3.setText(sends.getValue().toString());
                        break;
                    default:
                        break;
                }
            }
        });
    }
}