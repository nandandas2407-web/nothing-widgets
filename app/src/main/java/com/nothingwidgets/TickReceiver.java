package com.nothingwidgets;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/** Refreshes all widgets once a minute (alarm chain) and on boot / time changes. */
public class TickReceiver extends BroadcastReceiver {
    static final String ACTION_TICK = "com.nothingwidgets.TICK";

    static BaseWidget[] widgets() {
        return new BaseWidget[]{
                new DigitalClockWidget(), new LargeClockWidget(),
                new AnalogClockWidget(), new DateWidget(), new BatteryWidget()
        };
    }

    @Override
    public void onReceive(Context c, Intent intent) {
        boolean any = updateAll(c);
        if (any) schedule(c);
    }

    static boolean updateAll(Context c) {
        AppWidgetManager m = AppWidgetManager.getInstance(c);
        boolean any = false;
        for (BaseWidget w : widgets()) {
            int[] ids = m.getAppWidgetIds(new ComponentName(c, w.getClass()));
            for (int id : ids) { w.update(c, m, id); any = true; }
        }
        return any;
    }

    static void schedule(Context c) {
        AlarmManager am = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;
        long next = (System.currentTimeMillis() / 60000L + 1) * 60000L;
        PendingIntent pi = PendingIntent.getBroadcast(c, 0,
                new Intent(c, TickReceiver.class).setAction(ACTION_TICK),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        try {
            if (Build.VERSION.SDK_INT < 31 || am.canScheduleExactAlarms()) {
                am.setExact(AlarmManager.RTC, next, pi);
            } else {
                am.set(AlarmManager.RTC, next, pi);
            }
        } catch (SecurityException e) {
            am.set(AlarmManager.RTC, next, pi);
        }
    }
}
