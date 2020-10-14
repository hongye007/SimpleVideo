package com.tencent.liteav.k;

import com.tencent.liteav.beauty.b.*;
import android.content.*;
import com.tencent.liteav.basic.log.*;
import java.io.*;
import android.content.res.*;
import android.graphics.*;

public class g
{
    private ak a;
    private t b;
    private Context c;
    private String d;
    private n.h e;
    
    public g(final Context c) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = "Lighting";
        this.e = null;
        this.c = c;
    }
    
    public boolean a(final int n, final int n2) {
        return this.c(n, n2);
    }
    
    private boolean c(final int n, final int n2) {
        if (null == this.c) {
            TXCLog.e(this.d, "mContext is null!");
            return false;
        }
        final AssetManager assets = this.c.getResources().getAssets();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(assets.open("fennen.png"));
        }
        catch (IOException ex) {
            TXCLog.e(this.d, "decode stream failed.", ex);
        }
        if (null == this.a) {
            (this.a = new ak(bitmap)).a(true);
            if (!this.a.c()) {
                TXCLog.e(this.d, "mLoopupInvertFilter init error!");
                return false;
            }
        }
        this.a.a(n, n2);
        try {
            bitmap = BitmapFactory.decodeStream(assets.open("qingliang.png"));
        }
        catch (IOException ex2) {
            TXCLog.e(this.d, "decode stream failed.", ex2);
        }
        if (null == this.b) {
            (this.b = new t(bitmap)).a(true);
            if (!this.b.c()) {
                TXCLog.e(this.d, "mLoopupFilter init error!");
                return false;
            }
        }
        this.b.a(n, n2);
        return true;
    }
    
    public void a() {
        if (null != this.a) {
            this.a.e();
            this.a = null;
        }
        if (null != this.b) {
            this.b.e();
            this.b = null;
        }
    }
    
    public void b(final int n, final int n2) {
        this.c(n, n2);
    }
    
    public void a(final n.h e) {
        this.e = e;
        if (null != this.e) {
            if (null != this.a) {
                this.a.a(this.e.a / 5.0f);
                this.a.b(this.e.a * 1.5f);
            }
            if (null != this.b) {
                this.b.a(this.e.a / 5.0f);
            }
        }
    }
    
    public int a(final int n) {
        if (null == this.e || this.e.a <= 0.0f) {
            return n;
        }
        int n2 = n;
        if (null != this.a) {
            n2 = this.a.a(n2);
        }
        if (null != this.b) {
            n2 = this.b.a(n2);
        }
        return n2;
    }
    
    public void b() {
        this.a();
    }
}
