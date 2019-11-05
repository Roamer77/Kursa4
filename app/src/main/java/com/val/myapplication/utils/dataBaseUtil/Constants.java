package com.val.myapplication.utils.dataBaseUtil;

public class Constants {
    static final String ROW_ID="id";
    static final String NAME="name";
    static final String CONTENT="content";

    static final String DATABASE_NAME="lv_DB";
    static final String TABLE_NAME="lv_TB";
    static final int DATABASE_VERSION=1;

    static final String CREATE_TABLE_STRING="CREATE TABLE lv_TB(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, content TEXT NOT NULL);";
    static final String DROP_TB="DROP TABLE IF EXISTS "+TABLE_NAME;

}
