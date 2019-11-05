package com.val.myapplication.TabsForMySlider;

import android.content.Intent;
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

import com.val.myapplication.Entity.Sensors;
import com.val.myapplication.R;
import com.val.myapplication.activityes.ExecutableDeviceActivity;
import com.val.myapplication.castomAdaptors.CustomLayOutAdaptor;

import java.util.ArrayList;
import java.util.List;

public class Tab2  extends Fragment {
    private List<Sensors> exeDevices;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab2,container,false);
        exeDevices=getExeDeviseListData();
        ListView listViewForExeDevises= (ListView) view.findViewById(R.id.Tab2exeDeviceListView);
        CustomLayOutAdaptor customLayOutAdaptor=new CustomLayOutAdaptor(exeDevices,getActivity());
        listViewForExeDevises.setAdapter(customLayOutAdaptor);
        listViewForExeDevises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listViewForExeDevises.getItemAtPosition(position);
                Sensors sensors = (Sensors) o;
                Toast.makeText(getActivity(), "Selected :" + " " +sensors.getSensorId(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(), ExecutableDeviceActivity.class);
                intent.putExtra("id",sensors.getSensorId());
                startActivity(intent);
            }
        });
        return  view;
    }
    private List<Sensors> getExeDeviseListData(){
        List <Sensors> res=new ArrayList<>();
        Sensors light = new Sensors(6, "svet", "Свет");
        Sensors ventilator = new Sensors(7, "ventilator", "Вентилятор");
        Sensors serena = new Sensors(8, "serena", "Сирена ");
        res.add(light);
        res.add(ventilator);
        res.add(serena);
        return res;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Bundle intent=getActivity().getIntent().getExtras();
            String tmp=intent.getString("DeviceState");
            Log.e("MyTag","DataTest="+Integer.valueOf(tmp));
        } catch (NullPointerException e){
            Log.e("MyTag",e.getMessage());
        }


    }
}
