package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.basic.b.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;

public class m
{
    private int a;
    private int b;
    private boolean c;
    private ah d;
    private boolean e;
    private i f;
    private g g;
    private x h;
    private boolean i;
    private static String j;
    private b k;
    
    public void a(final b k) {
        TXCLog.i(m.j, "set notify");
        this.k = k;
    }
    
    public int a(final int n) {
        return n;
    }
    
    private void b() {
        TXCLog.i(m.j, "come into destroyPlayer");
        if (this.d != null) {
            this.d.a();
        }
        this.d = null;
        this.e = false;
        this.i = false;
        TXCLog.i(m.j, "come out destroyPlayer");
    }
    
    public void a() {
        TXCLog.i(m.j, "come into GreenScreen destroy");
        this.b();
        this.c();
        if (this.f != null) {
            this.f.e();
            this.f = null;
        }
        if (null != this.g) {
            this.g.e();
            this.g = null;
        }
        if (null != this.h) {
            this.h.e();
            this.h = null;
        }
        this.c = false;
        TXCLog.i(m.j, "come out GreenScreen destroy");
    }
    
    private void c() {
        if (this.b != -1 && this.b != this.a) {
            GLES20.glDeleteTextures(1, new int[] { this.b }, 0);
            this.b = -1;
        }
        if (this.a != -1) {
            GLES20.glDeleteTextures(1, new int[] { this.a }, 0);
            this.a = -1;
        }
    }
    
    static {
        m.j = "GPUGreenScreen";
    }
}
