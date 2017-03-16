package com.github.itswisdomagain.developers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView myImage = (ImageView) findViewById(R.id.background);
        Animation animation = new TranslateAnimation(0, 4800, 0, 0);
        animation.setDuration(10000);
        animation.setFillAfter(true);
        myImage.startAnimation(animation);
    }
}
