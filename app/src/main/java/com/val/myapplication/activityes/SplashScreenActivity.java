package com.val.myapplication.activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.val.myapplication.R;
import com.val.myapplication.utils.ChangeActionBarColor;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView imageView;
    private Animation animation;
    private ProgressBar progressBar;
    private ConstraintLayout layout;

    private static final int SPLASH_DURATION=2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        imageView=(ImageView) findViewById(R.id.ivSplashIcon);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        layout=(ConstraintLayout)findViewById(R.id.splash_screen_layout);
        animation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
    }

    private void initFunctionality(){
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                imageView.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent=new Intent(getApplicationContext(),CasomLoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        },SPLASH_DURATION);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initFunctionality();
    }
}
