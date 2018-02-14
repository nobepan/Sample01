package com.websarva.wings.android.intentsample2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by papa on 2017/12/14.
 */

public class DbReminder  {
    private SQLiteDatabase db;
    public static final String              TABLE_NAME = "tablereminder";
    public static final String[]    COLUMS = {"_id","date","title"};
    public int id;
    //======================================================================================================================
    public DbReminder( SQLiteDatabase db ) {
        this.db = db;
    }
    //======================================================================================================================
    public List<DataReminder> getAllData() {
        id = 0;
        String date = "";
        String title = "";
        List<DataReminder> dataArray = new ArrayList();
        Cursor cursor = db.query(TABLE_NAME, COLUMS, null, null, null, null, "date asc");
        while ( cursor.moveToNext() ){
            id           = cursor.getInt(0);
            date         = cursor.getString(1);
            title        = cursor.getString(2);
            DataReminder data = new DataReminder();
            data.id = id;
            data.date = date;
            data.title = title;
            dataArray.add(data);
        }
        cursor.close();

        return dataArray;
    }
    //======================================================================================================================
    public DataReminder getData(int id) {
        String where = "_id=" + id;
        String date = "";
        String title = "";
        Cursor cursor = db.query(TABLE_NAME, COLUMS, where, null,
                null, null, null);
        if( cursor.moveToNext() ){
            date         = cursor.getString(1);
            title        = cursor.getString(2);
        }
        cursor.close();
        DataReminder data = new DataReminder();
        data.date = date;
        data.title = title;
        return( data );
    }
    //======================================================================================================================
    public long updateData(DataReminder data) {
        ContentValues values = new ContentValues();
        values.put("date", data.date);
        values.put("title", data.title);
        return( db.update(TABLE_NAME, values, "_id="+data.id,
                null) );
    }
    //======================================================================================================================
    public long insertData(DataReminder data) {
        ContentValues values = new ContentValues();
        values.put("date", data.date);
        values.put("title", data.title);
        return( db.insert(TABLE_NAME, null, values) );
    }
    //======================================================================================================================
    public long deleteData(DataReminder data) {
        ContentValues values = new ContentValues();
        return ( db.delete(TABLE_NAME, "_id like '%'", null) );
    }
    //======================================================================================================================

    public void close() {
        db.close();
    }
    //======================================================================================================================
}
//======================================================================================================================
//======================================================================================================================
