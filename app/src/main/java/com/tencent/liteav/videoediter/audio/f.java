package com.tencent.liteav.videoediter.audio;

public class f
{
    private volatile float a;
    private volatile float b;
    
    public f() {
        this.a = 1.0f;
        this.b = 1.0f;
    }
    
    public void a(final float a) {
        this.a = a;
    }
    
    public void b(final float b) {
        this.b = b;
    }
    
    public short[] a(final short[] array, final short[] array2) {
        if (array2 != null && array2.length != 0) {
            for (int i = 0; i < array.length; ++i) {
                array[i] = this.a((int)(array[i] * this.b + array2[i] * this.a));
            }
            return array;
        }
        if (this.b == 1.0f) {
            return array;
        }
        for (int j = 0; j < array.length; ++j) {
            array[j] = this.a((int)(array[j] * this.b));
        }
        return array;
    }
    
    private short a(final int n) {
        short n2;
        if (n > 32767) {
            n2 = 32767;
        }
        else if (n < -32768) {
            n2 = -32768;
        }
        else {
            n2 = (short)n;
        }
        return n2;
    }
}
