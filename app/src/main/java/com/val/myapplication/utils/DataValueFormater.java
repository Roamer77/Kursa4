package com.val.myapplication.utils;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataValueFormater extends ValueFormatter {
    private final BarLineChartBase<?> chart;
    public DataValueFormater(BarLineChartBase<?> chart) {
        this.chart = chart;
    }
    @Override
    public String getFormattedValue(float value) {
        String data=new SimpleDateFormat("HH:mm:ss").format(new Date((long) (value*1000)));
        return data;
    }
}
