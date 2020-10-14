package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import java.nio.*;

public class z extends h
{
    private static String r;
    private int s;
    private int t;
    private float[] u;
    private int v;
    private int w;
    private int x;
    private float[] y;
    private float z;
    private float A;
    private boolean B;
    
    public z() {
        super("attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n \n uniform mat4 transformMatrix;\n uniform mat4 orthographicMatrix;\n \n varying vec2 textureCoordinate;\n void main()\n {\n     gl_Position = transformMatrix * vec4(position.xyz, 1.0) * orthographicMatrix;\n     textureCoordinate = inputTextureCoordinate.xy;\n }", com.tencent.liteav.beauty.b.z.r);
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.z = 1.0f;
        this.A = 1.0f;
        Matrix.orthoM(this.u = new float[16], 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
        Matrix.setIdentityM(this.y = new float[16], 0);
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.s = GLES20.glGetUniformLocation(this.q(), "transformMatrix");
        this.t = GLES20.glGetUniformLocation(this.q(), "orthographicMatrix");
        this.v = GLES20.glGetUniformLocation(this.q(), "scale");
        this.w = GLES20.glGetUniformLocation(this.q(), "center");
        this.x = GLES20.glGetUniformLocation(this.q(), "alpha");
        this.d(this.s, this.y);
        this.d(this.t, this.u);
        this.a(this.v, this.z);
        this.b(this.A);
        this.a(this.w, new float[] { 0.5f, 0.5f });
        return a;
    }
    
    @Override
    public void d() {
        super.d();
    }
    
    @Override
    public void a(final int n, final int n2) {
        if (this.f == n2 && this.e == n) {
            return;
        }
        super.a(n, n2);
        if (!this.B) {
            Matrix.orthoM(this.u, 0, -1.0f, 1.0f, -1.0f * n2 / n, 1.0f * n2 / n, -1.0f, 1.0f);
            this.d(this.t, this.u);
        }
    }
    
    @Override
    public int a(final int n, final int n2, final int n3) {
        if (!this.g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, n2);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16640);
        this.a(n, this.h, this.i);
        if (this.l instanceof a) {
            this.l.a(n3);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return n3;
    }
    
    @Override
    public void a(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        FloatBuffer floatBuffer3 = floatBuffer;
        if (!this.B) {
            final float[] array = new float[8];
            floatBuffer.position(0);
            floatBuffer.get(array);
            final float n2 = this.p() / (float)this.o();
            final float[] array2 = array;
            final int n3 = 1;
            array2[n3] *= n2;
            final float[] array3 = array;
            final int n4 = 3;
            array3[n4] *= n2;
            final float[] array4 = array;
            final int n5 = 5;
            array4[n5] *= n2;
            final float[] array5 = array;
            final int n6 = 7;
            array5[n6] *= n2;
            floatBuffer3 = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            floatBuffer3.put(array).position(0);
        }
        super.a(n, floatBuffer3, floatBuffer2);
    }
    
    public void a(final float n) {
        this.a(null, n);
    }
    
    private float[] a(final float[] array, final float n) {
        float[] y = array;
        if (null == y) {
            y = new float[16];
            Matrix.setIdentityM(y, 0);
        }
        Matrix.setRotateM(y, 0, n, 0.0f, 0.0f, 1.0f);
        this.y = y;
        this.d(this.s, this.y);
        return y;
    }
    
    private float[] b(final float[] array, final float n) {
        float[] y = array;
        if (null == y) {
            y = new float[16];
            Matrix.setIdentityM(y, 0);
        }
        Matrix.scaleM(y, 0, n, n, 1.0f);
        this.y = y;
        this.d(this.s, this.y);
        return y;
    }
    
    public float[] a(final float n, final float n2) {
        return this.b(this.a(null, n), n2);
    }
    
    public void b(final float n) {
        this.a(this.x, n);
    }
    
    static {
        z.r = "precision mediump float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float scale;\n uniform mediump float alpha;\n\nvoid main(void) {\n    gl_FragColor = vec4(texture2D(inputImageTexture, textureCoordinate).rgb, alpha); \n}\n";
    }
}
