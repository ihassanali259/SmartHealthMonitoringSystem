package com.example.shms1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity  extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    ImageView imageview;
    Animation anim, anim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageview = findViewById(R.id.imageViewsplash);

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_in);
        anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_from_left);
        imageview.startAnimation(anim);
        TextView textView = findViewById(R.id.textviewsplash);
        textView.startAnimation(anim2);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
