package com.tencent.liteav.beauty.b;

import android.opengl.*;
import com.tencent.liteav.basic.c.*;
import java.nio.*;

public class ad extends h
{
    public int r;
    public int s;
    public int t;
    private ByteBuffer x;
    public int u;
    public int v;
    public int w;
    private ByteBuffer y;
    
    public ad(final String s) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nattribute vec4 inputTextureCoordinate3;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nvarying vec2 textureCoordinate3;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n    textureCoordinate3 = inputTextureCoordinate3.xy;\n}", s);
        this.a(com.tencent.liteav.basic.c.k.a, false, true);
    }
    
    public ad(final String s, final String s2) {
        super(s, s2);
        this.t = -1;
        this.w = -1;
        this.a(com.tencent.liteav.basic.c.k.a, false, true);
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        GLES20.glUseProgram(this.q());
        this.r = GLES20.glGetAttribLocation(this.q(), "inputTextureCoordinate2");
        this.u = GLES20.glGetAttribLocation(this.q(), "inputTextureCoordinate3");
        this.s = GLES20.glGetUniformLocation(this.q(), "inputImageTexture2");
        this.v = GLES20.glGetUniformLocation(this.q(), "inputImageTexture3");
        GLES20.glEnableVertexAttribArray(this.r);
        GLES20.glEnableVertexAttribArray(this.u);
        return a;
    }
    
    @Override
    public int a(final int n, final int n2, final int n3) {
        return this.a(n, n2, n3, this.m, this.n);
    }
    
    public int a(final int n, final int t, final int w, final int n2, final int n3) {
        this.t = t;
        this.w = w;
        return super.a(n, n2, n3);
    }
    
    public int b(final int n, final int t, final int w) {
        this.t = t;
        this.w = w;
        return this.b(n);
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
    }
    
    @Override
    protected void i() {
        GLES20.glEnableVertexAttribArray(this.r);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.t);
        GLES20.glUniform1i(this.s, 3);
        this.x.position(0);
        GLES20.glVertexAttribPointer(this.r, 2, 5126, false, 0, (Buffer)this.x);
        GLES20.glEnableVertexAttribArray(this.u);
        GLES20.glActiveTexture(33988);
        GLES20.glBindTexture(3553, this.w);
        GLES20.glUniform1i(this.v, 4);
        this.y.position(0);
        GLES20.glVertexAttribPointer(this.u, 2, 5126, false, 0, (Buffer)this.y);
    }
    
    public void a(final k k, final boolean b, final boolean b2) {
        final float[] a = com.tencent.liteav.basic.c.l.a(k, b, b2);
        final ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        final FloatBuffer floatBuffer = order.asFloatBuffer();
        floatBuffer.put(a);
        floatBuffer.flip();
        this.x = order;
        final ByteBuffer order2 = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        final FloatBuffer floatBuffer2 = order2.asFloatBuffer();
        floatBuffer2.put(a);
        floatBuffer2.flip();
        this.y = order2;
    }
}
