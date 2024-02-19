package com.jp0030.evoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class welcome extends AppCompatActivity {
    AppCompatButton btnGetStart;
    ImageView imgLogo , imgWelcome ;
    LinearLayout linearLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        linearLayout = findViewById(R.id.linearLayout);
        btnGetStart = findViewById(R.id.btnGetStart);
        imgLogo = findViewById(R.id.imgLogo);
        imgWelcome = findViewById(R.id.imgWelcome);

        Animation heart_bite = AnimationUtils.loadAnimation(this, R.anim.heart_bite);
        imgLogo.startAnimation(heart_bite);


        Intent iLogin ;
        iLogin = new Intent(welcome.this , Login.class);


        btnGetStart.setOnClickListener(v -> startActivity(iLogin));


    }
}