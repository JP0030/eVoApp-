package com.jp0030.evoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.google.firebase.Timestamp;
import com.jp0030.evoapp.model.UserModel;
import com.jp0030.evoapp.utils.FirebaseUtil;

import java.util.Objects;

public class LoginUser extends AppCompatActivity {
    EditText edtUserName ;
//    EditText edtEmail;
    Button btnStart;
    ProgressBar progressBar3;
    String phoneNumber;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        edtUserName = findViewById(R.id.edtUserName);
//        edtEmail = findViewById(R.id.edtEmail);
        btnStart = findViewById(R.id.btnStart);
        progressBar3 = findViewById(R.id.progressBar3);

        phoneNumber = Objects.requireNonNull(getIntent().getExtras()).getString("phone");
        phoneNumber = getIntent().getExtras().getString("Phone");
        getUsername();
//        getEmail();


        btnStart.setOnClickListener((v -> {
            setUsername();
//            setEmail();
        }));

    }
    void setUsername(){

        String username = edtUserName.getText().toString();
        if(username.isEmpty() || username.length()<3){
            edtUserName.setError("Username length should be at least 3 chars");
            return;
        }
        setInProgress(true);
        if(userModel!=null){
            userModel.setUsername(username);
        }else{
            userModel = new UserModel(phoneNumber,username,Timestamp.now(),FirebaseUtil.currentUserId());
        }

        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(task -> {
            setInProgress(false);
            if(task.isSuccessful()){
                Intent intent = new Intent(LoginUser.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);
            }
        });

    }

    void getUsername(){
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            setInProgress(false);
            if(task.isSuccessful()){
                userModel = task.getResult().toObject(UserModel.class);
                if(userModel!=null){
                    edtUserName.setText(userModel.getUsername());
                }
            }
        });
    }


/*
    void getEmail() {
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    userModel =    task.getResult().toObject(UserModel.class);
                    if(userModel!=null){
                        edtEmail.setText(userModel.getEmail());
                    }
                }
            }
        });
    }
    void setEmail() {
        String email = edtEmail.getText().toString();
        if(email.isEmpty() || email.length()<3){
            edtUserName.setError("please enter right email i'd");
            return;
        }
        setInProgress(true);
        if(userModel!=null){
            userModel.setEmail(email);
        }else{
            userModel = new UserModel(phoneNumber,email,Timestamp.now(),FirebaseUtil.currentUserId());
        }
        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginUser.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                }
            }
        });
    }
*/


    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar3.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.GONE);
        }else{
            progressBar3.setVisibility(View.GONE);
            btnStart.setVisibility(View.VISIBLE);
        }
    }
}