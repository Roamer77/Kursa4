package com.val.myapplication.utils.dataBaseUtil;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "MyDataBase", null, 1);
        Log.e("MyTag","База создана");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("create table scripts ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "scriptData text" + ");");
            Log.e("MyTag","Таблица создана");

        }catch (SQLException e){
            Log.e("MyTag","При создании базы данных "+ e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(Constants.DROP_TB);
                db.execSQL(Constants.CREATE_TABLE_STRING);
            }catch (SQLException e){
                Log.e("MyTag","При бновлении таблицы "+ e.getMessage());

            }
    }
}
