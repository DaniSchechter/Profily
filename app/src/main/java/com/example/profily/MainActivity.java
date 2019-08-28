package com.example.profily;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.profily.Authentication.AuthenticationActivity;
import com.example.profily.Model.Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "on create");

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

        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public void displayAuthenticationActivity(Boolean clearBackStack) {
        Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
        if (clearBackStack) {
            clearBackStack();
        }
        startActivity(intent);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "on restart");
        if (Model.instance.getConnectedUserId() == null) {
            Log.d("TAG", "finishing");

            finish();
        } else {
            Log.d("TAG", "start destination id:  " + navController.getGraph().getStartDestination());
            navController.navigate(navController.getGraph().getStartDestination());
        }
    }

    private void clearBackStack() {
        Log.d("TAG", "Clearing back stack");
        Boolean stackNotEmpty;
        do {
            stackNotEmpty = navController.popBackStack();
        } while(stackNotEmpty);
    }
}
