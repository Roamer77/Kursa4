package com.val.myapplication.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeActionBarColor {

    public static void changeActionBarColor(AppCompatActivity appCompatActivity,String title){
        ActionBar actionBar=appCompatActivity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E9424242")));
        actionBar.setTitle(Html.fromHtml("<font color=\"#FFFB00\">" + title + "</font>"));
    }
}
