package com.val.myapplication.activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import android.widget.TextView;

import com.val.myapplication.R;
import com.val.myapplication.utils.MqttAPI;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SensorControlActivity extends AppCompatActivity {
    // тут буду изменять данные на лайаутах в зависимоти от датчика
    //в зависимоти от id буду ставить новый layout
    TextView textView;
    TextView sensorNameTv;
    SharedPreferences sharedPreferences;
    MqttAPI mqttAPI;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_control);

        toolbar=findViewById(R.id.sensors_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFB00\">" + "Настройки" + "</font>"));

        textView=(TextView) findViewById(R.id.tvDataFromServer);
        sensorNameTv=(TextView) findViewById(R.id.tvSensorName);

        sharedPreferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        String login=  sharedPreferences.getString("Login","");
        String password=sharedPreferences.getString("Password","");
        String serverUrl=sharedPreferences.getString("MqttUrlServer", "");
        String serverPort=sharedPreferences.getString("MqttPort", "");

        mqttAPI=new MqttAPI(this,login,password,serverUrl,serverPort);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            int id = data.getInt("id");
            String sensorName=data.getString("name");
            Log.e("MyTag","Зашел на "+id+" Название"+sensorName);

            sensorNameTv.setText(sensorName);
            mqttAPI.connect(String.valueOf(id));
        }

        mqttAPI.setCallBack(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                    textView.setText(message.toString());
                    Log.e("MyTag","ServerMsg "+message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }


}
