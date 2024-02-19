package com.jp0030.evoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.PhoneAccountSuggestion;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
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
    String varificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
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

    }

    void sendOtp(String phoneNumber, boolean isResend){
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                new PhoneAuthOptions.Builder(mAuth)
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
                                varificationCode = s;
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
    }

}