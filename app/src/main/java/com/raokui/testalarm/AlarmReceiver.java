package com.raokui.testalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 饶魁 on 2017/9/27.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra("name");
        Intent intent2 = new Intent("alarm");
        intent2.putExtra("name", id);
        context.sendBroadcast(intent2);
    }
}
