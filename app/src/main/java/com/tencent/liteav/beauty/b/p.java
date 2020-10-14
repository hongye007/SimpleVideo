package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.beauty.*;
import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import java.nio.*;

public class p extends h
{
    private byte[] r;
    private int s;
    private int[] t;
    private int[] u;
    private int v;
    private int w;
    private int[] x;
    private static String y;
    
    public p(final int s) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.r = null;
        this.s = 1;
        this.t = null;
        this.u = null;
        this.v = 0;
        this.w = 0;
        this.x = null;
        this.s = s;
        TXCLog.i(p.y, "yuv Type " + s);
    }
    
    @Override
    public boolean c() {
        int n = 7;
        if (this.s == 1) {
            n = 7;
        }
        else if (this.s == 3) {
            n = 9;
        }
        else {
            if (this.s == 2) {
                return super.c();
            }
            TXCLog.e(p.y, "don't support yuv format " + this.s);
        }
        NativeLoad.getInstance();
        this.a = NativeLoad.nativeLoadGLProgram(n);
        if (this.a != 0 && this.a()) {
            this.g = true;
        }
        else {
            this.g = false;
        }
        this.d();
        return this.g;
    }
    
    @Override
    public void a(final int n, final int n2) {
        if (this.f == n2 && this.e == n) {
            return;
        }
        this.t();
        if (null == this.t) {
            (this.t = new int[1])[0] = com.tencent.liteav.basic.c.j.a(n, n2, 6409, 6409, this.t);
        }
        this.v = GLES20.glGetUniformLocation(this.q(), "yTexture");
        this.w = GLES20.glGetUniformLocation(this.q(), "uvTexture");
        if (1 == this.s) {
            GLES20.glActiveTexture(33984);
            GLES20.glActiveTexture(33985);
            (this.u = new int[1])[0] = com.tencent.liteav.basic.c.j.a(n, n2 / 2, 6409, 6409, this.u);
            GLES20.glUniform1i(this.v, 0);
            GLES20.glUniform1i(this.w, 1);
        }
        else if (3 == this.s) {
            GLES20.glActiveTexture(33984);
            GLES20.glActiveTexture(33985);
            this.v = GLES20.glGetUniformLocation(this.q(), "yTexture");
            this.w = GLES20.glGetUniformLocation(this.q(), "uvTexture");
            (this.u = new int[1])[0] = com.tencent.liteav.basic.c.j.a(n / 2, n2 / 2, 6410, 6410, this.u);
            GLES20.glUniform1i(this.v, 0);
            GLES20.glUniform1i(this.w, 1);
        }
        else if (2 == this.s && null == this.x) {
            (this.x = new int[1])[0] = com.tencent.liteav.basic.c.j.a(n, n2, 6408, 6408, this.x);
        }
        super.a(n, n2);
    }
    
    public void a(final byte[] r) {
        this.r = r;
    }
    
    @Override
    protected void i() {
        super.i();
        int[] array = null;
        if (0 != this.e % 4) {
            array = new int[] { 0 };
            GLES20.glGetIntegerv(3317, array, 0);
            GLES20.glPixelStorei(3317, 1);
        }
        if (1 == this.s) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.t[0]);
            GLES20.glUniform1i(this.v, 0);
            NativeLoad.getInstance();
            NativeLoad.nativeglTexImage2D(3553, 0, 6409, this.e, this.f, 0, 6409, 5121, this.r, 0);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, this.u[0]);
            GLES20.glUniform1i(this.w, 1);
            NativeLoad.getInstance();
            NativeLoad.nativeglTexImage2D(3553, 0, 6409, this.e, this.f / 2, 0, 6409, 5121, this.r, this.e * this.f);
        }
        else if (3 == this.s) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.t[0]);
            GLES20.glUniform1i(this.v, 0);
            NativeLoad.getInstance();
            NativeLoad.nativeglTexImage2D(3553, 0, 6409, this.e, this.f, 0, 6409, 5121, this.r, 0);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, this.u[0]);
            GLES20.glUniform1i(this.w, 1);
            NativeLoad.getInstance();
            NativeLoad.nativeglTexImage2D(3553, 0, 6410, this.e / 2, this.f / 2, 0, 6410, 5121, this.r, this.e * this.f);
        }
        else if (2 == this.s) {
            this.s();
        }
        if (0 != this.e % 4) {
            if (null != array && array[0] > 0) {
                GLES20.glPixelStorei(3317, array[0]);
            }
            else {
                GLES20.glPixelStorei(3317, 4);
            }
        }
    }
    
    public int r() {
        if (2 == this.s) {
            final int s = this.s();
            GLES20.glBindTexture(3553, 0);
            return s;
        }
        return super.a(-1);
    }
    
    private int s() {
        GLES20.glBindTexture(3553, this.x[0]);
        if (null != this.r) {
            NativeLoad.getInstance();
            NativeLoad.nativeglTexImage2D(3553, 0, 6408, this.e, this.f, 0, 6408, 5121, this.r, 0);
        }
        return this.x[0];
    }
    
    @Override
    public void a(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        super.a(-1, floatBuffer, floatBuffer2);
    }
    
    private void t() {
        if (null != this.t && this.t[0] > 0) {
            GLES20.glDeleteTextures(1, this.t, 0);
            this.t = null;
        }
        if (null != this.u && this.u[0] > 0) {
            GLES20.glDeleteTextures(1, this.u, 0);
            this.u = null;
        }
        if (null != this.x && this.x[0] > 0) {
            GLES20.glDeleteTextures(1, this.x, 0);
            this.x = null;
        }
    }
    
    @Override
    public void b() {
        super.b();
        this.t();
    }
    
    static {
        p.y = "YUV420pToRGBFilter";
    }
}
