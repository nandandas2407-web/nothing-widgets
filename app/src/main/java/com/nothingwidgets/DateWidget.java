package com.nothingwidgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Square date card: weekday, big day number, month. */
public class DateWidget extends BaseWidget {
    @Override protected int defaultWidthDp() { return 110; }
    @Override protected int defaultHeightDp() { return 110; }
    @Override protected boolean squareOnly() { return true; }
    @Override protected Intent tapIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("content://com.android.calendar/time/" + System.currentTimeMillis()));
    }

    @Override
    protected Bitmap render(Context c, int w, int h, Nothing t) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bmp);
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(t.bg);
        cv.drawRoundRect(new RectF(0, 0, w, h), w * 0.22f, w * 0.22f, bg);

        Date now = new Date();
        String dow = new SimpleDateFormat("EEE", Locale.ENGLISH).format(now).toUpperCase(Locale.ENGLISH);
        String day = new SimpleDateFormat("dd", Locale.ENGLISH).format(now);
        String mon = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).format(now).toUpperCase(Locale.ENGLISH);

        float m = w * 0.14f;
        Nothing.fitText(cv, Nothing.paint(Nothing.ndot(c), Nothing.ACCENT), dow, "MON",
                new RectF(m, h * 0.09f, w * 0.5f, h * 0.24f));
        Nothing.fitText(cv, Nothing.paint(Nothing.ndot(c), t.text), day, "00",
                new RectF(m, h * 0.28f, w - m, h * 0.72f));
        Nothing.fitText(cv, Nothing.paint(Nothing.ndot(c), t.textSecondary), mon, "MMM 0000",
                new RectF(m, h * 0.78f, w * 0.7f, h * 0.90f));
        return bmp;
    }
}
