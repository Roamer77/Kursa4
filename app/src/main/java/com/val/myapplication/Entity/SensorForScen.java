package com.val.myapplication.Entity;

public class SensorForScen {
    private int sensorId;
    private String sensorName;
   private boolean  isSelected;

    public SensorForScen(int sensorId, String sensorName, boolean isSelected) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.isSelected = isSelected;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
