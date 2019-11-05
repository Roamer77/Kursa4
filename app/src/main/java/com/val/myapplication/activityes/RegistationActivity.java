package com.val.myapplication.activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import com.val.myapplication.Entity.UserInfo;
import com.val.myapplication.R;
import com.val.myapplication.utils.ChangeActionBarColor;
import com.val.myapplication.utils.RequestAPI;
import com.val.myapplication.utils.Response;



public class RegistationActivity extends AppCompatActivity {

    EditText etUserName;
    EditText etUserPassword;
    Button registrationBtn;
    UserInfo userInfo;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        toolbar=findViewById(R.id.registration_toolbar);
        setSupportActionBar(toolbar);
        setTitle(Html.fromHtml("<font color=\"#FFFB00\">" + "Умный дом ПГУ" + "</font>"));

        etUserName=(EditText) findViewById(R.id.etUserName);
        etUserPassword=(EditText) findViewById(R.id.etPasswrd);
        registrationBtn=(Button) findViewById(R.id.resSubmitBtn);

        registrationBtn.setOnClickListener(s->{
            userInfo =new UserInfo(etUserPassword.getText().toString(),etUserName.getText().toString());
            Log.e("MyTag"," Данные для отправки "+userInfo.getLogin()+" "+userInfo.getPassword());
                SendJsonToServer sendJsonToServer=new SendJsonToServer();
                sendJsonToServer.execute();

        });

    }


    protected class SendJsonToServer extends AsyncTask<Void,Void,Response> {
        RequestAPI requestAPI =new RequestAPI();
        @Override
        protected Response doInBackground(Void... voids) {
            try{
                final String url="http://192.168.1.2:8089/main/helloAndroid";
                requestAPI.doPostRequest(url,userInfo);
            }catch (Exception e){
                Log.e("MyTag",e.getMessage(),e);
            }
            return null;
        }
    }

}
