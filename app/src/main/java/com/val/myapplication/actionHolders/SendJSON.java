package com.val.myapplication.actionHolders;

import android.os.AsyncTask;
import android.util.Log;

import com.val.myapplication.Entity.GetRequest;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class SendJSON extends AsyncTask<Void,Void, GetRequest> {

   private GetRequest getRequest;
    @Override
    protected GetRequest doInBackground(Void... voids) {
        try {
            final  String url="https://api.myjson.com/bins/z4p4p";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            GetRequest getRequest =restTemplate.getForObject(url, GetRequest.class);
            this.getRequest = getRequest;
        }catch (Exception e){
            Log.e("MyTag",e.getMessage(),e);
        }

        return null;
    }


    public GetRequest getGetRequest() {
        return getRequest;
    }
}
