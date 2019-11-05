package com.val.myapplication.activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.val.myapplication.R;
import com.val.myapplication.TabsForMySlider.Tab2;
import com.val.myapplication.utils.ChangeActionBarColor;
import com.val.myapplication.utils.LayoutController;
import com.val.myapplication.utils.RequestAPI;
import com.val.myapplication.utils.Response;

import java.util.HashMap;

public class ExecutableDeviceActivity extends AppCompatActivity {
    private LayoutController layoutController;
    private Switch switcher;
    private int switcherIsChecked;
    private int deviseId;
    private ImageView ventilatorImageView;
    private ImageView svetImageViuew;
    private ImageView serenaImageView;
    private Animation animation;
    private String localServerUrl;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executable_device);
        toolbar=findViewById(R.id.exe_dev_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFB00\">" + "Настройки" + "</font>"));

        setLayoutToLayoutController();
        Bundle data = getIntent().getExtras();
        if (data != null) {
            int id = data.getInt("id");
            deviseId = id;
            Log.e("MyTag", "Смотри тут id:" + id);
            setContentView(layoutController.getLayOutById(id));
            setActionOnSwitcherForExeDevise(R.id.switch_ventilatorLayout);
            setActionOnSwitcherForExeDevise(R.id.switch_svetLayout);
            setActionOnSwitcherForExeDevise(R.id.switch_serenaLayout);
        }
        ventilatorImageView = (ImageView) findViewById(R.id.ventalator_iv);
        animation = AnimationUtils.loadAnimation(this, R.anim.ventelator_anim);
        svetImageViuew = (ImageView) findViewById(R.id.svet_iv);
        serenaImageView = (ImageView) findViewById(R.id.serena_iv);

        SharedPreferences sharedPreferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        localServerUrl=sharedPreferences.getString("LocalServerUrl", "");
    }


    private void setLayoutToLayoutController() {
        layoutController = new LayoutController();
        layoutController.setLayOut(6, R.layout.svet_layout);
        layoutController.setLayOut(7, R.layout.vantilaror_layout);
        layoutController.setLayOut(8, R.layout.serena_layout);
    }

    private void setActionOnSwitcherForExeDevise(int switcherId) {
        switcher = (Switch) findViewById(switcherId);

        if (switcher != null) {
            switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Intent intent=new Intent(ExecutableDeviceActivity.this, Tab2.class);

                    SendJsonToServer sendJsonToServer = new SendJsonToServer();
                    if (isChecked) {
                        switcherIsChecked = 1;
                        intent.putExtra("DeviceState","Вкл");
                        sendJsonToServer.execute();
                        imageOnDestributor(deviseId);

                    } else {
                        switcherIsChecked = 2;
                        imageOffDestributor(deviseId);
                        intent.putExtra("DeviceState","Выкл"+deviseId);
                        sendJsonToServer.execute();

                    }
                    Toast.makeText(ExecutableDeviceActivity.this, isChecked ? "Вкл" : "Выкл", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    protected class SendJsonToServer extends AsyncTask<Void, Void, Response> {
        RequestAPI requestAPI = new RequestAPI();
        HashMap<String, String> msgServer = new HashMap<>();

        @Override
        protected Response doInBackground(Void... voids) {
            if (switcherIsChecked == 1) {
                msgServer.put(String.valueOf(deviseId), "Влk");
            } else if (switcherIsChecked == 2) {
                msgServer.put(String.valueOf(deviseId), "Выkл");
            }

            try {
                //не забыть указать порт
                final String url = "http://"+localServerUrl+":8089"+"/main/helloAndroid";
                Log.e("MyTag","ServerUrl= "+localServerUrl);
                requestAPI.doPostRequest(url, msgServer);
            } catch (Exception e) {
                Log.e("MyTag", e.getMessage(), e);
            }
            return null;
        }
    }

    private void setVentilatorAnimation(Animation animation) {
        animation.setRepeatCount(5);
        ventilatorImageView.startAnimation(animation);

    }

    private void stopVentilatorAnamation(Animation animation) {
        //как остановить аниаацию нужно придумать
    }

    private void setSvetOnImage() {
        svetImageViuew.setImageResource(R.mipmap.svet);
    }

    private void setSvetOffImage() {
        svetImageViuew.setImageResource(R.mipmap.svet_off);
    }

    private void setSerenaOnImage() {
        serenaImageView.setImageResource(R.mipmap.serena);
    }

    private void setSerenaOffImage() {
        serenaImageView.setImageResource(R.mipmap.serena_off);
    }

    private void imageOnDestributor(int id) {
        switch (id) {
            case 6:
                setSvetOnImage();
                break;
            case 7:
                setVentilatorAnimation(animation);
                break;
            case 8:
                setSerenaOnImage();
                break;
            default:
                break;
        }
    }
    private void imageOffDestributor(int id) {
        switch (id) {
            case 6:
                setSvetOffImage();
                break;
            case 7:
                stopVentilatorAnamation(animation);
                break;
            case 8:
                setSerenaOffImage();
                break;
            default:
                break;
        }
    }
}
