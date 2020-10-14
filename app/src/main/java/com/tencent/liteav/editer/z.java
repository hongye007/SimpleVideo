package com.tencent.liteav.editer;

import java.util.concurrent.atomic.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;

public class z extends d
{
    private final String Y = "VideoDecAndDemuxGenerateGivenTimes";
    
    public z() {
        this.V = 0;
        this.W = 0;
        this.X = new AtomicBoolean(true);
        this.U = new ArrayList<Long>();
        (this.C = new HandlerThread("DecGeneGiveTime")).start();
        this.B = new b(this.C.getLooper());
    }
    
    @Override
    protected void l() {
        if (this.A.get() == 2) {
            TXCLog.e("VideoDecAndDemuxGenerateGivenTimes", "start ignore, state = " + this.A.get());
            return;
        }
        this.a(this.g.get());
        this.B.sendEmptyMessage(5);
        this.A.set(2);
    }
    
    @Override
    protected void m() {
        if (this.A.get() == 1) {
            TXCLog.e("VideoDecAndDemuxGenerateGivenTimes", "stop ignore, mCurrentState = " + this.A.get());
            return;
        }
        this.B.sendEmptyMessage(7);
    }
    
    @Override
    public void a(final boolean b) {
    }
    
    @Override
    public synchronized void p() {
        this.X.set(true);
    }
    
    @Override
    public void k() {
        if (this.C != null) {
            final Message obtain = Message.obtain();
            obtain.what = 105;
            obtain.arg1 = 13;
            this.B.sendMessage(obtain);
            this.C = null;
        }
    }
}
