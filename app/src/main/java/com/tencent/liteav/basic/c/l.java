package com.tencent.liteav.basic.c;

public class l
{
    public static final float[] a;
    public static final float[] b;
    public static final float[] c;
    public static final float[] d;
    public static final float[] e;
    
    public static float[] a(final k k, final boolean b, final boolean b2) {
        float[] array = null;
        switch (l$1.a[k.ordinal()]) {
            case 1: {
                array = l.b.clone();
                break;
            }
            case 2: {
                array = l.c.clone();
                break;
            }
            case 3: {
                array = l.d.clone();
                break;
            }
            default: {
                array = l.a.clone();
                break;
            }
        }
        if (b) {
            array = new float[] { a(array[0]), array[1], a(array[2]), array[3], a(array[4]), array[5], a(array[6]), array[7] };
        }
        if (b2) {
            array = new float[] { array[0], a(array[1]), array[2], a(array[3]), array[4], a(array[5]), array[6], a(array[7]) };
        }
        return array;
    }
    
    private static float a(final float n) {
        if (n == 0.0f) {
            return 1.0f;
        }
        return 0.0f;
    }
    
    static {
        a = new float[] { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
        b = new float[] { 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f };
        c = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f };
        d = new float[] { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
        e = new float[] { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f };
    }
}
