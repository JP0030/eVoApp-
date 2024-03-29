package com.jp0030.evoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.jp0030.evoapp.utils.AndroidUtil;

import java.util.concurrent.TimeUnit;

public class LoginOtp extends AppCompatActivity {
    EditText edtOtp ;
    Button btnContinue ;
    TextView txtResendOtp ;
    ProgressBar progressBar2;
    String phoneNumber;
    Long timeOutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    CountDownTimer resendTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_otp);

        edtOtp = findViewById(R.id.edtOtp);
        btnContinue = findViewById(R.id.btnContinue);
        txtResendOtp = findViewById(R.id.txtResendOtp);
        progressBar2 = findViewById(R.id.progressBar2);

       phoneNumber = getIntent().getExtras().getString("phone");

       sendOtp(phoneNumber,false);

       btnContinue.setOnClickListener(v -> {
           String enteredOtp = edtOtp.getText().toString();
           PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,enteredOtp);
           SignIn(credential);
       });
       txtResendOtp.setOnClickListener((v) ->{
           sendOtp(phoneNumber,true);
       });
    }

    void sendOtp(String phoneNumber, boolean isResend){
        startResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeOutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                SignIn(phoneAuthCredential);
                                setInProgress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndroidUtil.showToast(getApplicationContext()," OTP verification failed ");
                                setInProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                AndroidUtil.showToast(getApplicationContext()," OTP sent successfully ");
                                setInProgress(false);
                            }
                        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    void setInProgress(boolean inProgress){
        if (inProgress){
            progressBar2.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.GONE);
        }else {
            progressBar2.setVisibility(View.GONE);
            btnContinue.setVisibility(View.VISIBLE);
        }
    }

    void SignIn (PhoneAuthCredential phoneAuthCredential){
        //Login part
        setInProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginOtp.this, LoginUser.class);
                    intent.putExtra("Phone", phoneNumber);
                    startActivity(intent);
                }else {
                    AndroidUtil.showToast(getApplicationContext(), "OTP verification failed");
                }
            }
        });
    }


    void startResendTimer() {
        txtResendOtp.setEnabled(false);
        resendTimer = new CountDownTimer(timeOutSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeOutSeconds = millisUntilFinished / 1000;
                txtResendOtp.setText("Resend OTP in " + timeOutSeconds + " seconds");
            }

            @Override
            public void onFinish() {
                timeOutSeconds = 60L;
                txtResendOtp.setEnabled(true);
                txtResendOtp.setText("Resend OTP");
            }
        };
        resendTimer.start();
    }


}