package com.softrasol.ahmed.digitaltaskeradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.softrasol.ahmed.digitaltaskeradmin.Services.NotificationService;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText mEdtEmail, mEdtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        widgetsInflation();

    }

    public void ButtonSignInClick(View view) {
        signInUsingEmailAndPassword();
    }

    private void signInUsingEmailAndPassword() {

        validatingEditTextFields();

    }

    private void validatingEditTextFields() {

        String email = mEdtEmail.getText().toString().trim();
        String password = mEdtPassword.getText().toString();

        if (email.isEmpty()){
            mEdtEmail.setError("Required");
            mEdtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEdtEmail.setError("Invalid Email Address");
            mEdtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            mEdtPassword.setError("Required");
            mEdtPassword.requestFocus();
            return;
        }

        signInUsingEmailAndPasswordFirebaseAuth(email, password);

    }

    private void signInUsingEmailAndPasswordFirebaseAuth(String email, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener
                (new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignInActivity.this,
                            "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(new Intent(getApplicationContext(), NotificationService.class));
                    }else {
                        startService(new Intent(getApplicationContext(), NotificationService.class));
                    }

                }else {
                    Toast.makeText(SignInActivity.this, "Database Error "+
                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void widgetsInflation() {

        mEdtEmail = findViewById(R.id.edt_signin_email);
        mEdtPassword = findViewById(R.id.edt_signin_password);

    }
}
