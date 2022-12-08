package edu.northeastern.numad22fa_team8.MeowFinder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.northeastern.numad22fa_team8.R;

public class CatPostDetail extends AppCompatActivity {

    TextView title, description, location, timestamp, status, authorName, authorEmail;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_post);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);
        timestamp = findViewById(R.id.timestamp);
        status = findViewById(R.id.status);
        authorName = findViewById(R.id.AuthorName);
        authorEmail = findViewById(R.id.AuthorEmail);
        db = FirebaseDatabase.getInstance().getReference().child("MeowFinderAppPosts");

        String curr_title = getIntent().getStringExtra("title");
        db.child(curr_title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String str_title = snapshot.child("title").getValue().toString();
                    String str_description = snapshot.child("description").getValue().toString();
                    String str_location = snapshot.child("location").getValue().toString();
                    String str_timestamp = snapshot.child("timestamp").getValue().toString();
                    String str_status = snapshot.child("status").getValue().toString();
                    String str_authorName = snapshot.child("authorName").getValue().toString();
                    String str_authorEmail = snapshot.child("authorEmail").getValue().toString();

                    title.setText(String.format("Title: %s", str_title));
                    authorName.setText(String.format("Author Name: %s",str_description));
                    authorEmail.setText(String.format("Author Email: %s",str_location));
                    description.setText(String.format("Description: %s", str_timestamp));
                    location.setText(String.format("Location: %s", str_status));
                    timestamp.setText(String.format("Timestamp: %s", str_authorName));
                    status.setText(String.format("Status: %s", str_authorEmail));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(CatPostDetail.this, HomeFragment.class);
        startActivity(intent);*/
        getSupportFragmentManager().beginTransaction().replace(R.id.cat_post_detail, new HomeFragment()).addToBackStack(null).commit();


    }
}
