package com.val.myapplication.activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.val.myapplication.R;

import java.util.HashSet;

public class ConfigurationActivity extends AppCompatActivity {

    final String USER_CONFIG = "save user config";
    EditText mqttLogin;
    EditText mqttPassword;
    EditText mqttUrlServer;
    EditText mqttPort;
    EditText localServerUrl;
    Button configSaveBtn;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        toolbar=findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFB00\">" + "Настройки" + "</font>"));
        mqttLogin = (EditText) findViewById(R.id.Mqtt_login_et);
        mqttPassword = (EditText) findViewById(R.id.Mqtt_password_et);
        mqttUrlServer = (EditText) findViewById(R.id.Mqtt_servet_et);
        mqttPort = (EditText) findViewById(R.id.Mqtt_port_et);
        localServerUrl = (EditText) findViewById(R.id.Http_server_url_et);

        configSaveBtn = (Button) findViewById(R.id.config_seve_btn);
        configSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserConfigurations();
            }
        });
        loadUserConfigurations();

      Log.e("MyTag","Starage is Enable: "+ Environment.isExternalStorageEmulated());
    }

    private void setTextOnEditTextFilds() {
        mqttLogin.setText(mqttLogin.getText());
        mqttPassword.setText(mqttPassword.getText());
        mqttUrlServer.setText(mqttUrlServer.getText());
        mqttPort.setText(mqttPort.getText());
    }

    private HashSet<String> getAllInfo() {
        HashSet<String> userConfigs = new HashSet<>();
        userConfigs.add(mqttLogin.getText().toString());
        userConfigs.add(mqttPassword.getText().toString());
        userConfigs.add(mqttUrlServer.getText().toString());
        userConfigs.add(mqttPort.getText().toString());
        userConfigs.add(localServerUrl.getText().toString());
        return userConfigs;
    }

    private void saveUserConfigurations() {
        sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Login", mqttLogin.getText().toString());
        editor.putString("Password", mqttPassword.getText().toString());
        editor.putString("MqttUrlServer", mqttUrlServer.getText().toString());
        editor.putString("MqttPort", mqttPort.getText().toString());
        editor.putString("LocalServerUrl", localServerUrl.getText().toString());
        editor.commit();
        Toast.makeText(this.getApplicationContext(), "Настройки успешно сохранены", Toast.LENGTH_LONG).show();
    }

    private void loadUserConfigurations() {
        sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        mqttLogin.setText(sharedPreferences.getString("Login", ""));
        mqttPassword.setText(sharedPreferences.getString("Password", ""));
        mqttUrlServer.setText(sharedPreferences.getString("MqttUrlServer", ""));
        mqttPort.setText(sharedPreferences.getString("MqttPort", ""));
        localServerUrl.setText(sharedPreferences.getString("LocalServerUrl", ""));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}
