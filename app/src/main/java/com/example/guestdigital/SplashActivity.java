package com.example.guestdigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    TextView tvWelcome,tvTo;
    ImageView img_SplashIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Get rid of status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Including Animations hooks
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_transition);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_transition);

        //Normal widget hooks
        tvWelcome = findViewById(R.id.tv_welcome);
        tvTo = findViewById(R.id.tv_to);
        img_SplashIcon = findViewById(R.id.img_SplashIcon);
        //Connecting Image and Text to the Animations
        tvWelcome.setAnimation(topAnim);
        tvTo.setAnimation(topAnim);
        img_SplashIcon.setAnimation(bottomAnim);

        int splash_timeout = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent after_splash = new Intent(SplashActivity.this, GuestList.class);
                startActivity(after_splash);
                finish();
            }
        }, splash_timeout);


    }
}