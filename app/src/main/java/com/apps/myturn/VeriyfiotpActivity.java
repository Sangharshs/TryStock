package com.apps.myturn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apps.myturn.Models.Res;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static com.apps.myturn.Api.BASE_URL;

public class VeriyfiotpActivity extends AppCompatActivity {
    MaterialButton verifyBtn;
    String number;
    String otpId;
    FirebaseAuth mAuth;
    TextInputEditText textInputEditText;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veryfiotp);

        verifyBtn = findViewById(R.id.verifyBTN_material);
        textInputEditText = findViewById(R.id.getOTP);


        number = getIntent().getStringExtra("number");
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(VeriyfiotpActivity.this,MainActivity.class));
            finish();
        }

        initiateOtp();

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textInputEditText.getText().toString().equals("")) {
                    PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(otpId, textInputEditText.getText().toString());
                    signInWithPhoneAuthCredential(authCredential);

                } else if (textInputEditText.getText().length() != 6) {
                    Toast.makeText(VeriyfiotpActivity.this, "Enter correct otp", Toast.LENGTH_SHORT).show();
                }


            }
        });
        Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
    }

    private void initiateOtp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                otpId = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                String code = phoneAuthCredential.getSmsCode();
                                if (code != null) {
                                    textInputEditText.setText(code);
                                }
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VeriyfiotpActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(VeriyfiotpActivity.this, RegisterActivity.class);
                            intent.putExtra("number", number);
                            startActivity(intent);

                            finish();


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}