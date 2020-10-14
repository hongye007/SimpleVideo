package com.tencent.liteav.videoediter.audio;

import com.tencent.liteav.basic.log.*;

public class e
{
    private int a;
    private int b;
    private volatile boolean c;
    
    public void a(final int a, final int b) {
        if (this.a == a && this.b == b) {
            return;
        }
        this.c = true;
        this.a = a;
        this.b = b;
        this.a();
    }
    
    public short[] a(final short[] array) {
        if (array == null) {
            return array;
        }
        if (this.a()) {
            return array;
        }
        if (this.a == this.b) {
            return array;
        }
        if (this.a == 2 && this.b == 1) {
            return this.c(array);
        }
        if (this.a == 1 && this.b == 2) {
            return this.b(array);
        }
        return array;
    }
    
    private boolean a() {
        if (!this.c) {
            TXCLog.e("FaceDetect", "you must set target channel count first");
            return true;
        }
        if (this.a < 1 || this.a > 2 || this.b < 1 || this.b > 2) {
            TXCLog.e("FaceDetect", "channel count must between 1 and 2");
            return true;
        }
        return false;
    }
    
    private short[] b(final short[] array) {
        final short[] array2 = new short[array.length * 2];
        for (int i = 0; i < array.length; ++i) {
            array2[i * 2] = array[i];
            array2[i * 2 + 1] = array[i];
        }
        return array2;
    }
    
    private short[] c(final short[] array) {
        final int n = array.length / 2;
        final short[] array2 = new short[n];
        for (int i = 0, n2 = 0; i < n; ++i, ++n2, ++n2) {
            array2[i] = array[n2];
        }
        return array2;
    }
}
