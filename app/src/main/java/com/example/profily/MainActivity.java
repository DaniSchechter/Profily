package com.example.profily;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.main_nav_host);

        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);

        navView.setOnNavigationItemSelectedListener( menuItem -> {
                navController.navigate(menuItem.getItemId());
                return true;
        });

    }

}
