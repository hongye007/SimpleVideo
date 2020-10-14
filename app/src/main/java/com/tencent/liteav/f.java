package com.tencent.liteav;

import android.opengl.*;

public class f extends ae
{
    private int[] r;
    private int[] s;
    private h t;
    
    public f() {
        super("precision mediump float;  \nvarying vec2 textureCoordinate;  \nuniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2;  \nvoid main() {   \n\tgl_FragColor = vec4(mix(texture2D(inputImageTexture2, textureCoordinate).rgb, texture2D(inputImageTexture, textureCoordinate).rgb, vec3(0.06,0.21,0.6)),1.0);   \n}  \n");
        this.r = null;
        this.s = null;
    }
    
    @Override
    public boolean a() {
        this.b = GLES20.glGetAttribLocation(this.a, "position");
        this.c = GLES20.glGetUniformLocation(this.a, "inputImageTexture");
        this.d = GLES20.glGetAttribLocation(this.a, "inputTextureCoordinate");
        this.v = GLES20.glGetUniformLocation(this.q(), "inputImageTexture2");
        return true;
    }
    
    @Override
    public int a(final int n) {
        if (this.t == null) {
            (this.t = new h()).a(true);
            this.t.c();
            this.t.a(this.e, this.f);
            this.t.a(n, this.t.m(), this.t.l());
        }
        final int c = this.c(n, this.t.l());
        this.t.a(c, this.t.m(), this.t.l());
        return c;
    }
    
    @Override
    public void b() {
        super.b();
        if (this.t != null) {
            this.t.startWithoutAudio();
            this.t = null;
        }
        if (this.s != null) {
            GLES20.glDeleteFramebuffers(1, this.s, 0);
            this.s = null;
        }
        if (this.r != null) {
            GLES20.glDeleteTextures(1, this.r, 0);
            this.r = null;
        }
    }
}
