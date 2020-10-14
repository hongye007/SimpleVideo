package com.tencent.liteav.g;

import android.media.*;
import android.os.*;

public class s extends i
{
    private static s w;
    public int u;
    public int v;
    
    public static s r() {
        if (s.w == null) {
            s.w = new s();
        }
        return s.w;
    }
    
    private s() {
    }
    
    public g a(final boolean b) {
        g f = new g();
        f.c = 0;
        switch (this.v) {
            case 0: {
                f.a = 360;
                f.b = 640;
                break;
            }
            case 1: {
                f.a = 480;
                f.b = 640;
                break;
            }
            case 2: {
                f.a = 540;
                f.b = 960;
                break;
            }
            case 3: {
                f.a = 720;
                f.b = 1280;
                break;
            }
        }
        if (b) {
            f = this.f(f);
        }
        return f;
    }
    
    private g f(final g g) {
        final int b = g.b;
        g.b = g.a;
        g.a = b;
        return g;
    }
    
    public void d(final MediaFormat mediaFormat) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.a = mediaFormat.getInteger("sample-rate");
            this.b = mediaFormat.getInteger("channel-count");
        }
    }
}
