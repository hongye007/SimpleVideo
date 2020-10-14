package com.tencent.liteav.beauty.b.b;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.beauty.*;
import android.opengl.*;

public class b extends h
{
    private int r;
    private int s;
    private float[] t;
    private String u;
    
    public b() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.r = -1;
        this.s = -1;
        this.t = new float[4];
        this.u = "Beauty3Filter";
    }
    
    @Override
    public boolean c() {
        NativeLoad.getInstance();
        this.a = NativeLoad.nativeLoadGLProgram(14);
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
    public boolean a() {
        final boolean a = super.a();
        this.r = GLES20.glGetUniformLocation(this.q(), "singleStepOffset");
        this.s = GLES20.glGetUniformLocation(this.q(), "beautyParams");
        this.a(5.0f);
        return a;
    }
    
    public void c(final int n, final int n2) {
        this.a(this.r, new float[] { 2.0f / n, 2.0f / n2 });
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
        this.c(n, n2);
    }
    
    public void a(final float n) {
        this.t[0] = n;
        this.b(this.t);
    }
    
    public void b(final float n) {
        this.t[1] = n;
        this.b(this.t);
    }
    
    public void c(final float n) {
        this.t[2] = n;
        this.b(this.t);
    }
    
    private void b(final float[] array) {
        this.c(this.s, array);
    }
}
