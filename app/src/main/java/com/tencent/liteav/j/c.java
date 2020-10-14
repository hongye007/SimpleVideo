package com.tencent.liteav.j;

import com.tencent.liteav.basic.log.*;

public class c
{
    public static int[] a(final int n, int n2, int n3) {
        if (n2 <= 0 || n3 <= 0) {
            TXCLog.w("GlUtil", "no input size. " + n2 + "*" + n3);
            return new int[] { n2, n3 };
        }
        final float n4 = n2 * 1.0f / n3;
        int n5 = n2;
        int n6 = n3;
        if (n == 0) {
            if ((n2 <= 640 && n3 <= 360) || (n2 <= 360 && n3 <= 640)) {
                TXCLog.i("GlUtil", "target size: " + n2 + "*" + n3);
                return new int[] { n2, n3 };
            }
            if (n2 >= n3) {
                final int n7 = (int)(360.0f * n4);
                n5 = ((n7 >= 640) ? 640 : n7);
                n6 = (int)(n5 / n4);
            }
            else {
                final int n8 = (int)(640.0f * n4);
                n5 = ((n8 >= 360) ? 360 : n8);
                n6 = (int)(n5 / n4);
            }
        }
        else if (n == 1) {
            if ((n2 <= 640 && n3 <= 480) || (n2 <= 480 && n3 <= 640)) {
                TXCLog.i("GlUtil", "target size: " + n2 + "*" + n3);
                return new int[] { n2, n3 };
            }
            if (n2 >= n3) {
                final int n9 = (int)(480.0f * n4);
                n5 = ((n9 >= 640) ? 640 : n9);
                n6 = (int)(n5 / n4);
            }
            else {
                final int n10 = (int)(640.0f * n4);
                n5 = ((n10 >= 480) ? 480 : n10);
                n6 = (int)(n5 / n4);
            }
        }
        else if (n == 2) {
            if ((n2 <= 960 && n3 <= 544) || (n2 <= 544 && n3 <= 960)) {
                TXCLog.i("GlUtil", "target size: " + n2 + "*" + n3);
                return new int[] { n2, n3 };
            }
            if (n2 >= n3) {
                final int n11 = (int)(544.0f * n4);
                n5 = ((n11 >= 960) ? 960 : n11);
                n6 = (int)(n5 / n4);
            }
            else {
                final int n12 = (int)(960.0f * n4);
                n5 = ((n12 >= 544) ? 544 : n12);
                n6 = (int)(n5 / n4);
            }
        }
        else if (n == 3) {
            if ((n2 <= 1280 && n3 <= 720) || (n2 <= 720 && n3 <= 1280)) {
                TXCLog.i("GlUtil", "target size: " + n2 + "*" + n3);
                return new int[] { n2, n3 };
            }
            if (n2 >= n3) {
                final int n13 = (int)(720.0f * n4);
                n5 = ((n13 >= 1280) ? 1280 : n13);
                n6 = (int)(n5 / n4);
            }
            else {
                final int n14 = (int)(1280.0f * n4);
                n5 = ((n14 >= 720) ? 720 : n14);
                n6 = (int)(n5 / n4);
            }
        }
        n2 = n5;
        n3 = n6;
        n2 = n2 + 1 >> 1 << 1;
        n3 = n3 + 1 >> 1 << 1;
        return new int[] { n2, n3 };
    }
}
