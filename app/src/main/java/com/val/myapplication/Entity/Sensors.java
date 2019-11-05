package com.val.myapplication.Entity;

public class Sensors {
    private  int sensorId;
    private  String sensorImgName;
    private  String sensorName;
    private  String sensorState;

    public Sensors(int sensorId, String sensorImgName, String sensorName) {
        this.sensorId = sensorId;
        this.sensorImgName = sensorImgName;
        this.sensorName = sensorName;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorImgName() {
        return sensorImgName;
    }

    public void setSensorImgName(String sensorImgName) {
        this.sensorImgName = sensorImgName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorState() {
        return sensorState;
    }

    public void setSensorState(String sensorState) {
        this.sensorState = sensorState;
    }

    @Override
    public String toString() {
        return "Sensors{" +
                "sensorId=" + sensorId +
                ", sensorImgName='" + sensorImgName + '\'' +
                ", sensorName='" + sensorName + '\'' +
                ", sensorState='" + sensorState + '\'' +
                '}';
    }
}
