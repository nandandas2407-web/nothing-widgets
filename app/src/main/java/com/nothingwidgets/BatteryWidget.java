package com.nothingwidgets;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.BatteryManager;

/** Dotted ring battery gauge. */
public class BatteryWidget extends BaseWidget {
    @Override protected int defaultWidthDp() { return 110; }
    @Override protected int defaultHeightDp() { return 110; }
    @Override protected boolean squareOnly() { return true; }
    @Override protected Intent tapIntent() { return new Intent(Intent.ACTION_POWER_USAGE_SUMMARY); }

    @Override
    protected Bitmap render(Context c, int w, int h, Nothing t) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bmp);
        float s = Math.min(w, h);
        float cx = w / 2f, cy = h / 2f;

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(t.bg);
        cv.drawRoundRect(new RectF(0, 0, w, h), s * 0.22f, s * 0.22f, p);

        int pct = 0;
        boolean charging = false;
        Intent b = c.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (b != null) {
            int level = b.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = b.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            pct = Math.round(level * 100f / Math.max(1, scale));
            charging = b.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) != 0;
        }

        final int N = 40;
        int filled = Math.round(pct / 100f * N);
        float ringR = s * 0.39f;
        for (int i = 0; i < N; i++) {
            double a = Math.toRadians(i * 360.0 / N);
            float x = cx + (float) Math.sin(a) * ringR;
            float y = cy - (float) Math.cos(a) * ringR;
            boolean on = i < filled;
            p.setColor(on ? (pct <= 15 && !charging ? Nothing.ACCENT : t.text) : t.inactive);
            cv.drawCircle(x, y, s * (on ? 0.021f : 0.016f), p);
        }

        Nothing.fitText(cv, Nothing.paint(Nothing.ndot(c), t.text), String.valueOf(pct), "00",
                new RectF(cx - s * 0.20f, cy - s * 0.17f, cx + s * 0.20f, cy + s * 0.07f));
        String label = charging ? "CHARGING" : "BATTERY";
        Nothing.fitText(cv, Nothing.paint(Nothing.ndot(c), charging ? Nothing.ACCENT : t.textSecondary),
                label, "CHARGING",
                new RectF(cx - s * 0.20f, cy + s * 0.13f, cx + s * 0.20f, cy + s * 0.21f));
        return bmp;
    }
}
