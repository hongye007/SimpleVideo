package com.tencent.liteav.beauty.b;

import android.graphics.*;
import android.opengl.*;
import com.tencent.liteav.basic.c.*;

public class t extends h
{
    protected String r;
    protected Bitmap s;
    public int t;
    public int u;
    protected int v;
    protected float w;
    
    public t(final Bitmap s) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2; // lookup texture\n \n \n uniform lowp float intensity;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     mediump float blueColor = textureColor.b * 63.0;\n     \n     mediump vec2 quad1;\n     quad1.y = floor(floor(blueColor) / 8.0);\n     quad1.x = floor(blueColor) - (quad1.y * 8.0);\n     \n     mediump vec2 quad2;\n     quad2.y = floor(ceil(blueColor) / 8.0);\n     quad2.x = ceil(blueColor) - (quad2.y * 8.0);\n     \n     highp vec2 texPos1;\n     texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     highp vec2 texPos2;\n     texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     lowp vec4 newColor1 = texture2D(inputImageTexture2, texPos1);\n     lowp vec4 newColor2 = texture2D(inputImageTexture2, texPos2);\n     \n     lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n     gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), intensity);\n }");
        this.s = s;
    }
    
    public t(final String s, final String s2) {
        super(s, s2);
        this.r = null;
        this.s = null;
        this.u = -1;
        this.v = -1;
    }
    
    public t() {
        this.r = null;
        this.s = null;
        this.u = -1;
        this.v = -1;
    }
    
    @Override
    public boolean a() {
        this.t = GLES20.glGetUniformLocation(this.q(), "inputImageTexture2");
        this.v = GLES20.glGetUniformLocation(this.q(), "intensity");
        this.w = 0.5f;
        return super.a();
    }
    
    @Override
    public void d() {
        super.d();
        this.a(this.s);
        this.a(this.w);
    }
    
    public void a(final Bitmap bitmap) {
        this.a(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {
                    com.tencent.liteav.beauty.b.t.this.u = com.tencent.liteav.basic.c.j.a(bitmap, com.tencent.liteav.beauty.b.t.this.u, false);
                }
            }
        });
    }
    
    public void a(final float w) {
        this.w = w;
        this.a(this.v, this.w);
    }
    
    @Override
    public void b() {
        super.b();
        GLES20.glDeleteTextures(1, new int[] { this.u }, 0);
        this.u = -1;
    }
    
    @Override
    protected void j() {
        if (this.u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
    }
    
    @Override
    protected void i() {
        if (this.u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.u);
            GLES20.glUniform1i(this.t, 3);
        }
    }
}
