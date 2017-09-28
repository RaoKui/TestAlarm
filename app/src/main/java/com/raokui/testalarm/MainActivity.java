package com.raokui.testalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int INTERVAL = 24 * 60 * 60 * 1000;

    private EditText et_hour;

    private EditText et_minute;

    private TextView tv_text;

    private Button btn_set;

    private EditText et_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction("alarm");
        registerReceiver(mReceiver, filter);

        et_id = (EditText) findViewById(R.id.et_id);
        et_hour = (EditText) findViewById(R.id.et_hour);
        et_minute = (EditText) findViewById(R.id.et_minute);
        tv_text = (TextView) findViewById(R.id.tv_text);

        btn_set = (Button) findViewById(R.id.btn_set);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });
    }

    private void setAlarm() {


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, Integer.parseInt(et_hour.getText().toString()));
        instance.set(Calendar.MINUTE, Integer.parseInt(et_minute.getText().toString()));
        Intent alarm_intent = new Intent(this, AlarmReceiver.class);
        alarm_intent.putExtra("name", et_id.getText().toString());

        PendingIntent pending_intent = PendingIntent.getBroadcast(this, Integer.parseInt(et_id.getText().toString()), alarm_intent, 0);
        // 四个参数：1、类型  2、开始时间 3、重复时间
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), INTERVAL, pending_intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String id = intent.getStringExtra("name");
            tv_text.setText("闹钟" + id + "响了");
        }
    };
}
