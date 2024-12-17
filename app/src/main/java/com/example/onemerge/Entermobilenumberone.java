package com.example.onemerge;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class Entermobilenumberone extends AppCompatActivity {

    EditText enternumber;
    Button getotpbutton;
    ProgressBar progressBar;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entermobilenumberone);

        mauth = FirebaseAuth.getInstance();

        // Check if the user is logged in
        FirebaseUser currentUser = mauth.getCurrentUser();
        if (currentUser != null) {
            Intent ihome = new Intent(this, MainActivity.class);
            startActivity(ihome);

        }

        enternumber=findViewById(R.id.input_mobile_number);
        getotpbutton =findViewById(R.id.buttongetotp);

        progressBar =findViewById(R.id.progressbar_verify_otp1);

        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!enternumber.getText().toString().trim().isEmpty()){
                    if((enternumber.getText().toString().trim()).length()==10){

                        progressBar.setVisibility(v.VISIBLE);
                        getotpbutton.setVisibility(v.INVISIBLE);

                        // Initialize FirebaseAuth
                        mauth = FirebaseAuth.getInstance();

                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mauth)
                                        .setPhoneNumber("+91" + enternumber.getText().toString()) // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout duration
                                        .setActivity(Entermobilenumberone.this) // Activity (for callback binding)
                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                // Auto-retrieval or instant verification completed
                                                progressBar.setVisibility(View.GONE);
                                                getotpbutton.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                // Verification failed
                                                progressBar.setVisibility(View.GONE);
                                                getotpbutton.setVisibility(View.VISIBLE);
                                                Log.w(TAG, "onVerificationFailed", e);

                                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                                    // Invalid request
                                                } else if (e instanceof FirebaseTooManyRequestsException) {
                                                    // The SMS quota for the project has been exceeded
                                                } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                                                    // reCAPTCHA verification attempted with null Activity
                                                }

                                                // Show a message and update the UI
                                                Toast.makeText(Entermobilenumberone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                // OTP code sent
                                                progressBar.setVisibility(View.GONE);
                                                getotpbutton.setVisibility(View.VISIBLE);
                                                Intent intent = new Intent(getApplicationContext(), Verifyenterotptwo.class);
                                                intent.putExtra("mobile", enternumber.getText().toString());
                                                intent.putExtra("backendotp", backendotp);
                                                startActivity(intent);
                                            }
                                        })
                                        .build();

                        PhoneAuthProvider.verifyPhoneNumber(options);

                    }else {
                        Toast.makeText(Entermobilenumberone.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Entermobilenumberone.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}