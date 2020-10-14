package com.tencent.liteav.basic.util;

import android.os.*;

public class h extends Handler
{
    private int a;
    private boolean b;
    private a c;
    
    public h(final Looper looper, final a c) {
        super(looper);
        this.b = false;
        this.c = c;
    }
    
    public void handleMessage(final Message message) {
        if (null != this.c) {
            this.c.a();
        }
        if (this.b) {
            this.sendEmptyMessageDelayed(0, (long)this.a);
        }
    }
    
    public void a(final int n, final int a) {
        this.a();
        this.a = a;
        this.b = true;
        this.sendEmptyMessageDelayed(0, (long)n);
    }
    
    public void a() {
        while (this.hasMessages(0)) {
            this.removeMessages(0);
        }
        this.b = false;
    }
    
    public interface a
    {
        void a();
    }
}
