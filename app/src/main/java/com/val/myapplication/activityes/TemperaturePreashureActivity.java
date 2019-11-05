package com.val.myapplication.activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.val.myapplication.R;
import com.val.myapplication.utils.ChangeActionBarColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TemperaturePreashureActivity extends AppCompatActivity implements View.OnTouchListener {

    private ViewFlipper viewFlipper;
    private float formPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_preashure);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tem_preash_linerLayout);
        linearLayout.setOnTouchListener(this);
        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<Integer> layouts = new ArrayList<>();
        Collections.addAll(layouts, R.layout.slide1, R.layout.slide2);
        layouts.forEach(s -> {
            if (layoutInflater != null) {
                viewFlipper.addView(layoutInflater.inflate(s, null));
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                formPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float toPosition = event.getX();
                if (formPosition > toPosition) {
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_out));
                    viewFlipper.showNext();
                } else if (formPosition < toPosition) {
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_out));
                    viewFlipper.showPrevious();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
