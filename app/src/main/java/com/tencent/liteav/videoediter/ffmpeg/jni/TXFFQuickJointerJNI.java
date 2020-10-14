package com.tencent.liteav.videoediter.ffmpeg.jni;

import java.util.*;
import com.tencent.liteav.basic.log.*;

public class TXFFQuickJointerJNI
{
    private static final String TAG = "TXFFQuickJointerJNI";
    private long handle;
    private boolean isInitSuccess;
    private int mTotalVideoNums;
    private a mCallback;
    
    private native long init();
    
    private native void setSrcPaths(final long p0, final String[] p1);
    
    private native void setDstPath(final long p0, final String p1);
    
    private native int start(final long p0);
    
    private native int stop(final long p0);
    
    private native void destroy(final long p0);
    
    private native int verify(final long p0);
    
    private native int getVideoWidth(final long p0);
    
    private native int getVideoHeight(final long p0);
    
    public TXFFQuickJointerJNI() {
        this.handle = -1L;
        this.handle = this.init();
        if (this.handle != -1L) {
            this.isInitSuccess = true;
        }
    }
    
    public synchronized void destroy() {
        if (this.isInitSuccess) {
            this.destroy(this.handle);
            this.isInitSuccess = false;
            this.handle = -1L;
        }
    }
    
    public synchronized int getVideoWidth() {
        if (this.isInitSuccess) {
            return this.getVideoWidth(this.handle);
        }
        return 0;
    }
    
    public synchronized int getVideoHeight() {
        if (this.isInitSuccess) {
            return this.getVideoHeight(this.handle);
        }
        return 0;
    }
    
    public synchronized void setSrcPaths(final List<String> list) {
        if (this.isInitSuccess) {
            if (list == null || list.size() == 0) {
                TXCLog.e("TXFFQuickJointerJNI", "quick joiner path empty!!!");
                return;
            }
            this.mTotalVideoNums = list.size();
            final String[] array = new String[list.size()];
            for (int i = 0; i < list.size(); ++i) {
                array[i] = list.get(i);
            }
            this.setSrcPaths(this.handle, array);
        }
    }
    
    public synchronized void setDstPath(final String s) {
        if (this.isInitSuccess) {
            this.setDstPath(this.handle, s);
        }
    }
    
    public synchronized int start() {
        if (!this.isInitSuccess) {
            return -1;
        }
        if (this.mTotalVideoNums == 0) {
            TXCLog.e("TXFFQuickJointerJNI", "quick joiner path empty!!!");
            return -1;
        }
        return this.start(this.handle);
    }
    
    public synchronized int verify() {
        if (this.isInitSuccess) {
            return this.verify(this.handle);
        }
        return -1;
    }
    
    public synchronized void stop() {
        if (this.isInitSuccess) {
            this.stop(this.handle);
        }
    }
    
    public void setOnJoinerCallback(final a mCallback) {
        this.mCallback = mCallback;
    }
    
    public void callbackFromNative(final int n) {
        TXCLog.i("TXFFQuickJointerJNI", "callbackFromNative: index = " + n);
        if (this.mCallback != null) {
            this.mCallback.a((this.mTotalVideoNums > 0) ? ((n + 1) / (float)this.mTotalVideoNums) : 0.0f);
        }
    }
    
    public interface a
    {
        void a(final float p0);
    }
}
