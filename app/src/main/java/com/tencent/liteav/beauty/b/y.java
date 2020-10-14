package com.tencent.liteav.beauty.b;

import android.hardware.*;
import java.util.*;
import android.content.*;
import javax.microedition.khronos.opengles.*;
import javax.microedition.khronos.egl.*;
import android.util.*;
import android.opengl.*;
import java.nio.*;
import java.io.*;
import com.tencent.liteav.basic.log.*;
import android.graphics.*;
import com.tencent.liteav.basic.c.*;

public class y implements Camera.PreviewCallback, GLSurfaceView.Renderer
{
    static final float[] a;
    private h c;
    public final Object b;
    private SurfaceTexture d;
    private final FloatBuffer e;
    private final FloatBuffer f;
    private IntBuffer g;
    private int h;
    private int i;
    private int j;
    private int k;
    private final Queue<Runnable> l;
    private final Queue<Runnable> m;
    private k n;
    private boolean o;
    private boolean p;
    private q.a q;
    private float r;
    private float s;
    private float t;
    private Context u;
    private int v;
    private String w;
    private FileOutputStream x;
    private int y;
    private int z;
    
    public void onSurfaceCreated(final GL10 gl10, final EGLConfig eglConfig) {
        GLES20.glClearColor(this.r, this.s, this.t, 1.0f);
        GLES20.glDisable(2929);
        this.c.c();
    }
    
    public void onSurfaceChanged(final GL10 gl10, final int h, final int i) {
        GLES20.glViewport(0, 0, this.h = h, this.i = i);
        GLES20.glUseProgram(this.c.q());
        this.c.a(h, i);
        this.a();
        synchronized (this.b) {
            this.b.notifyAll();
        }
    }
    
    public void onDrawFrame(final GL10 gl10) {
        GLES20.glClear(16640);
        this.a(this.l);
        if (this.z == -1) {
            final int[] array = { 0 };
            GLES20.glGenTextures(1, array, 0);
            GLES20.glBindTexture(3553, this.z = array[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            final TypedValue typedValue = new TypedValue();
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = typedValue.density;
            final Bitmap decodeResource = BitmapFactory.decodeResource(this.u.getResources(), this.v, options);
            GLES20.glBindTexture(3553, this.z);
            GLUtils.texImage2D(3553, 0, decodeResource, 0);
        }
        this.c.a(this.z, this.e, this.f);
        final IntBuffer allocate = IntBuffer.allocate(250000);
        GLES20.glReadPixels(0, 0, 500, 500, 6408, 5121, (Buffer)allocate);
        if (this.y++ == 50) {
            try {
                if (this.x == null && this.u != null) {
                    final File externalFilesDir = this.u.getExternalFilesDir((String)null);
                    if (externalFilesDir != null) {
                        this.x = new FileOutputStream(new File(externalFilesDir.getAbsolutePath(), "rgbBuffer"));
                    }
                }
                final String string = allocate.order().toString();
                final int[] array2 = allocate.array();
                final byte[] array3 = new byte[1000000];
                if (string.compareTo("LITTLE_ENDIAN") != 0) {
                    for (int i = 0; i < 250000; ++i) {
                        array3[i * 4 + 3] = (byte)(array2[i] >> 24 & 0xFF);
                        array3[i * 4 + 2] = (byte)(array2[i] >> 16 & 0xFF);
                        array3[i * 4 + 1] = (byte)(array2[i] >> 8 & 0xFF);
                        array3[i * 4] = (byte)(array2[i] & 0xFF);
                    }
                }
                else {
                    for (int j = 0; j < 250000; ++j) {
                        array3[j * 4] = (byte)(array2[j] >> 24 & 0xFF);
                        array3[j * 4 + 1] = (byte)(array2[j] >> 16 & 0xFF);
                        array3[j * 4 + 2] = (byte)(array2[j] >> 8 & 0xFF);
                        array3[j * 4 + 3] = (byte)(array2[j] & 0xFF);
                    }
                }
                if (this.x != null) {
                    this.x.write(array3, 0, array3.length);
                    this.x.flush();
                }
            }
            catch (Exception ex) {
                TXCLog.e(this.w, "write bitmap failed.", ex);
            }
        }
        else {
            TXCLog.e("check1", "" + this.y);
        }
        this.a(this.m);
        if (this.d != null) {
            this.d.updateTexImage();
        }
    }
    
    private void a(final Queue<Runnable> queue) {
        if (null == queue) {
            TXCLog.e(this.w, "runAll queue is null!");
            return;
        }
        synchronized (queue) {
            while (!queue.isEmpty()) {
                queue.poll().run();
            }
        }
    }
    
    public void onPreviewFrame(final byte[] array, final Camera camera) {
        final Camera.Size previewSize = camera.getParameters().getPreviewSize();
        if (this.g == null && null != previewSize) {
            this.g = IntBuffer.allocate(previewSize.width * previewSize.height);
        }
        if (this.l.isEmpty()) {
            this.a(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }
    
    private void a() {
        float n = (float)this.h;
        float n2 = (float)this.i;
        if (this.n == com.tencent.liteav.basic.c.k.d || this.n == com.tencent.liteav.basic.c.k.b) {
            n = (float)this.i;
            n2 = (float)this.h;
        }
        final float max = Math.max(n / this.j, n2 / this.k);
        final int round = Math.round(this.j * max);
        final int round2 = Math.round(this.k * max);
        final float n3 = round / n;
        final float n4 = round2 / n2;
        float[] a = com.tencent.liteav.beauty.b.y.a;
        float[] a2 = com.tencent.liteav.basic.c.l.a(this.n, this.o, this.p);
        if (this.q == com.tencent.liteav.beauty.b.q.a.b) {
            final float n5 = (1.0f - 1.0f / n3) / 2.0f;
            final float n6 = (1.0f - 1.0f / n4) / 2.0f;
            a2 = new float[] { this.a(a2[0], n5), this.a(a2[1], n6), this.a(a2[2], n5), this.a(a2[3], n6), this.a(a2[4], n5), this.a(a2[5], n6), this.a(a2[6], n5), this.a(a2[7], n6) };
        }
        else {
            a = new float[] { com.tencent.liteav.beauty.b.y.a[0] / n4, com.tencent.liteav.beauty.b.y.a[1] / n3, com.tencent.liteav.beauty.b.y.a[2] / n4, com.tencent.liteav.beauty.b.y.a[3] / n3, com.tencent.liteav.beauty.b.y.a[4] / n4, com.tencent.liteav.beauty.b.y.a[5] / n3, com.tencent.liteav.beauty.b.y.a[6] / n4, com.tencent.liteav.beauty.b.y.a[7] / n3 };
        }
        int n7 = 0;
        a2[n7++] = 0.0f;
        a2[n7++] = 1.0f;
        a2[n7++] = 1.0f;
        a2[n7++] = 1.0f;
        a2[n7++] = 0.0f;
        a2[n7++] = 0.0f;
        a2[n7++] = 1.0f;
        a2[n7++] = 0.0f;
        this.e.clear();
        this.e.put(a).position(0);
        this.f.clear();
        this.f.put(a2).position(0);
    }
    
    private float a(final float n, final float n2) {
        return (n == 0.0f) ? n2 : (1.0f - n2);
    }
    
    protected void a(final Runnable runnable) {
        synchronized (this.l) {
            this.l.add(runnable);
        }
    }
    
    static {
        a = new float[] { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f };
    }
}
