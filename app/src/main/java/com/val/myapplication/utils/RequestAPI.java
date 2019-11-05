package com.val.myapplication.utils;

import com.val.myapplication.Entity.GetRequest;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RequestAPI {
    //отправит любой объект @param dataForSending ввиде JSON по указанной ссылке @param url
    public void doPostRequest(String url,Object dataForSending){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(url,dataForSending,String.class);
    }
    //для получения любых данных от сервера. Запросс делаеться по ссылке @param url, данные сохраняються в @param dataFromServer
    //Потом заменить GetRequest на класс представляющий датчики
    public GetRequest doGetRequest(String url){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        GetRequest data = restTemplate.getForObject(url, GetRequest.class);
        return data;
    }
}
