package com.tencent.liteav.editer;

import java.util.concurrent.atomic.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;

public class ai extends d
{
    private final String Y = "VideoDecAndDemuxGenerateGivenTimes";
    private boolean Z;
    
    public ai(final String s) {
        this.V = 0;
        this.W = 0;
        this.X = new AtomicBoolean(true);
        this.U = new ArrayList<Long>();
        (this.C = new HandlerThread(s + "Dec")).start();
        this.B = new b(this.C.getLooper());
    }
    
    @Override
    protected void l() {
        if (this.A.get() == 2) {
            TXCLog.e("VideoDecAndDemuxGenerateGivenTimes", "start ignore, state = " + this.A.get());
            return;
        }
        if (this.B != null) {
            if (this.Z) {
                this.B.sendEmptyMessage(5);
            }
            else {
                this.B.sendEmptyMessage(8);
            }
        }
        this.A.set(2);
    }
    
    @Override
    protected void m() {
        if (this.A.get() == 1) {
            TXCLog.e("VideoDecAndDemuxGenerateGivenTimes", "stop ignore, mCurrentState = " + this.A.get());
            return;
        }
        if (this.B != null) {
            this.B.sendEmptyMessage(7);
        }
    }
    
    @Override
    public synchronized void p() {
        this.X.set(true);
    }
    
    @Override
    public void k() {
        if (this.C != null) {
            if (this.B != null) {
                if (this.Z) {
                    final Message obtain = Message.obtain();
                    obtain.what = 105;
                    obtain.arg1 = 13;
                    this.B.sendMessage(obtain);
                }
                else {
                    final Message obtain2 = Message.obtain();
                    obtain2.what = 105;
                    obtain2.arg1 = 12;
                    this.B.sendMessage(obtain2);
                }
            }
            this.C = null;
        }
    }
    
    @Override
    public void a(final boolean z) {
        this.Z = z;
    }
}
