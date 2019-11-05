package com.val.myapplication.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;

public class Response {

    private String state;

    private Object data;

    public Response() {
    }

    public <T extends Map> Response(String state, T data) {
        this.state = state;
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public Object getData() {
        return data;
    }
}
