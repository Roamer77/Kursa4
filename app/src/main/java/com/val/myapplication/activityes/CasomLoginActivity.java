package com.val.myapplication.activityes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.val.myapplication.R;
import  com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


public class CasomLoginActivity extends AppCompatActivity {

    FloatingActionButton registrationFab;
    FloatingActionButton configFab;
    FloatingActionMenu fam;
    Button btnTest;
    TextView loginTv;
    TextView passwordTv;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casom_login);

        toolbar=findViewById(R.id.login_screen_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFB00\">" + "Умный дом ПГУ" + "</font>"));


        fam=(FloatingActionMenu) findViewById(R.id.fab_menu);
        btnTest=(Button) findViewById(R.id.btnForTest);
        loginTv=(TextView) findViewById(R.id.LoginTv);
        passwordTv=(TextView)findViewById(R.id.PasswordTv);
        fam.setOnMenuToggleListener(opened -> {
            if (opened) {
                makeElementsInvisible(View.INVISIBLE);
            } else {
                showToast("Menu is closed");
                makeElementsInvisible(View.VISIBLE);
            }
        });


        btnTest.setOnClickListener(v -> {
                   // Intent intent =new Intent(this, SliderTest.class);
                    Intent intent =new Intent(this, SliderTest.class);
                    startActivity(intent);
        }
        );
        registrationFab=(FloatingActionButton) findViewById(R.id.RegistrationFab);
        registrationFab.setOnClickListener(s->{
            Intent intent=new Intent(this,RegistationActivity.class);
            startActivity(intent);
        });
        configFab=(FloatingActionButton) findViewById(R.id.ConfigurationFab);
        configFab.setOnClickListener(s->{
            Intent intent=new Intent(this,ConfigurationActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT);
    }

    //делает видимым и невидемым элементы текущего экрана
    private  void makeElementsInvisible(int state){
        btnTest.setVisibility(state);
        loginTv.setVisibility(state);
        passwordTv.setVisibility(state);
    }


}
