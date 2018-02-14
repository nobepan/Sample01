package com.websarva.wings.android.intentsample2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<DataReminder> listdata;
    DataReminder data;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        this.init();
    }




    public void init() {

        ListView lvMenu = (ListView) findViewById(R.id.lvMenu);
        List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();

        DbReminder	db = new DbReminder(new MySQLiteOpenHelper(this).getWritableDatabase());

        listdata = db.getAllData();
         for (int i = 0; i<listdata.size(); i++) {
        data = listdata.get(i);
            Log.d("MenuListActivity",data.id + ":" + data.title);
            Map<String, String> menu = new HashMap<String, String>();
            menu.put("title", data.title);
            menu.put("date", data.date);
            menuList.add(menu);
        }
        //System.out.println("OKinit");
        String[] from = {"date", "title"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        adapter = new SimpleAdapter(MenuListActivity.this, menuList, android.R.layout.simple_list_item_2, from, to);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(this);
        //








    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        data = listdata.get(position);
        Intent intent = new Intent(MenuListActivity.this, MenuThanksActivity.class);
        intent.putExtra("id", data.id);
        startActivity(intent);
    }

    public void onClickNew(View view) {
        Intent intent = new Intent(MenuListActivity.this, com.websarva.wings.android.intentsample2.MenuThanksActivity.class);
        startActivity(intent);
    }

    public void onClickDelete(View view) {
        DbReminder	db = new DbReminder(new MySQLiteOpenHelper(this).getWritableDatabase());
        db.deleteData(data);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MenuListActivity","onRestart");
        this.init();
    }
}
