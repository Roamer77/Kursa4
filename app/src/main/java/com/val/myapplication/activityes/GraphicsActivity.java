package com.val.myapplication.activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.val.myapplication.Entity.GetRequest;
import com.val.myapplication.R;
import com.val.myapplication.utils.DataValueFormater;
import com.val.myapplication.utils.RequestAPI;
import com.val.myapplication.utils.Response;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class GraphicsActivity extends AppCompatActivity {
    LineChart lineChart;
    Toolbar toolbar;
    private GetRequest temeratureDataFromServer;
    private GetRequest humidityDataFromServer;
    private List<Entry> temperatureChartEntries;
    private List<Entry> humidityChartEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFB00\">" + "Статистика" + "</font>"));

        lineChart = (LineChart) findViewById(R.id.graphic1);

        SendJsonToServer sendJsonToServer = new SendJsonToServer();
        sendJsonToServer.execute();
        temperatureChartEntries = new ArrayList<>();// хранятся данные от датчика температуры
        humidityChartEntries = new ArrayList<>();// хронятся данные от датчика влажности

    }

    // инициализируем таргет промт
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphics_menu, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                new MaterialTapTargetPrompt.Builder(GraphicsActivity.this)
                        .setTarget(toolbar.getChildAt(1))
                        .setPrimaryText("Выбор графиков ")
                        .setSecondaryText("Тут можно посмотреть графики отражающие статистику")
                        .setBackgroundColour(Color.parseColor("#E9424242"))
                        .setPrimaryTextColour(Color.parseColor("#FFFB00"))
                        .setSecondaryTextColour(Color.parseColor("#FFFB00"))
                        .show();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this.getApplicationContext(), "График температуры", Toast.LENGTH_LONG).show();
                if (temperatureChartEntries.size() != 0) {
                    initNewChart(lineChart, temperatureChartEntries, "График температуры", "Температура");
                }
                return true;
            case R.id.action_settings1:
                if (humidityChartEntries.size() != 0) {
                    initNewChart(lineChart, humidityChartEntries, "График влажности", "Влажноть");
                }
                Toast.makeText(this.getApplicationContext(), "График влажности", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initNewChart(LineChart lineChart, List<Entry> dataList, String chartName, String lineName) {

        LineDataSet lineDataSet = new LineDataSet(dataList, lineName); //придумать название этому полю
        lineDataSet.setColor(Color.RED);
        LineData lineData = new LineData(lineDataSet);
        lineChart.getDescription().setText(chartName);
        lineChart.notifyDataSetChanged();
        XAxis xAxis = lineChart.getXAxis();
        ValueFormatter valueFormatter = new DataValueFormater(lineChart);
        xAxis.setValueFormatter(valueFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    ///запрос на данные для графиков
    protected class SendJsonToServer extends AsyncTask<Void, Void, Response> {
        RequestAPI requestAPI = new RequestAPI();
        SharedPreferences sharedPreferences=getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        @Override
        protected Response doInBackground(Void... voids) {
           String serverUrl=sharedPreferences.getString("LocalServerUrl",""); // его потом вставлять после http://
            try {
                final String url = "https://api.myjson.com/bins/am5i4";//для температуры
                final String url1 = "https://api.myjson.com/bins/i7vqw";//для влажности
                temeratureDataFromServer = requestAPI.doGetRequest(url);
                humidityDataFromServer = requestAPI.doGetRequest(url1);
            } catch (Exception e) {
                Log.e("MyTag", e.getMessage(), e);
            }
            return null;
        }

        //заполняем данными колекции для графиков
        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            fillChartListByData(temeratureDataFromServer, temperatureChartEntries);
            fillChartListByData(humidityDataFromServer, humidityChartEntries);
        }
    }

    //этот метод разбиват даные пришежшие от сервера на составляющие (т.к они представлены в коде Java как map)
    // класс представленя данных называеться GetRequest
    private void fillChartListByData(GetRequest dataFromServer, List<Entry> dataForChart) {
        if (dataFromServer != null) {
            //разбираем map c данными на составляющие
            //получаем "ключи"
            Map<String, String> tmp = dataFromServer.getContent();
            Set<String> keySet = tmp.keySet();
            String[] keys = new String[keySet.size()];
            keys = keySet.toArray(keys); //получаем массив ключей

            // получаем "значения"
            Collection<String> Colvalues = tmp.values();
            String[] values = new String[Colvalues.size()];
            values = Colvalues.toArray(values); //получаем массив значений
            if (values.length != 0) {
                //закидываем  в колекцию для нашего граффика
                for (int i = 0; i < values.length; i++) {
                    //конвертировать ключи(время unix timestep) в нормальную дату
                    dataForChart.add(new Entry(Float.valueOf(keys[i]), Float.valueOf(values[i])));
                }
            }
            Log.e("MyTag", "Фоновая задача всё доделала");
        }
    }
}
