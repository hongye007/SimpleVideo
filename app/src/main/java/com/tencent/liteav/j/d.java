package com.tencent.liteav.j;

import java.io.*;
import android.graphics.*;
import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import java.nio.*;

public class d
{
    public static FileOutputStream a;
    public static FileOutputStream b;
    public static FileOutputStream c;
    
    public static Bitmap a(final int n, final int n2, final int n3) {
        final Bitmap bitmap = Bitmap.createBitmap(n2, n3, Bitmap.Config.ARGB_8888);
        final ByteBuffer order = ByteBuffer.allocate(n2 * n3 * 4).order(ByteOrder.nativeOrder());
        order.position(0);
        final h h = new h();
        h.c();
        GLES20.glViewport(0, 0, n2, n3);
        h.b(n);
        GLES20.glReadPixels(0, 0, n2, n3, 6408, 5121, (Buffer)order);
        bitmap.copyPixelsFromBuffer((Buffer)order);
        h.e();
        return bitmap;
    }
    
    static {
        d.a = null;
        d.b = null;
        d.c = null;
    }
}
