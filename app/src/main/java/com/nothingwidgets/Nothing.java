package com.nothingwidgets;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

/** Nothing-style palette, fonts and text helpers. */
final class Nothing {
    static final int ACCENT = 0xFFD71921;

    final boolean dark;
    final int bg, surface, text, textSecondary, inactive;

    Nothing(boolean dark) {
        this.dark = dark;
        bg = dark ? 0xFF1A1A1A : 0xFFF5F5F5;
        surface = dark ? 0xFF2A2A2A : 0xFFFFFFFF;
        text = dark ? 0xFFFFFFFF : 0xFF1A1A1A;
        textSecondary = dark ? 0xFFAAAAAA : 0xFF666666;
        inactive = dark ? 0xFF4A4A4A : 0xFFCCCCCC;
    }

    static boolean isDark(Context c) {
        int m = c.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return m == Configuration.UI_MODE_NIGHT_YES;
    }

    private static Typeface ndot, ntype;

    static synchronized Typeface ndot(Context c) {
        if (ndot == null) {
            try { ndot = Typeface.createFromAsset(c.getAssets(), "fonts/ndot-55.otf"); }
            catch (Exception e) { ndot = Typeface.MONOSPACE; }
        }
        return ndot;
    }

    static synchronized Typeface ntype(Context c) {
        if (ntype == null) {
            try { ntype = Typeface.createFromAsset(c.getAssets(), "fonts/ntype82-regular.otf"); }
            catch (Exception e) { ntype = Typeface.SANS_SERIF; }
        }
        return ntype;
    }

    static Paint paint(Typeface tf, int color) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        p.setTypeface(tf);
        p.setColor(color);
        return p;
    }

    /** Draws text scaled to fill box (sized from ref, so size stays stable), centred. */
    static void fitText(Canvas cv, Paint p, String s, String ref, RectF box) {
        Rect r = new Rect();
        p.setTextSize(100f);
        p.getTextBounds(ref, 0, ref.length(), r);
        float scale = Math.min(box.width() / Math.max(1, r.width()), box.height() / Math.max(1, r.height()));
        p.setTextSize(100f * scale);
        p.getTextBounds(s, 0, s.length(), r);
        p.setTextAlign(Paint.Align.LEFT);
        cv.drawText(s, box.centerX() - r.exactCenterX(), box.centerY() - r.exactCenterY(), p);
    }
}
