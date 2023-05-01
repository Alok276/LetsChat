package com.example.letschat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class authentication extends AppCompatActivity {

    private EditText edtOtp;
    private Button btnotp;
    String phoneno;
    String otpid;
    private TextView txdetails;
    FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authenetication_page);
        Intent intent=getIntent();
        edtOtp=findViewById(R.id.editOtp);
        btnotp=findViewById(R.id.button);
        phoneno=intent.getStringExtra("message_key").toString();
        mAuth=FirebaseAuth.getInstance();


        initiateOtp();
        btnotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtOtp.getText().toString().isEmpty())
                    Toast.makeText(authentication.this, "Blank field can't be processed", Toast.LENGTH_SHORT).show();
                else if(edtOtp.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(),"invalid code",Toast.LENGTH_LONG).show();
                else{
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,edtOtp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });



    }

    private void initiateOtp() {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    otpid = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    System.out.println(e.getMessage());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            };
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent= new Intent(getApplicationContext(), home.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"sign in error"+ task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

}


