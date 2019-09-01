package com.example.profily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.profily.Authentication.AuthenticationActivity;
import com.example.profily.Model.Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    private static final int DISPLAY_AUTHENTICATION_ACTIVITY_CODE = 10;
    private boolean needToRestart = false;

    private NavController navController;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "on create");

        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();

        // Starting the app when no user is signed in - Display Authentication activity
        if (Model.instance.getConnectedUserId() == null)
        {
            displayAuthenticationActivity(false);
        }

        else {
            setContentView(R.layout.activity_main);

            navController = Navigation.findNavController(this, R.id.main_nav_host);

            BottomNavigationView navView = findViewById(R.id.bottom_nav_view);

            navView.setOnNavigationItemSelectedListener( menuItem -> {
                navController.navigate(menuItem.getItemId());
                return true;
            });
        }

    }

    public void displayAuthenticationActivity(Boolean clearBackStack) {
        Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);

        // Start for result to be able to differ MainActivity restart that origins in:
        // 1. From Authentication activity
        // 2. From camera
        if (intent.resolveActivity(getPackageManager()) != null) {
            this.startActivityForResult(intent, DISPLAY_AUTHENTICATION_ACTIVITY_CODE);
        }

        if (clearBackStack) {
            clearBackStack();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DISPLAY_AUTHENTICATION_ACTIVITY_CODE && resultCode == RESULT_OK) {
            needToRestart = true;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "on restart");
        if (Model.instance.getConnectedUserId() == null) {
            Log.d("TAG", "finishing");
            finish();
        }
        if(needToRestart) {
            Intent intent = getIntent();
            Log.d("TAG", "restarting activity");
            finish();
            startActivity(intent);
            needToRestart = false;
        }
    }

    private void clearBackStack() {
        Log.d("TAG", "Clearing back stack");
        boolean stackNotEmpty;
        do {
            stackNotEmpty = navController.popBackStack();
        } while(stackNotEmpty);
    }
}
