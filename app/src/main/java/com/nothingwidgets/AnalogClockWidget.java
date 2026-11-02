package com.nothingwidgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.provider.AlarmClock;

import java.util.Calendar;

/** Round analog clock with a dotted dial. */
public class AnalogClockWidget extends BaseWidget {
    @Override protected int defaultWidthDp() { return 110; }
    @Override protected int defaultHeightDp() { return 110; }
    @Override protected boolean squareOnly() { return true; }
    @Override protected Intent tapIntent() { return new Intent(AlarmClock.ACTION_SHOW_ALARMS); }

    @Override
    protected Bitmap render(Context c, int w, int h, Nothing t) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bmp);
        float cx = w / 2f, cy = h / 2f, r = Math.min(w, h) / 2f;

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(t.bg);
        cv.drawCircle(cx, cy, r, p);

        // dial dots
        for (int i = 0; i < 12; i++) {
            double a = Math.toRadians(i * 30);
            float x = cx + (float) Math.sin(a) * r * 0.82f;
            float y = cy - (float) Math.cos(a) * r * 0.82f;
            boolean major = i % 3 == 0;
            p.setColor(i == 0 ? Nothing.ACCENT : (major ? t.text : t.inactive));
            cv.drawCircle(x, y, r * (major ? 0.045f : 0.03f), p);
        }

        Calendar cal = Calendar.getInstance();
        int hr = cal.get(Calendar.HOUR);
        int mn = cal.get(Calendar.MINUTE);
        double hourA = Math.toRadians((hr + mn / 60.0) * 30.0);
        double minA = Math.toRadians(mn * 6.0);

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeCap(Paint.Cap.ROUND);

        p.setColor(t.text);
        p.setStrokeWidth(r * 0.085f);
        cv.drawLine(cx, cy, cx + (float) Math.sin(hourA) * r * 0.45f, cy - (float) Math.cos(hourA) * r * 0.45f, p);

        p.setStrokeWidth(r * 0.055f);
        cv.drawLine(cx, cy, cx + (float) Math.sin(minA) * r * 0.66f, cy - (float) Math.cos(minA) * r * 0.66f, p);

        p.setStyle(Paint.Style.FILL);
        p.setColor(Nothing.ACCENT);
        cv.drawCircle(cx, cy, r * 0.06f, p);
        return bmp;
    }
}
