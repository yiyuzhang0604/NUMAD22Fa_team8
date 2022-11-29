package edu.northeastern.numad22fa_team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.northeastern.numad22fa_team8.MeowFinder.MFAppMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_a7 = findViewById(R.id.buttonA7);
        Button btn_sticker_app = findViewById(R.id.buttonStickerApp);
        Button btn_about= findViewById(R.id.about);
        Button btn_meowfinder_app = findViewById(R.id.buttonMeowFinderApp);

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamName = "Team8: Yiyu Zhang, Yuan Zhuang, Jiayue Wu, Yang He";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(MainActivity.this, teamName, duration);
                toast.show();
            }
        });
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

        btn_meowfinder_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMeowFinderApp();
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

    private void openMeowFinderApp() {
        Intent intent = new Intent(this, MFAppMainActivity.class);
        startActivity(intent);
    }
}