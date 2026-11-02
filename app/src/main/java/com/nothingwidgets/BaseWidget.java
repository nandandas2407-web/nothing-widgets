package com.nothingwidgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;

/** All widgets render a bitmap (so the Nothing dot font works) and show it in an ImageView. */
public abstract class BaseWidget extends AppWidgetProvider {

    protected abstract int defaultWidthDp();
    protected abstract int defaultHeightDp();
    protected boolean squareOnly() { return false; }
    protected abstract Bitmap render(Context c, int w, int h, Nothing t);
    protected abstract Intent tapIntent();

    public void update(Context c, AppWidgetManager m, int id) {
        Bundle o = m.getAppWidgetOptions(id);
        int wdp = o.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 0);
        int hdp = o.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 0);
        if (wdp <= 0 || hdp <= 0) { wdp = defaultWidthDp(); hdp = defaultHeightDp(); }
        if (squareOnly()) { wdp = Math.min(wdp, hdp); hdp = wdp; }

        float d = c.getResources().getDisplayMetrics().density;
        float w = wdp * d, h = hdp * d;
        float sc = Math.min(1f, 640f / Math.max(w, h));
        int wi = Math.max(8, Math.round(w * sc));
        int hi = Math.max(8, Math.round(h * sc));

        Context app = c.getApplicationContext();
        Bitmap bmp = render(app, wi, hi, new Nothing(Nothing.isDark(app)));

        RemoteViews rv = new RemoteViews(c.getPackageName(), R.layout.widget_image);
        rv.setImageViewBitmap(R.id.img, bmp);
        Intent tap = tapIntent();
        if (tap != null) {
            tap.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pi = PendingIntent.getActivity(c, id, tap,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            rv.setOnClickPendingIntent(R.id.root, pi);
        }
        m.updateAppWidget(id, rv);
    }

    @Override
    public void onUpdate(Context c, AppWidgetManager m, int[] ids) {
        for (int id : ids) update(c, m, id);
        TickReceiver.schedule(c);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context c, AppWidgetManager m, int id, Bundle o) {
        update(c, m, id);
    }

    @Override
    public void onEnabled(Context c) {
        TickReceiver.schedule(c);
    }
}
