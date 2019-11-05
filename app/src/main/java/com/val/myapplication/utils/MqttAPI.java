package com.val.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.val.myapplication.activityes.ConfigurationActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
// server url =tcp://soldier.cloudmqtt.com:11237
//password = 95ey6sAO7iGa
//login= ponymwxp
public class MqttAPI {
    private MqttAndroidClient client;
    private String userPassword;
    private String userLogin;
    public MqttAPI(Context context,String login,String password,String serverUrl,String serverPort) {
        this.userLogin=login;
        this.userPassword=password;
        String clientId = MqttClient.generateClientId(); //это уникальный клиентский id
        this.client = new MqttAndroidClient(context, "tcp://"+serverUrl+":"+serverPort,
                clientId); //необходимо вторым парамметром передать сервер

    }
    //подключение
    public   void connect(String id){
        MqttConnectOptions connectOptions=new MqttConnectOptions();
        connectOptions.setUserName(userLogin);
        connectOptions.setPassword(userPassword.toCharArray());

        try {
            client.connect(connectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    /*DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    client.setBufferOpts(disconnectedBufferOptions);//узнать подробнее*/
                    Log.e("MyTag","Я подключился к серверу");
                    subscribe(id);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    //оформить подписку
    public   void subscribe(String id){
        if(client.isConnected()){
            String topic = "test/"+id;
            int qos = 1;
            try {
                client.subscribe(topic, qos, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.e("MyTag","Я подписался на топик:"+topic);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.e("MyTag","Ошибка я не подписался");
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }else  {
            Log.e("MyTag","Client не подключился");
        }

    }

    //получение сообщений
    public void getGreetingMessage(){
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.e("MyTag",message.toString());

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public  void setCallBack(MqttCallbackExtended callBack){
        client.setCallback(callBack);
    }


}
