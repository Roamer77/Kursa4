package com.val.myapplication.TabsForMySlider;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.val.myapplication.Entity.DataBaseEntity.ScriptEntity;
import com.val.myapplication.R;
import com.val.myapplication.utils.RequestAPI;
import com.val.myapplication.utils.Response;
import com.val.myapplication.utils.dataBaseUtil.DBHelper;

import java.util.ArrayList;

//на нём отобразим скрипты
public class ScriptTab2 extends Fragment {


    private Button refreshButton;
    private Button deleteButton;
    private ListView scriptListView;
    private ArrayAdapter<String> arrayAdapter;
    private DBHelper dbHelper;

    private  String msgToServer;

    private  String  localServerUrl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.script_tab2, container, false);
        initView(view);
        registerForContextMenu(scriptListView);
        scriptListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SendJsonToServer sendJsonToServer=new SendJsonToServer();
                String text = (String) parent.getItemAtPosition(position);
                sendJsonToServer.execute();
                if (text != null) {
                    msgToServer=text;
                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, 1, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); //позволяет получить информацию о нажатом элементе списка
        String text=(String)scriptListView.getItemAtPosition(menuInfo.position);
        switch (item.getItemId()) {
            case 1:
                deleteDataByName(text);
                Toast.makeText(getActivity(), "Удалён  "+text, Toast.LENGTH_LONG).show();
                return  true;
            default:
                break;
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DBHelper(getActivity());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        localServerUrl=sharedPreferences.getString("LocalServerUrl","");
    }

    private void initView(View view) {
        refreshButton = view.findViewById(R.id.refrash_db_btn);
        scriptListView = view.findViewById(R.id.scripts_listView);
        deleteButton = view.findViewById(R.id.deletBtn);
        refreshButton.setOnClickListener(v -> {
            loadData();
        });
        deleteButton.setOnClickListener(v -> {
            deleteAllData();
        });
    }

    private void loadData() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("scripts", null, null, null, null, null, null);

        ArrayList<String> entityArrayList = new ArrayList<>();

        int nameIndex = cursor.getColumnIndex("name");
        int scriptDataIndex = cursor.getColumnIndex("scriptData");
        while (cursor.moveToNext()) {
            ScriptEntity tmp = new ScriptEntity();
            tmp.setScriptName(cursor.getString(nameIndex));
            tmp.setScriptContent(cursor.getString(scriptDataIndex));
            entityArrayList.add(tmp.getScriptName());
            Log.e("MyTag", "Data from db: " + tmp.getScriptContent() + " " + tmp.getScriptName());
        }
        cursor.close();
        sqLiteDatabase.close();
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.script_entity_layout, R.id.myTextView, entityArrayList);
        scriptListView.setAdapter(arrayAdapter);
    }

    private void deleteAllData() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("scripts", null, null);
        sqLiteDatabase.close();
    }
    private  void deleteDataByName(String name){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        //лучше использовать аргумента, нежели писать  всё одной строкой в where(если так делать крашится активити).
       int deleteCounter= sqLiteDatabase.delete("scripts "," name =? "  , new String[]{name});
        Log.e("MyTag","delete id "+deleteCounter);
        sqLiteDatabase.close();
    }

    protected  class SendJsonToServer extends AsyncTask<Void,Void, Response>{
        RequestAPI requestAPI=new RequestAPI();

        @Override
        protected Response doInBackground(Void... voids) {

            try {
                final String url = "http://"+localServerUrl+":8089"+"/main/helloAndroid";
                if(msgToServer!=null){
                    Log.e("MyTag","MsgToServer from SendJson "+msgToServer + "на url "+url);

                    SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("select scriptData from scripts where name=?;",new String[]{msgToServer});
                    String data="";
                    while (cursor.moveToNext()){
                        data= cursor.getString(0);
                        Log.e("MyTag","Cursor data from SendJson "+data);
                    }

                    cursor.close();
                    sqLiteDatabase.close();
                    if(!data.equals("")){
                        Log.e("MyTag","Отправляю вот такую строку  "+data);
                        requestAPI.doPostRequest(url,msgToServer);
                    }
                }
            }catch (Exception e){
                Log.e("MyTag","При отправке запроса в классе ScriptTab2 "+e);
            }
            return null;
        }
    }
}
