package com.val.myapplication.TabsForMySlider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.val.myapplication.Entity.Sensors;
import com.val.myapplication.R;
import com.val.myapplication.activityes.SensorControlActivity;
import com.val.myapplication.activityes.TemperaturePreashureActivity;
import com.val.myapplication.castomAdaptors.CustomLayOutAdaptor;
import com.val.myapplication.utils.MqttAPI;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab1 extends Fragment {
    private  CustomLayOutAdaptor customLayOutAdaptor;
    private  String updateData;
    private  MqttAPI mqttAPI;
    private  List<Sensors>sensorsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.tab1,container,false);
         sensorsList = getListData();
        ListView listView = (ListView) view.findViewById(R.id.Tab1sensorsListView);
        customLayOutAdaptor=new CustomLayOutAdaptor(sensorsList, getActivity());
        listView.setAdapter(customLayOutAdaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Object o = listView.getItemAtPosition(position);
                Sensors sensors = (Sensors) o;
                Toast.makeText(getActivity(), "Selected :" + " " + sensors.getSensorId(), Toast.LENGTH_LONG).show();
                if (sensors.getSensorId() == 5) {
                    Intent intent = new Intent(getActivity() , TemperaturePreashureActivity.class);
                    intent.putExtra("id", sensors.getSensorId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), SensorControlActivity.class);
                    Log.e("MyTag","Tab1 select "+sensors.getSensorId());
                    intent.putExtra("id", sensors.getSensorId());
                    intent.putExtra("name",sensors.getSensorName());
                    Log.e("MyTag","Отравил= "+sensors.getSensorName());
                    startActivity(intent);
                }
            }
        });

        return view;
    }
    private List<Sensors> getListData() {
        List<Sensors> list = new ArrayList<>();
        Sensors gercon = new Sensors(0, "door_sensor_alarmed", "Герконовый датчик");
        Sensors fireSensor = new Sensors(1, "fire_sensor", "Пламяизвещатель");
        Sensors lightSensor = new Sensors(2, "light_sensor", "Датчик освещенности");
        Sensors smokeSensor = new Sensors(3, "smoke_sensor", "Датчик дыма");
        Sensors voltAmper = new Sensors(4, "volt_amper", "Вольт-амперметор");
        Sensors temperatureSensorHome = new Sensors(5, "humudity_temp_home", "Температура и влажность");
        list.add(gercon);
        list.add(fireSensor);
        list.add(lightSensor);
        list.add(smokeSensor);
        list.add(voltAmper);
        list.add(temperatureSensorHome);
        return list;
    }
    //спарсить данные и закинуть их в SensoreState в объект в SensorList
    private  void updateInfoInList(String update,CustomLayOutAdaptor customLayOutAdaptor,List<Sensors>sensors){
        Map<String,String> tmpData=testJSONPars(update);
        List<Sensors> tmp=sensors;
        for (int i=0;i<tmp.size();i++){
            tmp.get(i).setSensorState(tmpData.get(String.valueOf(i+1)));
        }
        customLayOutAdaptor.notifyDataSetChanged();
        Log.e("MyTag","Я изменил");
    }
    private  Map<String,String> testJSONPars(String update){
        // {"1":"20", "2":"open","3":"27'C","4":"20","5":"open","6":"data"} -формат отпраавления данных
        Map<String,String> mapWithData=new HashMap<>();
        if(update!=null){
            try {
                Log.e("MyTag","Update от Mqtt: "+update);
                ObjectMapper objectMapper=new ObjectMapper();
                mapWithData= objectMapper.readValue(update,Map.class);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("MyTag","Траблы"+e);
            }
        }else {
            Log.e("MyTag","Траблы"+" Строка пустая"+update);

        }
        return mapWithData;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        String login=  sharedPreferences.getString("Login","");
        String password=sharedPreferences.getString("Password","");
        String serverUrl=sharedPreferences.getString("MqttUrlServer", "");
        String serverPort=sharedPreferences.getString("MqttPort", "");

        Log.e("MyTag","Login-"+login);
        mqttAPI =new MqttAPI(getActivity(),login,password,serverUrl,serverPort);
        mqttAPI.connect("10");//любая цифра для топика которая не совпадает с id датчиа можно к примеру сразу 99)

        mqttAPI.setCallBack(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                updateData=message.toString();
                Log.e("MyTag","ServerMsg "+message.toString());
                updateInfoInList(updateData,customLayOutAdaptor,sensorsList);

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });



    }
}
