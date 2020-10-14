package com.tencent.liteav.basic.c;

import android.opengl.*;
import java.nio.*;

public class i extends h
{
    private float[] s;
    private int t;
    public boolean r;
    
    public i() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "#extension GL_OES_EGL_image_external : require\n\nvarying lowp vec2 textureCoordinate;\n \nuniform samplerExternalOES inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.s = new float[16];
        this.r = false;
        this.o = true;
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.t = GLES20.glGetUniformLocation(this.a, "textureTransform");
        return a && GLES20.glGetError() == 0;
    }
    
    @Override
    public void a(final float[] s) {
        this.s = s;
    }
    
    @Override
    public void a(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.a);
        this.k();
        if (!this.n() || this.s == null) {
            return;
        }
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 0, (Buffer)floatBuffer);
        GLES20.glEnableVertexAttribArray(this.b);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(this.d, 2, 5126, false, 0, (Buffer)floatBuffer2);
        GLES20.glEnableVertexAttribArray(this.d);
        GLES20.glUniformMatrix4fv(this.t, 1, false, this.s, 0);
        if (n != -1) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, n);
            GLES20.glUniform1i(this.c, 0);
        }
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.b);
        GLES20.glDisableVertexAttribArray(this.d);
        GLES20.glBindTexture(36197, 0);
    }
}
