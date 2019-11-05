package com.val.myapplication.TabsForMySlider;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.val.myapplication.Entity.DataBaseEntity.ScriptEntity;
import com.val.myapplication.Entity.SensorForScen;
import com.val.myapplication.R;
import com.val.myapplication.activityes.ScriptMakeActivity;
import com.val.myapplication.castomAdaptors.CustomScriptTabAdaptor;
import com.val.myapplication.castomAdaptors.ScriptMakerAdaptor;
import com.val.myapplication.utils.dataBaseUtil.Constants;
import com.val.myapplication.utils.dataBaseUtil.DBAdaptor;
import com.val.myapplication.utils.dataBaseUtil.DBHelper;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//на нём выбираем  датчики для скрипта
public class ScriptTab1 extends Fragment {
    private ListView listView;
    private Button saveBtn;
    private HashMap<Integer, Boolean> resData; //данные о датчиках которые будут включены в скрипт .Integer- id, Boolean- выбран ли датчик
    private HashMap<String, Map<Integer, Boolean>> dataForTab2;
    private ViewPager scriptViewPager;

    private  DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.script_tab1, container, false);
        listView = (ListView) view.findViewById(R.id.Script_listView);
        saveBtn = (Button) view.findViewById(R.id.save_script_btn);
        scriptViewPager = getActivity().findViewById(R.id.script_view_pager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper=new DBHelper(getActivity());

        ScriptMakerAdaptor adaptor = new ScriptMakerAdaptor(getData(), getActivity());
        listView.setAdapter(adaptor);


        resData = new HashMap<>();
        saveBtn.setOnClickListener(v -> {
            adaptor.getData().forEach(s -> {
                if (s.getIsSelected()) {
                    resData.put(s.getSensorId(), s.getIsSelected());
                }
            });
            if (getActivity() != null) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                initSaveDialog(alert);
                alert.show();
            }
        });

    }

    private ArrayList<SensorForScen> getData() {
        ArrayList<SensorForScen> sensorForScens = new ArrayList<>();
        sensorForScens.add(new SensorForScen(1, "Вентилятор", false));
        sensorForScens.add(new SensorForScen(2, "Свет", false));
        sensorForScens.add(new SensorForScen(3, "Двери", false));
        sensorForScens.add(new SensorForScen(4, "Серена", false));
        sensorForScens.add(new SensorForScen(5, "Куллер", false));
        return sensorForScens;
    }



    private String makeJsonString(HashMap<Integer, Boolean> hashMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String res = objectMapper.writeValueAsString(hashMap);
            return res;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Нет данных"; // не очень хорошо
    }
    private void initSaveDialog(AlertDialog.Builder alert) {
        alert.setTitle("Сохранить сценарий");
        alert.setMessage("Введите имя сценария");
        EditText editText = new EditText(getActivity());
        editText.setHint("Название скрипта");
        alert.setView(editText);



        alert.setPositiveButton("Сохранить", (dialog, which) -> {
            SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();

            ContentValues contentValues=new ContentValues();
            contentValues.put("name",editText.getText().toString());
            contentValues.put("scriptData",makeJsonString(resData));

            long id=sqLiteDatabase.insert("scripts",null,contentValues);
            Log.d("MyTag", "script name " + editText.getText().toString());
            Log.d("MyTag", "row inserted, ID = " + id);
            dbHelper.close();
        });
        alert.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
    }


}
/* код для создания из обекта строки json
            Gson gson=new Gson();
            String filename="myscripts.json";
                Writer writer=null;
            try {
                writer=new BufferedWriter(new OutputStreamWriter(getActivity().openFileOutput(filename,Context.MODE_PRIVATE))) ;
                gson.toJson(dataForTab2,writer);
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }*/