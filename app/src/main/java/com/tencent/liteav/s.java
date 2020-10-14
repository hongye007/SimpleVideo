package com.tencent.liteav;

import android.content.*;
import com.tencent.rtmp.ui.*;
import java.lang.ref.*;
import com.tencent.liteav.basic.b.*;
import com.tencent.liteav.basic.log.*;
import android.view.*;
import com.tencent.ugc.*;
import com.tencent.rtmp.*;
import com.tencent.liteav.basic.c.*;

public abstract class s
{
    protected j b;
    protected Context c;
    protected TXCloudVideoView d;
    protected WeakReference<b> e;
    
    public s(final Context context) {
        this.b = null;
        this.c = null;
        this.d = null;
        if (context != null) {
            this.c = context.getApplicationContext();
        }
    }
    
    public j q() {
        return this.b;
    }
    
    public void a(final j b) {
        this.b = b;
        if (this.b == null) {
            this.b = new j();
        }
    }
    
    public abstract int a(final String p0, final int p1);
    
    public abstract int a(final boolean p0);
    
    public void a() {
        TXCLog.w("TXIPlayer", "pause not support");
    }
    
    public void b() {
        TXCLog.w("TXIPlayer", "resume not support");
    }
    
    public void a(final Surface surface) {
    }
    
    public void a(final int n, final int n2) {
    }
    
    public void e(final int n) {
        TXCLog.w("TXIPlayer", "seek not support");
    }
    
    public void a(final TXCloudVideoView d) {
        this.d = d;
    }
    
    public TextureView d() {
        return null;
    }
    
    public void a(final b b) {
        this.e = new WeakReference<b>(b);
    }
    
    public void a(final TXRecordCommon.ITXVideoRecordListener itxVideoRecordListener) {
    }
    
    public void a(final TXLivePlayer.ITXAudioRawDataListener itxAudioRawDataListener) {
    }
    
    public abstract void b(final boolean p0);
    
    public abstract void c(final boolean p0);
    
    public abstract void c(final int p0);
    
    public abstract void a(final int p0);
    
    public abstract void b(final int p0);
    
    public abstract int d(final int p0);
    
    public abstract int e();
    
    public abstract boolean c();
    
    public void e(final boolean b) {
        TXCLog.w("TXIPlayer", "autoPlay not implement");
    }
    
    public void b(final float n) {
        TXCLog.w("TXIPlayer", "rate not implement");
    }
    
    public void a(final Context context, final int n) {
    }
    
    public void a(final t t, final com.tencent.liteav.basic.a.b b, final Object o) {
    }
    
    public int a(final String s) {
        return -1;
    }
    
    public void g() {
    }
    
    public boolean d(final boolean b) {
        return false;
    }
    
    public boolean f() {
        return false;
    }
    
    public abstract void a(final boolean p0, final int p1);
    
    public abstract int i();
    
    public abstract void a(final o p0);
}
