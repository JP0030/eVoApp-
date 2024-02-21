package com.jp0030.evoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class LoginUser extends AppCompatActivity {
    EditText edtUserName , edtEmail;
    Button btnStart;
    ProgressBar progressBar3;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        edtUserName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        btnStart = findViewById(R.id.btnStart);
        progressBar3 = findViewById(R.id.progressBar3);

        phoneNumber = getIntent().getExtras().getString("phone");
        getUserName();
        
    }

    void getUserName (){
        setInProgress(true);

    }

    void setInProgress(boolean inProgress){
        if (inProgress){
            progressBar3.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.GONE);
        }else {
            progressBar3.setVisibility(View.GONE);
            btnStart.setVisibility(View.VISIBLE);
        }
    }
}