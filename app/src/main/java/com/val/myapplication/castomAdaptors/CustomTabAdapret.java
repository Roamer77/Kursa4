package com.val.myapplication.castomAdaptors;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.val.myapplication.TabsForMySlider.Tab1;
import com.val.myapplication.TabsForMySlider.Tab2;

public class CustomTabAdapret extends FragmentStatePagerAdapter {

    private int tubNumber;

    public CustomTabAdapret(FragmentManager fm, Integer tubNamber) {
        super(fm);
        this.tubNumber = tubNamber;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab1();
            case 1:
                return new Tab2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tubNumber;
    }
}
