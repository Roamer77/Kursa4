package com.val.myapplication.activityes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;
import com.val.myapplication.Entity.DataBaseEntity.ScriptEntity;
import com.val.myapplication.Entity.SensorForScen;
import com.val.myapplication.R;

import com.val.myapplication.castomAdaptors.CustomScriptTabAdaptor;
import com.val.myapplication.castomAdaptors.ScriptMakerAdaptor;
import com.val.myapplication.utils.dataBaseUtil.DBAdaptor;


import java.util.ArrayList;

public class ScriptMakeActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_make_activity);

        tabLayout = (TabLayout) findViewById(R.id.script_tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText("Конструктор сценария"));
        tabLayout.addTab(tabLayout.newTab().setText("Сценарии"));

        viewPager = (ViewPager) findViewById(R.id.script_view_pager);
        viewPager.setAdapter(new CustomScriptTabAdaptor(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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


    }




}
