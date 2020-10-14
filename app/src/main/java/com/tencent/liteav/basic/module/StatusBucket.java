package com.tencent.liteav.basic.module;

import com.tencent.liteav.basic.log.*;

public class StatusBucket
{
    private static final String TAG;
    private long mBucketObj;
    
    public StatusBucket() {
        this.mBucketObj = 0L;
        this.mBucketObj = nativeCreateStatusBucket();
    }
    
    public StatusBucket(final long mBucketObj) {
        this.mBucketObj = 0L;
        this.mBucketObj = mBucketObj;
    }
    
    public void setBooleanStatus(final String s, final int n, final boolean b) {
        nativeSetBooleanStatus(this.mBucketObj, s, n, b);
    }
    
    public void setIntStatus(final String s, final int n, final int n2) {
        nativeSetIntStatus(this.mBucketObj, s, n, n2);
    }
    
    public void setLongStatus(final String s, final int n, final long n2) {
        nativeSetLongStatus(this.mBucketObj, s, n, n2);
    }
    
    public boolean getBooleanStatus(final String s, final int n) {
        return nativeGetBooleanStatus(this.mBucketObj, s, n);
    }
    
    public int getIntStatus(final String s, final int n) {
        return nativeGetIntStatus(this.mBucketObj, s, n);
    }
    
    public long getLongStatus(final String s, final int n) {
        return nativeGetLongStatus(this.mBucketObj, s, n);
    }
    
    public void merge(final StatusBucket statusBucket) {
        nativeMerge(this.mBucketObj, statusBucket.mBucketObj);
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        final long mBucketObj = this.mBucketObj;
        this.mBucketObj = 0L;
        nativeDestroyStatusBucket(mBucketObj);
    }
    
    public static void testStatusBucket() {
        final StatusBucket statusBucket = new StatusBucket();
        final StatusBucket statusBucket2 = new StatusBucket();
        statusBucket.setBooleanStatus("1", 1, true);
        statusBucket.setIntStatus("2", 2, 2);
        statusBucket.setLongStatus("3", 3, 3L);
        statusBucket2.setIntStatus("1", 4, 4);
        statusBucket2.setBooleanStatus("5", 5, true);
        statusBucket2.setLongStatus("6", 6, 6L);
        statusBucket.merge(statusBucket2);
        TXCLog.e(StatusBucket.TAG, "test_status_bucket: id:1, key:1, value:" + statusBucket.getBooleanStatus("1", 1));
        TXCLog.e(StatusBucket.TAG, "test_status_bucket: id:1, key:4, value:" + statusBucket.getIntStatus("1", 4));
        TXCLog.e(StatusBucket.TAG, "test_status_bucket: id:2, key:2, value:" + statusBucket.getIntStatus("2", 2));
        TXCLog.e(StatusBucket.TAG, "test_status_bucket: id:3, key:3, value:" + statusBucket.getLongStatus("3", 3));
        TXCLog.e(StatusBucket.TAG, "test_status_bucket: id:5, key:5, value:" + statusBucket.getBooleanStatus("5", 5));
        TXCLog.e(StatusBucket.TAG, "test_status_bucket: id:6, key:6, value:" + statusBucket.getLongStatus("6", 6));
    }
    
    private static native long nativeCreateStatusBucket();
    
    private static native void nativeDestroyStatusBucket(final long p0);
    
    private static native void nativeSetBooleanStatus(final long p0, final String p1, final int p2, final boolean p3);
    
    private static native void nativeSetIntStatus(final long p0, final String p1, final int p2, final int p3);
    
    private static native void nativeSetLongStatus(final long p0, final String p1, final int p2, final long p3);
    
    private static native boolean nativeGetBooleanStatus(final long p0, final String p1, final int p2);
    
    private static native int nativeGetIntStatus(final long p0, final String p1, final int p2);
    
    private static native long nativeGetLongStatus(final long p0, final String p1, final int p2);
    
    private static native void nativeMerge(final long p0, final long p1);
    
    static {
        TAG = StatusBucket.class.getName();
    }
}
