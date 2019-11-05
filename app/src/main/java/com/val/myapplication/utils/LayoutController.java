package com.val.myapplication.utils;

import java.util.HashMap;

public class LayoutController {
    private HashMap<Integer,Integer> layOutMap=new HashMap<>();

    public  Integer getLayOutById(Integer sensorId) {
        return layOutMap.get(sensorId);
    }
    public  void setLayOut(Integer sensorId,Integer layoutId){
        layOutMap.put(sensorId,layoutId);
    }

}
