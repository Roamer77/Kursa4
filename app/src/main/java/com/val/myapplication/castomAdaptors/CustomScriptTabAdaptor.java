package com.val.myapplication.castomAdaptors;

import android.util.Log;
import android.widget.BaseAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.val.myapplication.TabsForMySlider.ScriptTab1;
import com.val.myapplication.TabsForMySlider.ScriptTab2;

public class CustomScriptTabAdaptor extends FragmentStatePagerAdapter {
    private  int tabNumber;

    public CustomScriptTabAdaptor(FragmentManager fm) {
        super(fm);
    }

    public CustomScriptTabAdaptor(FragmentManager fm, Integer tabNumber) {
        super(fm);
        this.tabNumber=tabNumber;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ScriptTab1();
            case 1:
                return new ScriptTab2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }
}
