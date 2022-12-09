package edu.northeastern.numad22fa_team8.MeowFinder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.numad22fa_team8.R;

public class CatPostDetail extends AppCompatActivity {

    TextView title, description, location, timestamp, status, authorName, authorEmail;
    AppCompatButton btnAddComment, btnCancel, btnAdd;
    RecyclerView rvComments;
    EditText etComment;
    ScrollView scrollView;
    Dialog dialog;
    DatabaseReference db, dbComments;
    CommentsAdapter adapter;
    ProgressDialog progress_dialog;

    @SuppressLint("MissingInflatedId")
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
        rvComments = findViewById(R.id.rvComments);
        btnAddComment = findViewById(R.id.btnAddComment);
        scrollView = findViewById(R.id.scrollView);
        progress_dialog = new ProgressDialog(this);
        btnAddComment.setVisibility(View.VISIBLE);
        dialog = new Dialog(CatPostDetail.this);

        String curr_title = getIntent().getStringExtra("title");
        db = FirebaseDatabase.getInstance().getReference().child("MeowFinderAppPosts");
        dbComments = FirebaseDatabase.getInstance().getReference().child("CommentsData").child(curr_title);

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
                    authorName.setText(String.format("Author Name: %s", str_authorName));
                    authorEmail.setText(String.format("Author Email: %s", str_authorEmail));
                    description.setText(String.format("Description: %s", str_description));
                    location.setText(String.format("Location: %s", str_location));
                    timestamp.setText(String.format("Timestamp: %s", str_timestamp));
                    status.setText(String.format("Status: %s", str_status));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogComment();
            }
        });

        dbComments.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    scrollView.setVisibility(View.VISIBLE);
                    progress_dialog.setTitle(getString(R.string.app_name));
                    progress_dialog.setMessage("Just a few Seconds... ");
                    progress_dialog.setCancelable(false);
                    progress_dialog.show();
                    showRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CatPostDetail.this, MFAppMainActivity.class);
        startActivity(intent);
        //getSupportFragmentManager().beginTransaction().replace(R.id.cat_post_detail, new HomeFragment()).addToBackStack(null).commit();
    }

    private void dialogComment() {
        dialog.setContentView(R.layout.dialog_comment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnAdd = dialog.findViewById(R.id.btnAdd);
        etComment = dialog.findViewById(R.id.etComment);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getComment = etComment.getText().toString();
                if (getComment.isEmpty()){
                    Toast.makeText(CatPostDetail.this, "Please write comment first", Toast.LENGTH_SHORT).show();
                }else{
                    progress_dialog.setTitle(getString(R.string.app_name));
                    progress_dialog.setMessage("Just a few Seconds... ");
                    progress_dialog.setCancelable(false);
                    progress_dialog.show();
                    String push_id = dbComments.push().getKey();
                    dbComments.child(push_id).setValue(getComment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progress_dialog.dismiss();
                            dialog.dismiss();
                            startActivity(new Intent(CatPostDetail.this,MFAppMainActivity.class));
                            Toast.makeText(CatPostDetail.this, "Comment Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress_dialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText(CatPostDetail.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        dialog.show();
    }

    private void showRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CatPostDetail.this, 1);
        rvComments.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(dbComments, String.class)
                .build();

        adapter = new CommentsAdapter(options, CatPostDetail.this, progress_dialog);
        adapter.startListening();
        rvComments.setAdapter(adapter);
    }

}
