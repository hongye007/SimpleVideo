package com.tencent.rtmp.ui;

import android.content.*;
import android.util.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;

public class TXLogView extends LinearLayout
{
    private TextView b;
    private TextView c;
    private ScrollView d;
    private ScrollView e;
    StringBuffer a;
    private final int f = 3000;
    private boolean g;
    
    public TXLogView(final Context context) {
        this(context, null);
    }
    
    public TXLogView(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = new StringBuffer("");
        this.g = false;
        this.setOrientation(1);
        this.b = new TextView(context);
        this.c = new TextView(context);
        this.d = new ScrollView(context);
        this.e = new ScrollView(context);
        final LayoutParams layoutParams = new LayoutParams(-1, 0);
        layoutParams.weight = 0.2f;
        this.d.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.d.setBackgroundColor(1627389951);
        this.d.setVerticalScrollBarEnabled(true);
        this.d.setScrollbarFadingEnabled(true);
        this.b.setLayoutParams((ViewGroup.LayoutParams)new LayoutParams(-1, -1));
        this.b.setTextSize(2, 11.0f);
        this.b.setTextColor(-16777216);
        this.b.setTypeface(Typeface.MONOSPACE, 1);
        this.b.setLineSpacing(4.0f, 1.0f);
        this.b.setPadding(a(context, 2.0f), a(context, 2.0f), a(context, 2.0f), a(context, 2.0f));
        this.d.addView((View)this.b);
        final LayoutParams layoutParams2 = new LayoutParams(-1, 0);
        layoutParams2.weight = 0.8f;
        layoutParams2.topMargin = a(context, 2.0f);
        this.e.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        this.e.setBackgroundColor(1627389951);
        this.e.setVerticalScrollBarEnabled(true);
        this.e.setScrollbarFadingEnabled(true);
        this.c.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
        this.c.setTextSize(2, 13.0f);
        this.c.setTextColor(-16777216);
        this.c.setPadding(a(context, 2.0f), a(context, 2.0f), a(context, 2.0f), a(context, 2.0f));
        this.e.addView((View)this.c);
        this.addView((View)this.d);
        this.addView((View)this.e);
        this.setVisibility(8);
    }
    
    public static int a(final Context context, final float n) {
        return (int)(n * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
