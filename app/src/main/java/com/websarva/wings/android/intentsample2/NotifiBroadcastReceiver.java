package com.websarva.wings.android.intentsample2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotifiBroadcastReceiver extends BroadcastReceiver {

    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 0);
        Log.d("ChkIDonRv1", "" + id);
        DbReminder	db = new DbReminder(new MySQLiteOpenHelper(context).getWritableDatabase());
        DataReminder data = db.getData(id);
        System.out.println("データタイトル" + data.title);
        // RecieverからMainActivityを起動させる
        //notificationManager.cancelAll();
        Log.d("ChkIDonRv2", "" + id);

        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("ChkIDnoMgr", "" + id);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("リマインドです")
                .setWhen(System.currentTimeMillis())
                .setContentTitle(data.title)
                .setContentText(data.date)
                // 音、バイブレート、LEDで通知
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        Log.d("ChkIDno", "" + id);

        // 古い通知を削除
       //notificationManager.cancel();
        //Log.d("notiManagerCancelAll", "" + id);

        // 通知
        notificationManager.notify(R.string.app_name, notification);
    }

}
