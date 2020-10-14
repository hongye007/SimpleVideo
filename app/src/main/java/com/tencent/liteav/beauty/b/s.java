package com.tencent.liteav.beauty.b;

import android.graphics.*;
import android.opengl.*;
import com.tencent.liteav.basic.c.*;
import java.nio.*;

public class s extends h
{
    private float r;
    private Bitmap s;
    private int t;
    private int u;
    private float v;
    private Bitmap w;
    private int x;
    private int y;
    private float z;
    private int A;
    private float[] B;
    private int C;
    private float[] D;
    
    public s(final float r, final Bitmap s, final float v, final Bitmap w, final float z) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2; // lookup texture 1\n uniform sampler2D inputImageTexture3; // lookup texture 2\n \n \n uniform lowp vec3 v3_params;\n uniform lowp vec2 v2_texs;\n \n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     mediump float blueColor = textureColor.b * 63.0;\n     \n     mediump vec2 quad1;\n     quad1.y = floor(floor(blueColor) / 8.0);\n     quad1.x = floor(blueColor) - (quad1.y * 8.0);\n     \n     mediump vec2 quad2;\n     quad2.y = floor(ceil(blueColor) / 8.0);\n     quad2.x = ceil(blueColor) - (quad2.y * 8.0);\n     \n     highp vec2 texPos1;\n     texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     highp vec2 texPos2;\n     texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     lowp vec4 newColor1;\n     lowp vec4 newColor2;\n     if(textureCoordinate.x <= v3_params.x) { \n       if(v2_texs.x == 1.0) { \n         newColor1 = texture2D(inputImageTexture2, texPos1);\n         newColor2 = texture2D(inputImageTexture2, texPos2);\n         lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n         gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), v3_params.y);\n       } else { \n         gl_FragColor = textureColor;\n       } \n     } else {\n       if(v2_texs.y == 1.0) { \n         newColor1 = texture2D(inputImageTexture3, texPos1);\n         newColor2 = texture2D(inputImageTexture3, texPos2);\n         lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n         gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), v3_params.z);\n       } else { \n         gl_FragColor = textureColor;\n       } \n     }\n }");
        this.B = new float[3];
        this.D = new float[2];
        this.r = r;
        this.s = s;
        this.w = w;
        this.v = v;
        this.z = z;
    }
    
    public s(final String s, final String s2) {
        super(s, s2);
        this.u = -1;
        this.y = -1;
    }
    
    public s() {
        this.u = -1;
        this.y = -1;
    }
    
    @Override
    public boolean a() {
        this.t = GLES20.glGetUniformLocation(this.q(), "inputImageTexture2");
        this.x = GLES20.glGetUniformLocation(this.q(), "inputImageTexture3");
        this.A = GLES20.glGetUniformLocation(this.q(), "v3_params");
        this.C = GLES20.glGetUniformLocation(this.q(), "v2_texs");
        return super.a();
    }
    
    @Override
    public void d() {
        super.d();
        this.a(this.r, this.s, this.v, this.w, this.z);
    }
    
    public void a(final float n, final Bitmap bitmap, final float n2, final Bitmap bitmap2, final float n3) {
        this.a(n, n2, n3);
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.beauty.b.s.this.s = bitmap;
                com.tencent.liteav.beauty.b.s.this.w = bitmap2;
                if (bitmap != null) {
                    com.tencent.liteav.beauty.b.s.this.u = com.tencent.liteav.basic.c.j.a(bitmap, com.tencent.liteav.beauty.b.s.this.u, false);
                    com.tencent.liteav.beauty.b.s.this.D[0] = 1.0f;
                }
                else {
                    if (com.tencent.liteav.beauty.b.s.this.u != -1) {
                        GLES20.glDeleteTextures(1, new int[] { com.tencent.liteav.beauty.b.s.this.u }, 0);
                    }
                    com.tencent.liteav.beauty.b.s.this.u = -1;
                    com.tencent.liteav.beauty.b.s.this.D[0] = 0.0f;
                }
                if (bitmap2 != null) {
                    com.tencent.liteav.beauty.b.s.this.y = com.tencent.liteav.basic.c.j.a(bitmap2, com.tencent.liteav.beauty.b.s.this.y, false);
                    com.tencent.liteav.beauty.b.s.this.D[1] = 1.0f;
                }
                else {
                    if (com.tencent.liteav.beauty.b.s.this.y != -1) {
                        GLES20.glDeleteTextures(1, new int[] { com.tencent.liteav.beauty.b.s.this.y }, 0);
                    }
                    com.tencent.liteav.beauty.b.s.this.y = -1;
                    com.tencent.liteav.beauty.b.s.this.D[1] = 0.0f;
                }
            }
        });
    }
    
    public void a(final float n) {
        this.a(this.r, n, 0.0f);
    }
    
    public void a(final float r, final float v, final float z) {
        this.r = r;
        this.v = v;
        this.z = z;
        this.B[0] = this.r;
        this.B[1] = this.v;
        this.B[2] = this.z;
    }
    
    @Override
    public void b() {
        super.b();
        GLES20.glDeleteTextures(2, new int[] { this.u, this.y }, 0);
        this.u = -1;
        this.y = -1;
    }
    
    @Override
    protected void i() {
        if (this.u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.u);
            GLES20.glUniform1i(this.t, 3);
        }
        if (this.y != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(3553, this.y);
            GLES20.glUniform1i(this.x, 4);
        }
        GLES20.glUniform2fv(this.C, 1, FloatBuffer.wrap(this.D));
        GLES20.glUniform3fv(this.A, 1, FloatBuffer.wrap(this.B));
    }
    
    @Override
    protected void j() {
        if (this.u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
        if (this.y != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
    }
}
