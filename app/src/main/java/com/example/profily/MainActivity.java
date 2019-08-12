package com.example.profily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.profily.Authentication.AuthenticationActivity;
import com.example.profily.Model.Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();

        if (Model.instance.getConnectedUserId() == null)
        {
            displayAuthenticationActivity(false);
        }

        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.main_nav_host);

        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);

        navView.setOnNavigationItemSelectedListener( menuItem -> {
                navController.navigate(menuItem.getItemId());
                return true;
        });
    }

    public void displayAuthenticationActivity(Boolean andFinish) {
        Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
        startActivity(intent);
        if (andFinish) {
            finish();
        }
    }

}
