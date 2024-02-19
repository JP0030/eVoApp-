package com.jp0030.evoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {
    CountryCodePicker countryCode;
    EditText edtMoNo;
    Button btnOtp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        countryCode =findViewById(R.id.countryCode);
        edtMoNo = findViewById(R.id.edtMoNo);
        btnOtp = findViewById(R.id.btnOtp);
        progressBar = findViewById(R.id.progressBar);

        countryCode.registerCarrierNumberEditText(edtMoNo);

        progressBar.setVisibility(View.GONE);

        btnOtp.setOnClickListener((v) -> {
            if (!countryCode.isValidFullNumber()){
                edtMoNo.setError("Phone number is not valid");
                return;
            }
            Intent intent =new Intent(Login.this,LoginOtp.class);
            intent.putExtra("phone", countryCode.getFullNumberWithPlus());
            startActivity(intent);
        });

    }
}