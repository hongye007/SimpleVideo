package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import java.util.*;
import com.tencent.liteav.beauty.*;
import android.graphics.*;
import android.util.*;
import android.opengl.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import java.nio.*;

public class aj extends h
{
    protected a[] r;
    private a x;
    protected List<e.f> s;
    protected boolean t;
    protected int u;
    private int y;
    protected static final short[] v;
    private static final float[] z;
    private static final float[] A;
    protected ShortBuffer w;
    private String B;
    
    public aj(final String s, final String s2) {
        super(s, s2);
        this.r = null;
        this.x = null;
        this.s = null;
        this.t = false;
        this.u = 1;
        this.y = 1;
        this.w = null;
        this.B = "GPUWatermark";
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(aj.v.length * 2);
        allocateDirect.order(ByteOrder.nativeOrder());
        (this.w = allocateDirect.asShortBuffer()).put(aj.v);
        this.w.position(0);
        this.o = true;
    }
    
    public aj() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }
    
    @Override
    public void e() {
        super.e();
        this.t = false;
        this.r();
    }
    
    public void d(final boolean t) {
        this.t = t;
    }
    
    public void a(final Bitmap c, final float n, final float n2, final float n3, final int n4) {
        if (c == null) {
            if (null != this.r && null != this.r[n4]) {
                Log.i(this.B, "release " + n4 + " water mark!");
                if (this.r[n4].d != null) {
                    GLES20.glDeleteTextures(1, this.r[n4].d, 0);
                }
                this.r[n4].d = null;
                this.r[n4].c = null;
                this.r[n4] = null;
            }
            return;
        }
        if (null == this.r[n4] || n4 >= this.r.length) {
            Log.e(this.B, "index is too large for mSzWaterMark!");
            return;
        }
        this.a(c.getWidth(), c.getHeight(), n, n2, n3, n4);
        if (this.r[n4].d == null) {
            GLES20.glGenTextures(1, this.r[n4].d = new int[1], 0);
            GLES20.glBindTexture(3553, this.r[n4].d[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
        }
        if (this.r[n4].c == null || !this.r[n4].c.equals(c)) {
            GLES20.glBindTexture(3553, this.r[n4].d[0]);
            if (!c.isRecycled()) {
                GLUtils.texImage2D(3553, 0, c, 0);
            }
            else {
                TXCLog.e(this.B, "SetWaterMark when bitmap is recycled");
            }
        }
        this.r[n4].c = c;
    }
    
    protected void a(final int n, final int n2, final float n3, final float n4, final float n5, final int n6) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(aj.z.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        this.r[n6].a = allocateDirect.asFloatBuffer();
        final float[] array = new float[aj.z.length];
        final float n7 = n2 / (float)n * n5 * this.e / this.f;
        array[0] = 2.0f * n3 - 1.0f;
        array[1] = 1.0f - 2.0f * n4;
        array[2] = array[0];
        array[3] = array[1] - 2.0f * n7;
        array[4] = array[0] + 2.0f * n5;
        array[5] = array[3];
        array[6] = array[4];
        array[7] = array[1];
        for (int i = 1; i <= 7; i += 2) {
            final float[] array2 = array;
            final int n8 = i;
            array2[n8] *= -1.0f;
        }
        this.r[n6].a.put(array);
        this.r[n6].a.position(0);
        final ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(aj.A.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        (this.r[n6].b = allocateDirect2.asFloatBuffer()).put(aj.A);
        this.r[n6].b.position(0);
    }
    
    public void a(final Bitmap bitmap, final float n, final float n2, final float n3) {
        if (null == this.r) {
            this.r = new a[1];
        }
        if (null == this.r[0]) {
            this.r[0] = new a();
        }
        this.a(bitmap, n, n2, n3, 0);
        this.x = this.r[0];
    }
    
    public void a(final List<e.f> s) {
        if (null != this.s && this.a(this.s, s)) {
            Log.i(this.B, "Same markList");
            return;
        }
        this.s = s;
        if (null != this.r && this.r.length > 1) {
            for (int i = this.y; i < this.r.length; ++i) {
                if (this.r[i] != null && this.r[i].d != null) {
                    GLES20.glDeleteTextures(1, this.r[i].d, 0);
                }
            }
        }
        (this.r = new a[s.size() + this.y])[0] = this.x;
        for (int j = 0; j < s.size(); ++j) {
            final e.f f = s.get(j);
            if (null != f) {
                this.r[j + this.y] = new a();
                this.a(f.a, f.b, f.c, f.d, j + this.y);
            }
        }
    }
    
    private boolean a(final List<e.f> list, final List<e.f> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            final e.f f = list.get(i);
            final e.f f2 = list2.get(i);
            if (!TXCCommonUtil.equals(f.a, f2.a) || f.b != f2.b || f.c != f2.c || f.d != f2.d) {
                return false;
            }
        }
        return true;
    }
    
    private void r() {
        if (null != this.r) {
            for (int i = 0; i < this.r.length; ++i) {
                if (null != this.r[i]) {
                    if (this.r[i].d != null) {
                        GLES20.glDeleteTextures(1, this.r[i].d, 0);
                    }
                    this.r[i].d = null;
                    this.r[i].c = null;
                    this.r[i] = null;
                }
            }
        }
        this.r = null;
    }
    
    @Override
    protected void j() {
        super.j();
        if (this.t) {
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(this.u, 771);
            GLES20.glActiveTexture(33984);
            for (int i = 0; i < this.r.length; ++i) {
                if (null != this.r[i]) {
                    GLES20.glBindTexture(3553, this.r[i].d[0]);
                    GLES20.glUniform1i(this.c, 0);
                    GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 8, (Buffer)this.r[i].a);
                    GLES20.glEnableVertexAttribArray(this.b);
                    GLES20.glVertexAttribPointer(this.d, 4, 5126, false, 16, (Buffer)this.r[i].b);
                    GLES20.glEnableVertexAttribArray(this.d);
                    GLES20.glDrawElements(4, aj.v.length, 5123, (Buffer)this.w);
                    GLES20.glDisableVertexAttribArray(this.b);
                    GLES20.glDisableVertexAttribArray(this.d);
                }
            }
            GLES20.glDisable(3042);
        }
    }
    
    static {
        v = new short[] { 1, 2, 0, 2, 0, 3 };
        z = new float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
        A = new float[] { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f };
    }
    
    public class a
    {
        public FloatBuffer a;
        public FloatBuffer b;
        public Bitmap c;
        public int[] d;
        
        public a() {
            this.a = null;
            this.b = null;
            this.d = null;
        }
    }
}
