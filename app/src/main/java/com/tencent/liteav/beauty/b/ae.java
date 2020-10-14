package com.tencent.liteav.beauty.b;

import android.opengl.*;
import com.tencent.liteav.basic.c.*;
import java.nio.*;

public class ae extends h
{
    public int u;
    public int v;
    public int w;
    private ByteBuffer r;
    
    public ae(final String s) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n}", s);
    }
    
    public ae(final String s, final String s2) {
        super(s, s2);
        this.u = -1;
        this.w = -1;
        this.a(com.tencent.liteav.basic.c.k.a, false, true);
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        if (a) {
            this.u = GLES20.glGetAttribLocation(this.q(), "inputTextureCoordinate2");
            this.v = GLES20.glGetUniformLocation(this.q(), "inputImageTexture2");
            GLES20.glEnableVertexAttribArray(this.u);
        }
        return a;
    }
    
    @Override
    public void b() {
        super.b();
    }
    
    @Override
    protected void i() {
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.w);
        GLES20.glUniform1i(this.v, 3);
        if (this.u != -1) {
            GLES20.glEnableVertexAttribArray(this.u);
            this.r.position(0);
            GLES20.glVertexAttribPointer(this.u, 2, 5126, false, 0, (Buffer)this.r);
        }
    }
    
    public void a(final k k, final boolean b, final boolean b2) {
        final float[] a = com.tencent.liteav.basic.c.l.a(k, b, b2);
        final ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        final FloatBuffer floatBuffer = order.asFloatBuffer();
        floatBuffer.put(a);
        floatBuffer.flip();
        this.r = order;
    }
    
    public int c(final int n, final int w) {
        this.w = w;
        return this.a(n, this.m, this.n);
    }
    
    public int a(final int n, final int w, final int n2, final int n3) {
        this.w = w;
        return this.a(n, n2, n3);
    }
    
    public int d(final int n, final int w) {
        this.w = w;
        return this.b(n);
    }
}
