package com.tencent.liteav.videoediter.audio;

public class d
{
    private volatile float a;
    
    public d() {
        this.a = 1.0f;
    }
    
    public void a(final float a) {
        this.a = a;
    }
    
    public short[] a(final short[] array) {
        if (this.a == 1.0f) {
            return array;
        }
        for (int i = 0; i < array.length; ++i) {
            final int n = (int)(array[i] * this.a);
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
            array[i] = n2;
        }
        return array;
    }
}
