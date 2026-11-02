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

/** Pill-shaped digital clock. */
public class DigitalClockWidget extends BaseWidget {
    @Override protected int defaultWidthDp() { return 250; }
    @Override protected int defaultHeightDp() { return 70; }
    @Override protected Intent tapIntent() { return new Intent(AlarmClock.ACTION_SHOW_ALARMS); }

    @Override
    protected Bitmap render(Context c, int w, int h, Nothing t) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bmp);
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(t.bg);
        cv.drawRoundRect(new RectF(0, 0, w, h), h / 2f, h / 2f, bg);

        boolean is24 = DateFormat.is24HourFormat(c);
        Date now = new Date();
        String time = new SimpleDateFormat(is24 ? "HH:mm" : "hh:mm", Locale.ENGLISH).format(now);
        Paint p = Nothing.paint(Nothing.ndot(c), t.text);
        float pad = h * 0.30f;
        float right = is24 ? w - pad : w - pad - h * 0.75f;
        Nothing.fitText(cv, p, time, "00:00", new RectF(pad, h * 0.2f, right, h * 0.8f));

        if (!is24) {
            String ap = new SimpleDateFormat("a", Locale.ENGLISH).format(now).toUpperCase(Locale.ENGLISH);
            Paint a = Nothing.paint(Nothing.ndot(c), Nothing.ACCENT);
            Nothing.fitText(cv, a, ap, "AM", new RectF(w - pad - h * 0.6f, h * 0.38f, w - pad, h * 0.62f));
        }
        return bmp;
    }
}
