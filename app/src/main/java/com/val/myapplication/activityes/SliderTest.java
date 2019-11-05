package com.val.myapplication.activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.val.myapplication.R;
import com.val.myapplication.castomAdaptors.CustomTabAdapret;
import com.val.myapplication.castomAdaptors.ScriptMakerAdaptor;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class SliderTest extends AppCompatActivity {

    FloatingActionButton graphicsBtn;
    FloatingActionButton scriptBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_test);

        graphicsBtn =(FloatingActionButton) findViewById(R.id.GraphicsBtn);
        scriptBtn=(FloatingActionButton) findViewById(R.id.ScriptFab);
        TabLayout layout=(TabLayout) findViewById(R.id.tab_layout);
        layout.addTab(layout.newTab().setText("Датчики"));
        layout.addTab(layout.newTab().setText("Исполнительные устройства"));
        layout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager=(ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new CustomTabAdapret(getSupportFragmentManager(),layout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(layout));
        layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        graphicsBtn.setOnClickListener(v -> {
            Intent intent=new Intent(this,GraphicsActivity.class);
            startActivity(intent);
        });
        scriptBtn.setOnClickListener(v->{
            Intent intent1=new Intent(this, ScriptMakeActivity.class);
            startActivity(intent1);
        });

    }

}
