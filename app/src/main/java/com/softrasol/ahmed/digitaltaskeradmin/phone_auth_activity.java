package com.softrasol.ahmed.digitaltaskeradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.softrasol.ahmed.digitaltaskeradmin.Interfaces.ToastMessage;

import java.util.concurrent.TimeUnit;

public class phone_auth_activity extends AppCompatActivity {

    EditText editTextPhone,editTextCode;

    String codeSend;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_activity);

        Button Cntn_with_phone= findViewById(R.id.cntn_with_phone);



        Cntn_with_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(phone_auth_activity.this);
                bottomSheetDialog.setContentView(R.layout.phone_auth_bottom_sheet_dialog);
                Button verify_Code=bottomSheetDialog.findViewById(R.id.verification_code);
                Button send_verification_code=bottomSheetDialog.findViewById(R.id.send_verification_code);

                editTextCode=bottomSheetDialog.findViewById(R.id.code_field);
                editTextPhone=bottomSheetDialog.findViewById(R.id.phone_field);


                FirebaseAuth.getInstance();

                verify_Code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                     verifySignInCode();

                    }


                });



                send_verification_code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        sendVerificationCode();

                    }
                });


                bottomSheetDialog.show();
            }
        });





    }


    private void sendVerificationCode()
    {
        String phone=editTextPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            editTextCode.setError("Enter a phone number");
            editTextCode.requestFocus();
            return;
        }

        if (phone.length() < 11)
        {
            editTextPhone.setError("Enter a valid Phone Number");
            editTextPhone.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSend=s;
        }
    };

   private void verifySignInCode()
   {
       String code=editTextCode.getText().toString().trim();

       PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSend, code);

       signInWithPhoneAuthCredential(credential);
   }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Toast.makeText(phone_auth_activity.this, "Error in phone", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
