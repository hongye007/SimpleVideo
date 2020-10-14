package com.tencent.liteav.b;

import com.tencent.liteav.CaptureAndEnc;

import android.content.*;

public class d
{
    public e a;
    
    public d(final Context context) {
        this.a = new e(context, true);
    }
    
    public void a(final float[] array) {
        this.a.a(array);
    }
    
    public f a(int a, CaptureAndEnc.e a2) {
        a2 = this.a(a2);
        a = this.a.a(a, a2.m(), a2.n(), a2.h(), 4, 0);
        final f f = new f();
        f.b = a2;
        f.a = a;
        return f;
    }
    
    public void a() {
        this.a.b();
        this.a = null;
    }
    
    private CaptureAndEnc.e a(final CaptureAndEnc.e e) {
        final int n = 360 - e.h();
        if (n == 90 || n == 270) {
            final int n2 = e.n();
            e.k(e.m());
            e.j(n2);
        }
        return e;
    }
}
