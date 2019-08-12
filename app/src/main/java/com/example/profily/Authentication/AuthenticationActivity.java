package com.example.profily.Authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.profily.MainActivity;
import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.User.User;
import com.example.profily.Model.Schema.User.UserAsyncDao;
import com.example.profily.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity {

    private enum CurrentPage {
        Login, Signup
    }
    private static final String LoginToRegisterHint = "Don't have an account? ";
    private static final String RegisterToLoginHint = "Already have an account? ";

    private CurrentPage currentPage;

    private FirebaseAuth mAuth;

    private Button submitButton;
    private TextView actionHint;
    private TextView changeMethodLink;
    private TextView authenticationErrorText;
    private ProgressBar progressBar;

    // Form fields
    private EditText emailInput;
    private EditText passwordInput;
    private EditText passwordVerificationInput;
    private EditText usernameInput;
    private EditText firstNameInput;
    private EditText lastNameInput;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mAuth = FirebaseAuth.getInstance();
        currentPage  = CurrentPage.Login;

        progressBar = findViewById(R.id.authentication_progress_bar);
        submitButton = findViewById(R.id.authentication_submit);
        authenticationErrorText = findViewById(R.id.authentication_error);
        actionHint = findViewById(R.id.authentication_action_hint);
        changeMethodLink = findViewById(R.id.authentication_change_method);
        emailInput = findViewById(R.id.authentication_email);
        passwordInput = findViewById(R.id.authentication_password);
        passwordVerificationInput = findViewById(R.id.authentication_password_verification);
        usernameInput = findViewById(R.id.authentication_username);
        firstNameInput = findViewById(R.id.authentication_first_name);
        lastNameInput = findViewById(R.id.authentication_last_name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {

        if (!validateForm()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            addUser(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            authenticationErrorText.setText( task.getException().getMessage());
                            updateUI(null);
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            authenticationErrorText.setText( task.getException().getMessage());
                            updateUI(null);
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(emailInput.getText().toString())) {
            emailInput.setError("Required.");
            valid = false;
        } else {
            emailInput.setError(null);
        }

        if (TextUtils.isEmpty(passwordInput.getText().toString())) {
            passwordInput.setError("Required.");
            valid = false;
        } else {
            passwordInput.setError(null);
        }

        if (currentPage == CurrentPage.Signup) {
            if (!passwordInput.getText().toString().equals(passwordVerificationInput.getText().toString())) {
                passwordVerificationInput.setError("Password don't match");
                valid = false;
            } else {
                passwordVerificationInput.setError(null);
            }

            if (TextUtils.isEmpty(usernameInput.getText().toString())) {
                usernameInput.setError("Required.");
                valid = false;
            } else {
                usernameInput.setError(null);
            }

            if (TextUtils.isEmpty(firstNameInput.getText().toString())) {
                firstNameInput.setError("Required.");
                valid = false;
            } else {
                firstNameInput.setError(null);
            }

            if (TextUtils.isEmpty(lastNameInput.getText().toString())) {
                lastNameInput.setError("Required.");
                valid = false;
            } else {
                lastNameInput.setError(null);
            }
        }

        return valid;
    }


    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            this.finish();
        }
        else {
            if (currentPage == CurrentPage.Login) {
                displayLogin();
            } else {
                displaySignup();
            }
        }
    }

    private void displayLogin() {

        currentPage = CurrentPage.Login;
        actionHint.setText(LoginToRegisterHint);
        changeMethodLink.setText("Sign up");
        submitButton.setText("Log In");
        changeMethodLink.setOnClickListener(view -> displaySignup());
        submitButton.setOnClickListener(view -> {signIn(
                emailInput.getText().toString(),
                passwordInput.getText().toString());
//                finish();
            }
        );
        passwordVerificationInput.setVisibility(View.GONE);
        usernameInput.setVisibility(View.GONE);
        firstNameInput.setVisibility(View.GONE);
        lastNameInput.setVisibility(View.GONE);
    }

    private void displaySignup() {
        currentPage = CurrentPage.Signup;
        actionHint.setText(RegisterToLoginHint);
        changeMethodLink.setText("Log in");
        submitButton.setText("Sign up");
        changeMethodLink.setOnClickListener(view -> displayLogin());
        submitButton.setOnClickListener(view -> createAccount(
                emailInput.getText().toString(), passwordInput.getText().toString())
        );
        passwordVerificationInput.setVisibility(View.VISIBLE);
        usernameInput.setVisibility(View.VISIBLE);
        firstNameInput.setVisibility(View.VISIBLE);
        lastNameInput.setVisibility(View.VISIBLE);
    }

    private void addUser(FirebaseUser fbUser)
    {
        User user = new User(
                fbUser.getUid(),
                "",
                usernameInput.getText().toString(),
                "",
                firstNameInput.getText().toString(),
                lastNameInput.getText().toString()
        );

        Model.instance.addUser(user);
    }
}
