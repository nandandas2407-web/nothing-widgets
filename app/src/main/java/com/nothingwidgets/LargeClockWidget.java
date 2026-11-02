package com.nothingwidgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.provider.AlarmClock;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Square clock with stacked hours / minutes. */
public class LargeClockWidget extends BaseWidget {
    @Override protected int defaultWidthDp() { return 110; }
    @Override protected int defaultHeightDp() { return 110; }
    @Override protected boolean squareOnly() { return true; }
    @Override protected Intent tapIntent() { return new Intent(AlarmClock.ACTION_SHOW_ALARMS); }

    @Override
    protected Bitmap render(Context c, int w, int h, Nothing t) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bmp);
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(t.bg);
        cv.drawRoundRect(new RectF(0, 0, w, h), w * 0.22f, w * 0.22f, bg);

        boolean is24 = DateFormat.is24HourFormat(c);
        Date now = new Date();
        String hh = new SimpleDateFormat(is24 ? "HH" : "hh", Locale.ENGLISH).format(now);
        String mm = new SimpleDateFormat("mm", Locale.ENGLISH).format(now);

        Paint hp = Nothing.paint(Nothing.ndot(c), t.text);
        Paint mp = Nothing.paint(Nothing.ndot(c), t.text);
        float m = w * 0.14f;
        Nothing.fitText(cv, hp, hh, "00", new RectF(m, h * 0.10f, w - m, h * 0.48f));
        Nothing.fitText(cv, mp, mm, "00", new RectF(m, h * 0.52f, w - m, h * 0.90f));

        Paint dot = new Paint(Paint.ANTI_ALIAS_FLAG);
        dot.setColor(Nothing.ACCENT);
        cv.drawCircle(w * 0.12f, h * 0.12f, w * 0.025f, dot);
        return bmp;
    }
}
