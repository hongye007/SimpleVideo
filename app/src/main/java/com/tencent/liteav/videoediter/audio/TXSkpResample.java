package com.tencent.liteav.videoediter.audio;

import com.tencent.liteav.basic.log.*;

public class TXSkpResample
{
    private static final String TAG = "TXSkpResample";
    private volatile boolean isInitSuccess;
    private long handle;
    
    private final native long nativeInit(final int p0, final int p1);
    
    private final native void uninit(final long p0);
    
    private final native short[] resample(final long p0, final short[] p1);
    
    public synchronized void init(final int n, final int n2) {
        if (this.isInitSuccess) {
            this.destroy();
        }
        this.handle = this.nativeInit(n, n2);
        this.isInitSuccess = (this.handle != -1L);
    }
    
    public synchronized short[] doResample(final short[] array) {
        if (this.isInitSuccess) {
            return this.resample(this.handle, array);
        }
        TXCLog.e("TXSkpResample", " you should nativeInit this object first");
        return array;
    }
    
    public synchronized void destroy() {
        if (this.isInitSuccess) {
            this.isInitSuccess = false;
            this.uninit(this.handle);
        }
    }
}
