package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;
import java.nio.*;

public class al extends h
{
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private float w;
    private static String x;
    private final float[] y;
    private float[] z;
    
    public al() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "precision highp float;\nvarying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\nuniform lowp float alphaLevel;\nuniform vec2 offsetR; \nuniform vec2 offsetG;\nuniform vec2 offsetB;\n\nvoid main()\n{\n\tmediump vec4 fout;\n\tfout.r = texture2D(inputImageTexture, textureCoordinate + offsetR).r; \n\tfout.g = texture2D(inputImageTexture, textureCoordinate + offsetG).g; \n\tfout.b = texture2D(inputImageTexture, textureCoordinate + offsetB).b; \n\tfout.a = alphaLevel;\n\n    gl_FragColor = fout;\n}\n\n");
        this.r = -1;
        this.s = -1;
        this.t = -1;
        this.u = -1;
        this.v = -1;
        this.w = 0.3f;
        this.y = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };
        this.z = this.y.clone();
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            TXCLog.e(al.x, "onInit failed");
            return false;
        }
        this.r = GLES20.glGetUniformLocation(this.a, "textureTransform");
        this.v = GLES20.glGetUniformLocation(this.a, "alphaLevel");
        this.s = GLES20.glGetUniformLocation(this.a, "offsetR");
        this.t = GLES20.glGetUniformLocation(this.a, "offsetG");
        this.u = GLES20.glGetUniformLocation(this.a, "offsetB");
        this.a(this.w);
        return true;
    }
    
    public void a(final float[] array, final float[] array2, final float[] array3) {
        this.a(this.s, array);
        this.a(this.t, array2);
        this.a(this.u, array3);
    }
    
    public void a(final float w) {
        this.w = w;
        this.a(this.v, this.w);
    }
    
    public void a(final float n, final int n2) {
        if (n <= 0.0f) {
            this.z = this.y.clone();
        }
        else {
            this.z = this.y.clone();
            for (int i = 0; i < n2; ++i) {
                final float[] array = new float[16];
                Matrix.setIdentityM(array, 0);
                Matrix.scaleM(array, 0, n, n, 1.0f);
                Matrix.multiplyMM(this.z, 0, array, 0, this.z, 0);
                Matrix.setIdentityM(array, 0);
                Matrix.translateM(array, 0, 0.02f, 0.02f, 1.0f);
                Matrix.multiplyMM(this.z, 0, array, 0, this.z, 0);
            }
        }
    }
    
    @Override
    public int a(final int n) {
        return this.a(n, this.m, this.n);
    }
    
    @Override
    public void a(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.a);
        this.k();
        if (!this.n() || this.z == null) {
            return;
        }
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 0, (Buffer)floatBuffer);
        GLES20.glEnableVertexAttribArray(this.b);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(this.d, 2, 5126, false, 0, (Buffer)floatBuffer2);
        GLES20.glEnableVertexAttribArray(this.d);
        GLES20.glUniformMatrix4fv(this.r, 1, false, this.z, 0);
        if (n != -1) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, n);
            GLES20.glUniform1i(this.c, 0);
        }
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.b);
        GLES20.glDisableVertexAttribArray(this.d);
        GLES20.glBindTexture(3553, 0);
    }
    
    static {
        al.x = "ZoomInOut";
    }
}
