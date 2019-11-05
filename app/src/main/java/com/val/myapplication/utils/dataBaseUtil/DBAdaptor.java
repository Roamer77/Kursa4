package com.val.myapplication.utils.dataBaseUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.val.myapplication.Entity.DataBaseEntity.ScriptEntity;

import java.util.ArrayList;

public class DBAdaptor {
    private Context context;
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DBAdaptor(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public boolean saveScript(ScriptEntity scriptEntity) {
        try {
            database = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.NAME, scriptEntity.getScriptName());
            contentValues.put(Constants.CONTENT, scriptEntity.getScriptContent());
            long result = database.insert(Constants.TABLE_NAME, Constants.ROW_ID, contentValues);
            if (result > 0) {
                Log.e("MyTag","Строка добавлена");
                return true;
            }
        } catch (SQLException e) {
            Log.e("MyTag","При сохранении строки: "+ e.getMessage());
        } finally {
            database.close();
        }
        return false;
    }
    public   ArrayList<ScriptEntity>  getAllScripts(){

        ArrayList<ScriptEntity> scriptEntityArrayList=new ArrayList<>();

        try {
            database=dbHelper.getWritableDatabase();
            Cursor cursor=database.rawQuery("SELECT * FROM "+Constants.TABLE_NAME,null);

            ScriptEntity tmpEntity;
            scriptEntityArrayList.clear();
            while (cursor.moveToNext()){

                String name=cursor.getString(1);
                String content=cursor.getString(2);

                tmpEntity=new ScriptEntity(name,content);
                scriptEntityArrayList.add(tmpEntity);
            }

        }catch (SQLException e) {
            Log.e("MyTag","При получении строк: "+ e.getMessage());
        } finally {
            database.close();
        }
        return scriptEntityArrayList;
    }
}
