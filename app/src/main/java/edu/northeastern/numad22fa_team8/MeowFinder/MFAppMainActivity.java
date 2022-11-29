package edu.northeastern.numad22fa_team8.MeowFinder;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.northeastern.numad22fa_team8.R;

public class MFAppMainActivity extends AppCompatActivity {

//    ActivityMainBinding binding;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CreatePostFragment createPostFragment = new CreatePostFragment();
    SearchPostsFragment searchPostsFragment = new SearchPostsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        setContentView(R.layout.activity_mfhome);

        bottomNavigationView = findViewById(R.id.meowfinder_bottomNavigationView);

        // initially replace the fragment by homeFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.meowfinder_container, homeFragment).commit();

        // add listeners
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.meow_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.meowfinder_container, homeFragment).commit();
                        return true;
                    case R.id.meow_create:
                        getSupportFragmentManager().beginTransaction().replace(R.id.meowfinder_container, createPostFragment).commit();
                        return true;
                    case R.id.meow_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.meowfinder_container, searchPostsFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }

}