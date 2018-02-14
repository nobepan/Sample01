package com.websarva.wings.android.intentsample2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    static final String DB = "sqlite_sample.db";
    static final int DB_VERSION = 1;
    static final String CREATE_TABLE = "create table tablereminder ( _id integer primary key autoincrement, date text not null, title text not null );";
    static final String DROP_TABLE = "drop table tablereminder;";

    public MySQLiteOpenHelper(Context c) {
        super(c, DB, null, DB_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
