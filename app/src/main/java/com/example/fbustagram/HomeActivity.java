package com.example.fbustagram;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fbustagram.fragments.ComposeFragment;
import com.example.fbustagram.fragments.PostsFragment;
import com.example.fbustagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
       // Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        // Configure the refreshing colors
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // handle navigation selection
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment ;
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                fragment = new PostsFragment();
                                Log.i("home", "home");
                                break;
                            case R.id.new_post:
                                fragment = new ComposeFragment();
                                break;
                            case R.id.user_profile:
                                fragment = new ProfileFragment();
                                break;
                            default:
                                fragment = new PostsFragment();
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        return true;
                    }
                });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

    }









}
