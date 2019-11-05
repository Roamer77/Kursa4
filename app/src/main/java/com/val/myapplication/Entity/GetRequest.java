package com.val.myapplication.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GetRequest {

    @JsonProperty("content")
    private Map<String,String> content;


    public Map<String, String> getContent() {
        return content;
    }
}
