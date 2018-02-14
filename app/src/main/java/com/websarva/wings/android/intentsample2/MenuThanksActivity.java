package com.websarva.wings.android.intentsample2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MenuThanksActivity extends Activity implements OnDateChangeListener {
    EditText edText;
    CalendarView calendarView;
    String date = "";
    int id = 0;

    public MenuThanksActivity () {
        System.out.println("コンストラクタ");
        //onCreate();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_thanks);
        System.out.println("OK3");
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);



        edText = (EditText) findViewById(R.id.edText);
        calendarView = (CalendarView) findViewById(R.id.cal);
        calendarView.setOnDateChangeListener(this);


        DbReminder	db = new DbReminder(new MySQLiteOpenHelper(this).getWritableDatabase());

        if (id > 0) {
            DataReminder data = db.getData(id);
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern( "yyyy/MM/dd" );
            try {
                java.util.Date dat = sdf.parse(data.date);
                calendarView.setDate(dat.getTime());
            }
            catch (Exception e){
                e.printStackTrace();
            }

            edText.setText(data.title);
        }
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.YEAR ) + "/" + (calendar.get(Calendar.MONTH )+1) + "/" + calendar.get(Calendar.DATE);
    }

    public void onSelectedDayChange (CalendarView view, int year, int month, int day) {
        date = year + "/" + (month +1) + "/" + day;
        Log.d("onSelectedDayChange",date);
    }

    public void onSaveButtonClick(View view) {
        DbReminder	db = new DbReminder(new MySQLiteOpenHelper(this).getWritableDatabase());
        DataReminder data = new DataReminder();

        data.title = edText.getText().toString();;
        data.date = date;
        if (id > 0) {
            data.id = id;
            db.updateData(data);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            // Intent のインスタンス生成
            Intent intent = new Intent(getApplicationContext(), NotifiBroadcastReceiver.class);
            intent.putExtra("id", id);
            // Broadcast にメッセージを送るための設定
            //PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    5*1000, // 1sec = 1000
                    pending
            );
        }else {
            int id = (int)db.insertData(data);
            Log.d("ChkIDonSaveButtonClick1", "" + id);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            // Intent のインスタンス生成
            Intent intent = new Intent(getApplicationContext(), NotifiBroadcastReceiver.class);
            intent.putExtra("id", id);
            // Broadcast にメッセージを送るための設定
            //PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    5*1000, // 1sec = 1000
                    pending
            );
            Log.d("ChkIDonSaveButtonClick2", "" + id);


        }
        finish();

    }
}

