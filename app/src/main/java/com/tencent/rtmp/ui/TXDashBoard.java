package com.tencent.rtmp.ui;

import java.text.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import android.widget.*;
import android.view.*;
import com.tencent.liteav.basic.util.*;
import android.text.*;
import android.os.*;

public class TXDashBoard extends LinearLayout
{
    protected TextView a;
    protected TextView b;
    protected ScrollView c;
    protected StringBuffer d;
    protected int e;
    private final SimpleDateFormat f;
    private boolean g;
    
    public TXDashBoard(final Context context) {
        this(context, null);
    }
    
    public TXDashBoard(final Context context, final AttributeSet set) {
        super(context, set);
        this.d = new StringBuffer("");
        this.e = 3000;
        this.f = new SimpleDateFormat("HH:mm:ss.SSS");
        this.g = false;
        this.setOrientation(1);
        this.setVisibility(4);
    }
    
    public void setStatusTextSize(final float textSize) {
        if (this.a != null) {
            this.a.setTextSize(textSize);
        }
    }
    
    public void a(final int n, final int n2, final int n3, final int n4) {
        if (this.a != null) {
            this.a.setPadding(n, n2, n3, 0);
        }
        if (this.c != null) {
            this.c.setPadding(n, 0, n3, n4);
        }
    }
    
    public void setEventTextSize(final float textSize) {
        if (this.b != null) {
            this.b.setTextSize(textSize);
        }
    }
    
    public void a(final CharSequence text) {
        if (this.a != null) {
            this.a.setText(text);
        }
    }
    
    public void setLogMsgLenLimit(final int e) {
        this.e = e;
    }
    
    public void setShowLevel(final int n) {
        switch (n) {
            case 0: {
                if (this.a != null) {
                    this.a.setVisibility(4);
                }
                if (this.c != null) {
                    this.c.setVisibility(4);
                }
                this.setVisibility(4);
                break;
            }
            case 1: {
                this.b();
                this.a.setVisibility(0);
                this.c.setVisibility(4);
                this.setVisibility(0);
                break;
            }
            default: {
                this.b();
                this.a.setVisibility(0);
                this.c.setVisibility(0);
                this.setVisibility(0);
                break;
            }
        }
    }
    
    private void b() {
        if (this.a != null) {
            return;
        }
        this.a = new TextView(this.getContext());
        this.b = new TextView(this.getContext());
        this.c = new ScrollView(this.getContext());
        this.a.setLayoutParams((ViewGroup.LayoutParams)new LayoutParams(-1, -2));
        this.a.setTextColor(-49023);
        this.a.setTypeface(Typeface.MONOSPACE);
        final LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.c.setPadding(0, 10, 0, 0);
        this.c.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.c.setVerticalScrollBarEnabled(true);
        this.c.setScrollbarFadingEnabled(true);
        this.b.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
        this.b.setTextColor(-49023);
        this.c.addView((View)this.b);
        this.addView((View)this.a);
        this.addView((View)this.c);
        if (this.d.length() <= 0) {
            this.d.append("liteav sdk version:" + TXCCommonUtil.getSDKVersionStr() + "\n");
        }
        this.b.setText((CharSequence)this.d.toString());
    }
    
    public void a(final String s) {
        if (TextUtils.isEmpty((CharSequence)s)) {
            return;
        }
        final String string = "[" + this.f.format(System.currentTimeMillis()) + "]" + s + "\n";
        if (this.d.length() <= 0) {
            this.d.append("liteav sdk version:" + TXCCommonUtil.getSDKVersionStr() + "\n");
        }
        while (this.d.length() > this.e) {
            int index = this.d.indexOf("\n");
            if (index == 0) {
                index = 1;
            }
            this.d = this.d.delete(0, index);
        }
        this.d = this.d.append(string);
        if (this.b != null) {
            this.b.setText((CharSequence)this.d.toString());
        }
    }
    
    public void a(final boolean g) {
        this.g = g;
    }
    
    public void a() {
        this.d.setLength(0);
        if (this.a != null) {
            this.a.setText((CharSequence)"");
        }
        if (this.b != null) {
            this.b.setText((CharSequence)"");
        }
    }
    
    public void a(final Bundle bundle, final Bundle bundle2, final int n) {
        if (this.g) {
            return;
        }
        if (n == 2011 || n == 2012) {
            return;
        }
        if (bundle != null && this.a != null) {
            this.a.setText((CharSequence)this.a(bundle));
        }
        if (this.d.length() <= 0) {
            this.d.append("liteav sdk version:" + TXCCommonUtil.getSDKVersionStr() + "\n");
        }
        if (bundle2 != null) {
            final String string = bundle2.getString("EVT_MSG");
            if (string != null && !string.isEmpty()) {
                this.a(n, string);
                if (this.b != null) {
                    this.b.setText((CharSequence)this.d.toString());
                }
                if (this.getVisibility() == 0 && this.c != null && this.b != null) {
                    this.a(this.c, (View)this.b);
                }
            }
        }
    }
    
    protected void a(final int n, final String s) {
        if (n == 1020) {
            return;
        }
        final String format = new SimpleDateFormat("HH:mm:ss.SSS").format(System.currentTimeMillis());
        while (this.d.length() > this.e) {
            int index = this.d.indexOf("\n");
            if (index == 0) {
                index = 1;
            }
            this.d = this.d.delete(0, index);
        }
        this.d = this.d.append("\n[" + format + "]" + s);
    }
    
    protected String a(final Bundle bundle) {
        return String.format("%-16s %-16s %-16s\n%-12s %-12s %-12s %-12s\n%-14s %-14s %-14s\n%-16s %-16s", "CPU:" + bundle.getString("CPU_USAGE"), "RES:" + bundle.getInt("VIDEO_WIDTH") + "*" + bundle.getInt("VIDEO_HEIGHT"), "SPD:" + bundle.getInt("NET_SPEED") + "Kbps", "JIT:" + bundle.getInt("NET_JITTER"), "FPS:" + bundle.getInt("VIDEO_FPS"), "GOP:" + bundle.getInt("VIDEO_GOP") + "s", "ARA:" + bundle.getInt("AUDIO_BITRATE") + "Kbps", "QUE:" + bundle.getInt("AUDIO_CACHE") + " | " + bundle.getInt("VIDEO_CACHE") + "," + bundle.getInt("V_SUM_CACHE_SIZE") + "," + bundle.getInt("V_DEC_CACHE_SIZE") + " | " + bundle.getInt("AV_RECV_INTERVAL") + "," + bundle.getInt("AV_PLAY_INTERVAL") + "," + String.format("%.1f", bundle.getFloat("AUDIO_CACHE_THRESHOLD")).toString(), "VRA:" + bundle.getInt("VIDEO_BITRATE") + "Kbps", "DRP:" + bundle.getInt("AUDIO_DROP") + "|" + bundle.getInt("VIDEO_DROP"), "SVR:" + bundle.getString("SERVER_IP"), "AUDIO:" + bundle.getString("AUDIO_PLAY_INFO"));
    }
    
    private void a(final ScrollView scrollView, final View view) {
        if (scrollView == null || view == null) {
            return;
        }
        int n = view.getMeasuredHeight() - scrollView.getMeasuredHeight();
        if (n < 0) {
            n = 0;
        }
        scrollView.scrollTo(0, n);
    }
}
