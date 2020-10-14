package com.tencent.liteav.editer;

import android.annotation.*;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.basic.log.*;

import android.os.*;

@TargetApi(16)
public class y extends d
{
    private final String Y = "VideoDecAndDemuxGenerate";
    
    public y(final String s) {
        (this.C = new HandlerThread(s + "VdecGene")).start();
        this.B = new b(this.C.getLooper());
        (this.E = new HandlerThread(s + "AdecGene")).start();
        this.D = new a(this.E.getLooper());
    }
    
    public synchronized void l() {
        if (this.A.get() == 2 || this.A.get() == 3) {
            TXCLog.e("VideoDecAndDemuxGenerate", "start ignore, mCurrentState = " + this.A.get());
            return;
        }
        this.A.set(2);
        this.S.set(false);
        this.T.getAndSet(false);
        this.w.getAndSet(false);
        this.x.getAndSet(false);
        this.y.getAndSet(false);
        this.z.getAndSet(false);
        this.i = 0;
        final com.tencent.liteav.i.a.i b = CameraProxy.f.a().b();
        if (b == null) {
            this.b(0L, 0L);
        }
        else {
            this.b(b.a * 1000L, b.b * 1000L);
        }
        this.a(this.g.get());
        this.B.sendEmptyMessage(101);
        if (this.h()) {
            this.D.sendEmptyMessage(201);
        }
    }
    
    public synchronized void m() {
        if (this.A.get() == 1) {
            TXCLog.e("VideoDecAndDemuxGenerate", "stop ignore, mCurrentState is STATE_INIT");
            return;
        }
        this.A.set(1);
        this.B.sendEmptyMessage(103);
        if (this.h()) {
            this.D.sendEmptyMessage(203);
        }
    }
    
    @Override
    public void a(final boolean b) {
    }
    
    @Override
    public void k() {
        if (this.C != null) {
            final Message obtain = Message.obtain();
            obtain.what = 105;
            obtain.arg1 = 14;
            this.B.sendMessage(obtain);
            this.C = null;
        }
        if (this.E != null) {
            if (this.h()) {
                final Message obtain2 = Message.obtain();
                obtain2.what = 205;
                this.D.sendMessage(obtain2);
                this.E = null;
            }
            else {
                this.E.quit();
            }
            this.E = null;
        }
    }
}
