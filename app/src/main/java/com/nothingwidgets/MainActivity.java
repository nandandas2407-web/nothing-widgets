package com.nothingwidgets;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String[] NAMES = {"Digital Clock (pill)", "Digital Clock (large)",
            "Analog Clock", "Date", "Battery"};
    private static final Class<?>[] CLASSES = {DigitalClockWidget.class, LargeClockWidget.class,
            AnalogClockWidget.class, DateWidget.class, BatteryWidget.class};

    private int dp(int v) { return Math.round(v * getResources().getDisplayMetrics().density); }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);

        Typeface dot = Nothing.ndot(this);

        ScrollView sv = new ScrollView(this);
        sv.setBackgroundColor(Color.BLACK);
        LinearLayout col = new LinearLayout(this);
        col.setOrientation(LinearLayout.VERTICAL);
        col.setPadding(dp(24), dp(48), dp(24), dp(32));
        sv.addView(col, new ViewGroup.LayoutParams(-1, -2));

        TextView title = new TextView(this);
        title.setText("NOTHING\nWIDGETS");
        title.setTypeface(dot);
        title.setTextColor(Color.WHITE);
        title.setTextSize(40);
        col.addView(title);

        TextView sub = new TextView(this);
        sub.setText("Tap a widget to add it to your home screen.\nOr long-press home > Widgets > Nothing Widgets.");
        sub.setTextColor(0xFFAAAAAA);
        sub.setTextSize(14);
        sub.setPadding(0, dp(12), 0, dp(24));
        col.addView(sub);

        for (int i = 0; i < NAMES.length; i++) {
            final Class<?> cls = CLASSES[i];
            Button btn = new Button(this);
            btn.setText(NAMES[i].toUpperCase());
            btn.setTypeface(dot);
            btn.setTextColor(Color.WHITE);
            btn.setAllCaps(false);
            btn.setTextSize(16);
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(0xFF1A1A1A);
            bg.setCornerRadius(dp(30));
            bg.setStroke(dp(1), 0xFF444444);
            btn.setBackground(bg);
            btn.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            btn.setPadding(dp(24), 0, dp(24), 0);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, dp(60));
            lp.bottomMargin = dp(12);
            btn.setLayoutParams(lp);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) { pin(cls); }
            });
            col.addView(btn);
        }
        setContentView(sv);
    }

    private void pin(Class<?> cls) {
        AppWidgetManager m = getSystemService(AppWidgetManager.class);
        if (m != null && m.isRequestPinAppWidgetSupported()) {
            m.requestPinAppWidget(new ComponentName(this, cls), null, null);
        } else {
            Toast.makeText(this, "Long-press your home screen > Widgets > Nothing Widgets",
                    Toast.LENGTH_LONG).show();
        }
    }
}
