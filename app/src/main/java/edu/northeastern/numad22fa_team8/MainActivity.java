package edu.northeastern.numad22fa_team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_a7 = findViewById(R.id.buttonA7);
        Button btn_sticker_app = findViewById(R.id.buttonStickerApp);
        btn_a7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        btn_sticker_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStickerApp();
            }
        });

    }
    private void openActivity() {
        Intent intent = new Intent(this, A7.class);
        startActivity(intent);
    }

    private void openStickerApp() {
        Intent intent = new Intent(this, StickerApp.class);
        startActivity(intent);
    }
}