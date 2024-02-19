package com.jp0030.evoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginUser extends AppCompatActivity {
    EditText edtUserName , edtEmail;
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        edtUserName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        btnStart = findViewById(R.id.btnStart);

        Intent homeActivity ;
        homeActivity = new Intent(LoginUser.this, HomeActivity.class);

        btnStart.setOnClickListener(v -> startActivity(homeActivity));

    }
}